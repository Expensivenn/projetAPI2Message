package jdvc;

import classemetiers.Bureau;
import classemetiers.Employe;
import classemetiers.Message;
import myconnections.DBConnection;
import utilitaires.Utilitaires;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GestEmpl {
    private Scanner sc = new Scanner(System.in);
    private Connection dbConnect;

    public void gestion() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");
        do {
            System.out.println("1.ajout\n2.recherche\n3.tous\n4.fin");
            System.out.println("choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    recherche();
                    break;
                case 3:
                    tous();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);

    }
    public void ajout() {

        System.out.print("nom :");
        String nom = sc.nextLine();
        System.out.print("prénom :");
        String prenom = sc.nextLine();
        System.out.print("email :");
        String email = sc.nextLine();
        List<Integer> l = listeBureau();
        Integer idBurTmp;
        do {
            System.out.println("Id bureau dans lequel il travaille :");
            idBurTmp = sc.nextInt();
            if (!l.contains(idBurTmp)) {
                System.out.println("Pas un id existant recommencez !");
            }
        } while (!l.contains(idBurTmp));


        sc.skip("\n");
        String query1 = "insert into APIEMPLOYE(email,prenom,nom,id_bureau) values(?,?,?,?)";
        String query2 = "select id_employe from APIEMPLOYE where nom= ? and prenom =? and email =?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setString(1, email);
            pstm1.setString(2, prenom);
            pstm1.setString(3, nom);
            pstm1.setInt(4, idBurTmp);

            int n = pstm1.executeUpdate();
            System.out.println(n + " ligne insérée");
            if (n == 1) {
                pstm2.setString(1, nom);
                pstm2.setString(2, prenom);
                pstm2.setString(3, email);
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int idclient = rs.getInt(1);
                    System.out.println("id de l'employé = " + idclient);
                } else System.out.println("record introuvable");
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
    }
    public void recherche() {
        List<Integer> l = listeEmploye();
        Integer idEmpTmp;
        do {
            System.out.println("Id de l'employé :");
            idEmpTmp = sc.nextInt();
            if (!l.contains(idEmpTmp)) {
                System.out.println("Pas un id existant recommencez !");
            }
        } while (!l.contains(idEmpTmp));
        String query = "select * from APIEMPLOYE where id_employe = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idEmpTmp);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                String nom = rs.getString(4);
                String prenom = rs.getString(3);
                int id = rs.getInt(1);
                String email = rs.getString(2);
                Employe emp = new Employe(id,email,nom,prenom);
                System.out.println(emp);
                opSpeciales(emp);
            }
            else System.out.println("record introuvable");
        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }

    }
    private void opSpeciales(Employe employe) {
        do {
            System.out.println("1.messages envoyé entre deux dates\n2.messages reçus\n3.reponses envoyées\n4.menu principal");
            System.out.println("choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    messRecDate(employe);
                    break;
                case 2:
                    messRec(employe);
                    break;
                case 3:
                    messDest(employe);
                    break;

                case 4: return;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);

    }
    private void messRecDate(Employe employe) {
        System.out.println("Entrez première date :");
        LocalDate d1 = Utilitaires.lecDate();
        System.out.println("Entrez deuxième date :");
        LocalDate d2 = Utilitaires.lecDate();
        java.sql.Date d1Sql = java.sql.Date.valueOf(d1);
        java.sql.Date d2Sql = java.sql.Date.valueOf(d2);
        System.out.println(d1Sql);
        String query = "select * from APIMESSAGE where id_dest = ? AND dateenvoi BETWEEN ? AND ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,employe.getId());
            pstm.setDate(2,d1Sql);
            pstm.setDate(3,d2Sql);
            rechercheMessage(pstm);
        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
    }
    private void messRec(Employe employe) {
        String query = "select * from APIMESSAGE where id_dest = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,employe.getId());
            rechercheMessage(pstm);
        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
    }
    private void messDest(Employe employe) {
        String query = "select * from APIMESSAGE where id_employe = ? AND id_message_1 IS NOT NULL ";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,employe.getId());
            rechercheMessage(pstm);
        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
    }
    private void rechercheMessage(PreparedStatement pstm) throws SQLException {
            ResultSet rs = pstm.executeQuery();
            boolean trouve= false;
            while(rs.next()){
                trouve=true;
                int idMes = rs.getInt(1);
                String object = rs.getString(2);
                String contenu = rs.getString(3);
                LocalDate dateEnv = rs.getDate(4).toLocalDate();
                Message m = new Message(idMes,object,contenu,dateEnv);
                System.out.println(m);
            }
            if(!trouve) System.out.println("aucun message trouvée");

    }

    private List<Integer> listeBureau() {
        List<Integer> l = new ArrayList<>();
        String query="select * from APIBUREAU";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int idBur = rs.getInt(1);
                l.add(idBur);
                String sigle = rs.getString(2);
                String tel = rs.getString(3);
                Bureau b = new Bureau(idBur,sigle,tel);
                System.out.println("-"+b.getId()+" "+b.getSigle());
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
        return l;
    }
    private List<Integer> listeEmploye() {
        List<Integer> l = new ArrayList<>();
        String query="select * from APIEMPLOYE";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int idEmp = rs.getInt(1);
                l.add(idEmp);
                String mail = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                Employe e = new Employe(idEmp,mail,nom,prenom);
                System.out.println("-"+e.getId()+" "+e.getNom());
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
        return l;
    }
    private void tous() {
        String query="select * from APIEMPLOYE";
        try(Statement stm = dbConnect.createStatement()) {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()){
                int idEmp = rs.getInt(1);
                String email = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                int idBur = rs.getInt(5);
                Employe e = new Employe(idEmp,email,nom,prenom);
                System.out.println(e);
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
    }
    public static void main (String[] args){
        GestEmpl g = new GestEmpl();
        g.gestion();
    }

}
