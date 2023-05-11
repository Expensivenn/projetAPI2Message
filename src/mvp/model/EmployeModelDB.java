package mvp.model;

import classemetiers.Bureau;
import classemetiers.Employe;
import classemetiers.Message;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeModelDB implements DAO<Employe>,EmployeSpecial{
    private Connection dbConnect;
    private static final Logger logger = LogManager.getLogger(EmployeModelDB.class);
    public EmployeModelDB(){
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            // System.err.println("erreur de connexion");
            logger.error("erreur de connexion");
            System.exit(1);
        }
        logger.info("connexion établie");
    }
    //DAO BASIQUE
    @Override
    public Employe add(Employe employe) {
        String query1 = "insert into APIEMPLOYE(email,prenom,nom,id_bureau) values(?,?,?,?)";
        String query2 = "select id_employe from APIEMPLOYE where nom= ? and prenom =? and email =?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setString(1, employe.getMail());
            pstm1.setString(2, employe.getPrenom());
            pstm1.setString(3, employe.getNom());
            pstm1.setInt(4,employe.getId_bureau() );

            int n = pstm1.executeUpdate();
            if (n == 1) {
                pstm2.setString(1, employe.getNom());
                pstm2.setString(2, employe.getPrenom());
                pstm2.setString(3, employe.getMail());
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int idEmpl = rs.getInt(1);
                    employe.setId(idEmpl);
                    return employe;
                }
                else {
                    System.err.println("record introuvable");
                    return null;
                }
            }
            else{return null;}

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
            return null;
        }
    }


    @Override
    public boolean remove(Employe employe) {
        String query = "delete from APIEMPLOYE where id_employe = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,employe.getId());
            int n = pstm.executeUpdate();
            if(n!=0) return true;
            else return false;

        } catch (SQLException e) {
            System.err.println("erreur sql :"+e);
            return false;
        }
    }

    @Override
    public Employe update(Employe employe) {
        String query = "UPDATE APIEMPLOYE SET nom=?, prenom=?, email=?, id_bureau=? WHERE id_employe=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, employe.getNom());
            pstm.setString(2, employe.getPrenom());
            pstm.setString(3, employe.getMail());
            pstm.setInt(4, employe.getId_bureau());
            pstm.setInt(5, employe.getId());
            int n = pstm.executeUpdate();
            System.out.println(n + " ligne(s) mise(s) à jour");
        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
            return null;
        }
        return employe;
    }

    @Override
    public Employe read(Employe rech) {
        String query = "SELECT * FROM APIEMPLOYE WHERE id_employe = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, rech.getId());
            int n = pstm.executeUpdate();
            ResultSet rs = pstm.executeQuery();
            rs.next();
            if(n>0){
                //System.out.println(n + " ligne trouvé");
                Employe e = find(rs);
                return e;
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
            return null;
        }
        return null;
    }




    @Override
    public List<Employe> getAll() {
        List<Employe> l = new ArrayList<>();
        String query="select * from APIEMPLOYE";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                Employe e = find(rs);
                l.add(e);
            }

        } catch (SQLException e) {
            System.err.println("erreur sql :"+e);
            return null;
        }
        return l;
    }
    //EMPLOYE SPECIAL
    @Override
    public Employe readID(int id) {
        String query = "SELECT * FROM APIEMPLOYE WHERE id_employe = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id);
            int n = pstm.executeUpdate();
            ResultSet rs = pstm.executeQuery();
            rs.next();
            if(n>0){
                //System.out.println(n + " ligne trouvé");
                Employe e = find(rs);
                return e;
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
            return null;
        }
        return null;
    }


    private Employe find(ResultSet rs) throws SQLException {
        int idEmp = rs.getInt(1);
        String mail = rs.getString(2);
        String nom = rs.getString(4);
        String prenom = rs.getString(3);
        int idBur = rs.getInt(5);
        Employe e = new Employe(idEmp,mail,nom,prenom,idBur);
        return e;
    }



    @Override
    public List<Message> messagesRecu(Employe employe) {
        String query = "SELECT * FROM APIMESSRECUID WHERE id_empl = ?";
        return rechercheMessages(employe,query);
    }

    @Override
    public List<Message> messagesEnvoye(Employe employe) {
        List<Message> lm = new ArrayList<>();
        String query = "SELECT * FROM API_EMPLOYES_MESSAGES WHERE id_dest = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,employe.getId());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt(2);
                String objet = rs.getString(5);
                String contenu = rs.getString(6);
                LocalDate dateEnvoi = rs.getDate(7).toLocalDate();
                char lu = rs.getString(3).charAt(0);
                Date dateOuv = rs.getDate(4);
                LocalDate dateOuvert = dateOuv!=null?dateOuv.toLocalDate():null;
                Message m = new Message(id,objet,contenu,dateEnvoi);
                if(lu=='Y'){
                    m.setLu(true);
                    m.setDateRec(dateOuvert);
                }
                lm.add(m);
            }

        } catch (SQLException e) {
            //System.out.println("erreur sql :"+e);
            logger.error("erreur sql : "+e);
            return null;
        }
        return lm;
    }



    private  List<Message> rechercheMessages(Employe employe,String query){
        List<Message>lm = new ArrayList<>();
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,employe.getId());
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                //int idMes = rs.getInt(1);
                String object = rs.getString(2);
                String contenu = rs.getString(3);
                LocalDate dateEnv = rs.getDate(4).toLocalDate();
                Message m = new Message(object,contenu,dateEnv);
                lm.add(m);
            }

        } catch (SQLException e) {
            //System.out.println("erreur sql :"+e);
            logger.error("erreur sql : "+e);
            return null;
        }
        return lm;
    }
}

