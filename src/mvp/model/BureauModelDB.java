package mvp.model;

import classemetiers.Bureau;
import classemetiers.Message;
import mvp.presenter.BureauPresenter;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BureauModelDB  implements BureauSpecial,DAO<Bureau> {
    private Connection dbConnect;
    private static final Logger logger = LogManager.getLogger(MessageModelDB.class);
    public BureauModelDB(){
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
    public Bureau add(Bureau bureau) {
        String query1 = "insert into APIBUREAU(sigle,tel) values(?,?)";
        String query2 = "select id_bureau from APIBUREAU where sigle = ? and tel = ?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setString(1, bureau.getSigle());
            pstm1.setString(2, bureau.getTel());
            int n = pstm1.executeUpdate();
            if (n == 1) {
                pstm2.setString(1, bureau.getSigle());
                pstm2.setString(2, bureau.getTel());
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int idBur = rs.getInt(1);
                    bureau.setId(idBur);
                    return bureau;
                }
                else {
                    logger.error("record introuvable");
                    //System.err.println("record introuvable");
                    return null;
                }
            }
            else{return null;}

        } catch (SQLException e) {
            logger.error("erreur sql :"+e);
            return null;
        }
    }


    @Override
    public boolean remove(Bureau bureau) {
        String query = "delete from APIBUREAU where id_bureau = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,bureau.getId());
            int n = pstm.executeUpdate();
            if(n!=0) return true;
            return false;

        } catch (SQLException e) {
            logger.error("erreur sql :"+e);
            return false;
        }
    }

    @Override
    public Bureau update(Bureau bureau) {
        String query = "UPDATE APIBUREAU SET sigle=?, tel=? WHERE id_bureau=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, bureau.getSigle());
            pstm.setString(2, bureau.getTel());
            pstm.setInt(3,bureau.getId());
            int n = pstm.executeUpdate();
            if(n>0){
                System.out.println(n + " ligne(s) mise(s) à jour");
            }
        } catch (SQLException e) {
            //System.out.println("erreur sql :" + e);
            logger.error("erreur sql :"+e);
            return null;
        }
        return bureau;
    }

    @Override
    public Bureau read(Bureau rech) {
        String query = "SELECT * FROM APIBUREAU WHERE id_bureau = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, rech.getId());
            int n = pstm.executeUpdate();
            ResultSet rs = pstm.executeQuery();
            rs.next();
            if(n>0){
                String sigle = rs.getString(2);
                String tel = rs.getString(3);
                Bureau b = new Bureau(rech.getId(),sigle,tel);
                return b;
            }

        } catch (SQLException e) {
            //System.out.println("erreur sql :" + e);
            logger.error("erreur sql :"+e);
            return null;
        }
        return null;
    }

    @Override
    public List<Bureau> getAll() {
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
    //METHODE BUREAU SPECIAL
    public Bureau searchBur(int id) {
        String query = "SELECT * FROM APIBUREAU WHERE id_bureau = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id);
            int n = pstm.executeUpdate();
            ResultSet rs = pstm.executeQuery();
            rs.next();
            if(n>0){
                String sigle = rs.getString(2);
                String tel = rs.getString(3);
                Bureau b = new Bureau(id,sigle,tel);
                return b;
            }

        } catch (SQLException e) {
            //System.out.println("erreur sql :" + e);
            logger.error("erreur sql :"+e);
            return null;
        }
        return null;
    }


}
