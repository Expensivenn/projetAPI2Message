package mvp.model;

import classemetiers.Employe;
import classemetiers.Message;
import mvp.presenter.EmployePresenter;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilitaires.Utilitaires;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageModelDB implements DAO<Message>,MessagesSpecial{
    private Connection dbConnect;
    private static final Logger logger = LogManager.getLogger(MessageModelDB.class);
    public MessageModelDB(){
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            // System.err.println("erreur de connexion");
            logger.error("erreur de connexion");
            System.exit(1);
        }
        logger.info("connexion établie");
    }

    @Override
    public Message add(Message message) {
        String query1 = "insert into APIMESSAGE(objet,contenu,dateenvoi,id_employe) values(?,?,?,?)";
        String query2 = "select id_message from APIMESSAGE where id_employe = ? and objet = ?";
        String query3 = "insert into APIEMPLMES(id_message,id_employe) values(?,?)";

        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
             PreparedStatement pstm3 = dbConnect.prepareStatement(query3);
        ) {
            pstm1.setString(1, message.getObjet());
            pstm1.setString(2, message.getContenu() );
            java.sql.Date dsql = java.sql.Date.valueOf(message.getDate());
            pstm1.setDate(3,dsql);
            pstm1.setInt(4,message.getEmmetteur().getId());

            int n = pstm1.executeUpdate();
            int size = message.getRecepteurs().size();
            if (n == 1) {
                pstm2.setInt(1, message.getEmmetteur().getId());
                pstm2.setString(2, message.getObjet());
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int idMess = rs.getInt(1);
                    message.setId(idMess);
                    //ENCODER TABLE INTER

                    for(int i = 0;i<size;i++){

                        pstm3.setInt(1,idMess);
                        pstm3.setInt(2,message.getRecepteurIndex(i).getId());
                        int m = pstm3.executeUpdate();
                    }
                    return message;
                }
                else {
                    //System.err.println("record introuvable");
                    logger.error("record introuvable");
                    return null;
                }
            }
            else{return null;}

        } catch (SQLException e) {
            //System.out.println("erreur sql :" + e);
            logger.error("erreur sql :"+e);
            return null;
        }
    }


    @Override
    public boolean remove(Message message) {
        String query = "delete from APIMESSAGE where id_message = ?";
        String query1 = "delete from APIEMPLMES where id_message = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query1);
            PreparedStatement pstm1 = dbConnect.prepareStatement(query);) {
            pstm.setInt(1,message.getId());
            int n = pstm.executeUpdate();
            if(n!=0){
                pstm1.setInt(1,message.getId());
                int m = pstm1.executeUpdate();
                if(m!=0){
                    return true;
                }
            }
            else return false;

        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur sql :"+e);
            return false;
        }
        return false;
    }

    @Override
    public Message update(Message message) {
        String query = "UPDATE APIMESSAGE SET objet=?, contenu=? WHERE id_message=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1,message.getObjet());
            pstm.setString(2,message.getContenu());
            pstm.setInt(3,message.getId());
            int n = pstm.executeUpdate();
            if(n>0){
                System.out.println(n + " ligne(s) mise(s) à jour");
            }


        } catch (SQLException e) {
            //System.out.println("erreur sql :" + e);
            logger.error("erreur sql :"+e);
            return null;
        }
        return message;
    }

    @Override
    public Message read(Message rech) {
        String query = "SELECT * FROM APIMESSAGE WHERE id_message = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, rech.getId());
            int n = pstm.executeUpdate();
            ResultSet rs = pstm.executeQuery();
            rs.next();
            if(n>0){
                System.out.println(n + " ligne trouvé");
                Message m = find(rs);
                return m;
            }

        } catch (SQLException e) {
            //System.out.println("erreur sql :" + e);
            logger.error("erreur sql :"+e);
            return null;
        }
        return null;
    }

    @Override
    public List<Message> getAll() {
        List<Message> l = new ArrayList<>();
        String query="select * from APIMESSAGE";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);

            while(rs.next()){
                Message m = find(rs);
                l.add(m);
            }

        } catch (SQLException e) {
            //System.err.println("erreur sql :"+e);
            logger.error("erreur sql :"+e);
            return null;
        }
        return l;
    }
   //OPE SPE
    @Override
    public List<Integer> findIds(Message m){
        List<Integer> l = new ArrayList<>();
        String query1 = "SELECT * FROM APIMESSAGE WHERE id_message = ?";
        String query2 = "SELECT * FROM APIEMPLMES WHERE id_message = ?";

        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            pstm1.setInt(1, m.getId());
            ResultSet rs1 = pstm1.executeQuery();
            if (rs1.next()) {
                l.add(rs1.getInt(5));
            }
            pstm2.setInt(1, m.getId());
            ResultSet rs2 = pstm2.executeQuery();
            while (rs2.next()) {
                l.add(rs2.getInt(1));
            }
            return l;
        } catch (SQLException e) {
            logger.error("erreur sql :" + e);
            return null;
        }
    }

    @Override
    public List<Message> OpeSpeMessageEntreDates(LocalDate d1, LocalDate d2, Employe employe) {
        List<Message> lm = new ArrayList<>();
        java.sql.Date d1Sql = java.sql.Date.valueOf(d1);
        java.sql.Date d2Sql = java.sql.Date.valueOf(d2);
        String query = "select * from APIMESSAGE where id_employe = ? AND dateenvoi BETWEEN ? AND ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,employe.getId());
            pstm.setDate(2,d1Sql);
            pstm.setDate(3,d2Sql);
            //rechercheMessage(pstm,lm);
            ResultSet rs = pstm.executeQuery();
            boolean trouve= false;
            while(rs.next()){
                trouve=true;
                int idMes = rs.getInt(1);
                String object = rs.getString(2);
                String contenu = rs.getString(3);
                LocalDate dateEnv = rs.getDate(4).toLocalDate();
                Message m = new Message(idMes,object,contenu,dateEnv);
                lm.add(m);
            }
            if(!trouve) System.out.println("aucun message trouvée");

        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
        return lm;

    }

    @Override
    public List<Message> OpeSpeMessDest(Employe employe) {
        List<Message> lm = new ArrayList<>();
        String query = "select * from APIMESSRECUID where id_empl = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,employe.getId());
            rechercheMessage(pstm,lm);
        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
        return lm;
    }




    @Override
    public List<Message> OpeSpeAppliMessNonLu(Employe employe) {
        List<Message> lm = new ArrayList<>();
        String updateQuery = "UPDATE APIEMPLMES SET LU = 'Y', DATE_OUVERTURE = SYSDATE WHERE ID_EMPLOYE = ?";
        String selectQuery = "SELECT * FROM VUE_MESSAGES_NON_LUS WHERE \"Recepteur\" = ?";

        try (PreparedStatement updatePstmt = dbConnect.prepareStatement(updateQuery);
             PreparedStatement selectPstmt = dbConnect.prepareStatement(selectQuery)) {

            //ON RECHERCHE LES MESS NON LU
            selectPstmt.setInt(1, employe.getId());
            rechercheMessage2(selectPstmt, lm);
            //ON LES UPDATE COMME LU AVEC LA DATE
            updatePstmt.setInt(1, employe.getId());
            updatePstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }

        return lm;
    }

    @Override
    public List<Message> OpeSpeMessRep(Employe employe) {
        List<Message> lm = new ArrayList<>();
        String query = "SELECT * FROM message_parent_view WHERE id_employe = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,employe.getId());
            rechercheMessage2(pstm,lm);
        }
        catch (SQLException e){
            System.out.println("erreur sql :" + e);
        }
        return lm;
    }

    @Override
    public Message OpeSpeAppliRepondre(Message orig,Message reponse){
        Message re = add(reponse);
        String query1 = "UPDATE APIMESSAGE SET ID_MESSAGE_PARENT = ? WHERE ID_MESSAGE = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(query1)) {
            pstmt.setInt(1,orig.getId());
            pstmt.setInt(2,re.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
        return re;
    }

    @Override
    public List<Message> OpeSpeMessEnv(Employe employe) {
        /*
        List<Message> lm = new ArrayList<>();
        String query = "SELECT * FROM APIMESSAGE WHERE id_employe = ?";
        String query1 = "SELECT * FROM APIEMPLMES WHERE id_message = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query);
                PreparedStatement pstm2 = dbConnect.prepareStatement(query1)) {
            pstm.setInt(1, employe.getId());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int idMess = rs.getInt(1);
                String object = rs.getString(2);
                String contenu = rs.getString(3);
                LocalDate dateEnv = rs.getDate(4).toLocalDate();
                pstm2.setInt(1, idMess);
                ResultSet rs2 = pstm2.executeQuery();
                while (rs2.next()){
                    char lu = rs2.getString(3).charAt(0);
                    LocalDate dateOuv = rs2.getDate(4).toLocalDate();
                    Message message = new Message(idMess,object,contenu,dateEnv);
                    if(lu == 'Y'){
                        message.setLu(true);
                        message.setDateRec(dateOuv);
                    }
                    lm.add(message);
                }
            }
        }
        catch (SQLException e){
            System.out.println("erreur sql :" + e);
        }
        return lm;

         */
        List<Message> lm = new ArrayList<>();
        String query = "SELECT * FROM API_EMPLOYES_MESSAGES WHERE id_emm = ?";
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
    //SGBD
    @Override
    public Message SgbdEnvoyerMessage(Message m, int idEm, int idRe) {
        String query ="{? = call Sgbd_envoyer_message(?,?,?)}";
        try(CallableStatement cs = dbConnect.prepareCall(query)){
            cs.registerOutParameter(1,java.sql.Types.INTEGER);
            cs.setString(2,m.getObjet());
            cs.setString(3,m.getContenu());
            cs.setInt(4,idEm);
            cs.executeUpdate();
            int idM = cs.getInt(1);
            m.setId(idM);
            enrEmplMess(idM,idRe);
        }catch(SQLException e){
            System.out.println("erreur sql :" + e);
            logger.error("erreur sql : "+e);
            return null;
        }
        return m;
    }

    @Override
    public Message SgbdEnvoyerReponse(Message m, int idE, Message message) {
        String query ="{? = call Sgbd_envoyer_reponse(?,?,?,?)}";
        try(CallableStatement cs = dbConnect.prepareCall(query)){
            cs.registerOutParameter(1,java.sql.Types.INTEGER);
            cs.setString(2,m.getObjet());
            cs.setString(3,m.getContenu());
            cs.setInt(4,message.getId());
            cs.setInt(5,idE);
            cs.executeUpdate();
            int idMess = cs.getInt(1);
            m.setId(idMess);
            enrEmplMess(idMess,message.getEmmetteur().getId());
        }catch(SQLException e){
            System.out.println("erreur sql :" + e);
            logger.error("erreur sql : "+e);
            return null;
        }
        return m;
    }
    @Override
    public boolean SgbdAsReponse(Message message) {
        boolean res;
        String query ="{? = call Sgbd_reponse(?)}";
        try(CallableStatement cs = dbConnect.prepareCall(query)){
            cs.registerOutParameter(1, Types.BOOLEAN);
            cs.setInt(2,message.getId());
            cs.executeUpdate();
            res = cs.getBoolean(1);
        }catch(SQLException e){
            System.out.println("erreur sql :" + e);
            logger.error("erreur sql : "+e);
            return false;
        }
        return res;
    }

    @Override
    public int SgbdNombreReponse(Message mess) {
        int res;
        String query ="{? = call Sgdb_nombres_reponses(?)}";
        try(CallableStatement cs = dbConnect.prepareCall(query)){
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2,mess.getId());
            cs.executeUpdate();
            res = cs.getInt(1);
        }catch(SQLException e){
            System.out.println("erreur sql :" + e);
            logger.error("erreur sql : "+e);
            return 0;
        }
        return res;
    }

    @Override
    public List<Message> SgbdReponseMessage(Message message) {
        List<Message> lm = new ArrayList<>();
        String query ="{? = call Sgbd_reponses_message(?)}";
        try(CallableStatement cs = dbConnect.prepareCall(query)){
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            cs.setInt(2,message.getId());
            cs.executeUpdate();
            ResultSet rs=(ResultSet) cs.getObject(1);
            while(rs.next()) {
                String contenu = rs.getString("CONTENU");
                String objet = rs.getString("OBJET");
                int id = rs.getInt("ID_MESSAGE");
                LocalDate date = rs.getDate("DATEENVOI").toLocalDate();
                //int idEmp = rs.getInt("ID_EMPLOYE");
                Message m = new Message(id,objet,contenu,date);
                lm.add(m);
            }
            }catch(SQLException e){
            System.out.println("erreur sql :" + e);
            logger.error("erreur sql : "+e);
            return null;
        }
        return lm;
    }



    //Methode utilitaire interne a cette classe
    private void rechercheMessage(PreparedStatement pstm,List<Message> lm) throws SQLException {
        ResultSet rs = pstm.executeQuery();
        boolean trouve= false;
        while(rs.next()){
            trouve=true;
            //int idMes = rs.getInt(1);
            String object = rs.getString(2);
            String contenu = rs.getString(3);
            LocalDate dateEnv = rs.getDate(4).toLocalDate();
            int idMess =rs.getInt(5);
            Message m = new Message(idMess,object,contenu,dateEnv);
            lm.add(m);
        }
        if(!trouve) System.out.println("aucun message trouvée");

    }
    private void rechercheMessage2(PreparedStatement pstm,List<Message> lm) throws SQLException {
        ResultSet rs = pstm.executeQuery();
        boolean trouve= false;
        while(rs.next()){
            trouve=true;
            int idMess = rs.getInt(1);
            String object = rs.getString(2);
            String contenu = rs.getString(3);
            LocalDate dateEnv = rs.getDate(4).toLocalDate();
            //int idMess =rs.getInt(5);
            Message m = new Message(idMess,object,contenu,dateEnv);
            lm.add(m);
        }
        if(!trouve) System.out.println("aucun message trouvée");

    }
    private Message find(ResultSet rs) throws SQLException {
        int idMess = rs.getInt(1);
        String objet = rs.getString(2);
        String contenu = rs.getString(3);
        LocalDate dateEnv = rs.getDate(4).toLocalDate();
        Message m = new Message(idMess,objet,contenu,dateEnv);
        return m;
    }
    //MEthode pour enregistrer recepteur dans la table inter
    private void enrEmplMess(int idMess,int idEmp){
        String query = "insert into APIEMPLMES(id_message,id_employe) values(?,?)";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idMess);
            pstm.setInt(2,idEmp);
            pstm.executeUpdate();
        }catch (SQLException e){
            logger.error("erreur sql : "+e);
        }
    }
}



