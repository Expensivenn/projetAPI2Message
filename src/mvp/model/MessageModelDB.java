package mvp.model;

import classemetiers.Employe;
import classemetiers.Message;
import mvp.presenter.EmployePresenter;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageModelDB implements DAOMessage,MessagesSpecial{
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
    public Message addMessage(Message message) {
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
    public boolean removeMessage(Message message) {
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
    public Message updateMessage(Message message) {
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
    public Message searchMess(int id) {
        String query = "SELECT * FROM APIMESSAGE WHERE id_message = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id);
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
    public List<Message> getMessages() {
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
    private Message find(ResultSet rs) throws SQLException {
        int idMess = rs.getInt(1);
        String objet = rs.getString(2);
        String contenu = rs.getString(3);
        LocalDate dateEnv = rs.getDate(4).toLocalDate();
        Message m = new Message(idMess,objet,contenu,dateEnv);
        return m;
    }
    public List<Integer> findIds(Message m){
        /*
        List<Integer> l = new ArrayList<>();
        int nombreRecepteurs = 1 ;
        String query = "SELECT * FROM APIMESSAGE WHERE id_message = ?";
        String query1="select * from APIEMPLMES WHERE id_message = ?";
        String query2="select COUNT(*) from APIEMPLMES WHERE id_message = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query);
             PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            pstm2.setInt(1,m.getId());
            int c = pstm2.executeUpdate();
            ResultSet rs2 = pstm2.executeQuery();
            rs2.next();
            if(c>0){
               nombreRecepteurs = rs2.getInt(1);
            }
            pstm.setInt(1, m.getId());
            int n = pstm.executeUpdate();
            ResultSet rs = pstm.executeQuery();
            rs.next();
            if(n>0){
                int idExp = rs.getInt(5);
                l.add(idExp);
                pstm1.setInt(1, m.getId());
                for(int i = 0;i<nombreRecepteurs;i++){
                    int x = pstm.executeUpdate();
                    ResultSet rs1 = pstm1.executeQuery();
                    rs1.next();
                    if(x>0){
                        int idRec = rs1.getInt(1);
                        l.add(idRec);
                }
                    return l;
                }

            }

        } catch (SQLException e) {
            //System.out.println("erreur sql :" + e);
            logger.error("erreur sql :"+e);
            return null;
        }
        return null;

         */
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
    public List<Message> messageDates(LocalDate d1, LocalDate d2, Employe employe) {
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
    public List<Message> messageDest(Employe employe) {
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
    @Override
    public List<Message> messagesNonLuEmpl(Employe employe) {
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
    public List<Message> messagesRep(Employe employe) {
        List<Message> lm = new ArrayList<>();
        String query = "SELECT * FROM message_parent_view WHERE id_employe = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,employe.getId());
            rechercheMessage(pstm,lm);
        }
        catch (SQLException e){
            System.out.println("erreur sql :" + e);
        }
        return lm;
    }

    @Override
    public Message repondre(Message orig,Message reponse){
        Message re = addMessage(reponse);
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
}



