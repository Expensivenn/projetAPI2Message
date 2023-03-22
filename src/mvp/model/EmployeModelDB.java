package mvp.model;

import classemetiers.Bureau;
import classemetiers.Employe;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeModelDB implements DAOEmploye{
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

    @Override
    public Employe addEmploye(Employe employe) {
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
    public boolean removeEmploye(Employe employe) {
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
    public Employe updateEmploye(Employe employe) {
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
    public Employe searchEmp(int id) {
        String query = "SELECT * FROM APIEMPLOYE WHERE id_employe = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id);
            int n = pstm.executeUpdate();
            ResultSet rs = pstm.executeQuery();
            rs.next();
            if(n>0){
                System.out.println(n + " ligne trouvé");
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
    public List<Employe> getEmployes() {
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

    private Employe find(ResultSet rs) throws SQLException {
        int idEmp = rs.getInt(1);
        String mail = rs.getString(2);
        String nom = rs.getString(3);
        String prenom = rs.getString(4);
        int idBur = rs.getInt(5);
        Employe e = new Employe(idEmp,mail,nom,prenom,idBur);
        return e;
    }

    @Override
    public List<Bureau> getBureau(){
        List<Bureau> l = new ArrayList<>();
        String query="select * from APIBUREAU";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int idBur = rs.getInt(1);
                String sigle = rs.getString(2);
                String tel = rs.getString(3);
                Bureau b = new Bureau(idBur,sigle,tel);
                l.add(b);
            }

        } catch (SQLException e) {
            System.err.println("erreur sql :"+e);
            return null;
        }
        return l;
    }
}

