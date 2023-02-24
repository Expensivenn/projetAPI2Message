package jdvc;

import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ex01 {
    public void demo(){
        Scanner sc = new Scanner(System.in);
        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");
        System.out.println("id bureau des employés recherche : ");
        String idBur = sc.nextLine();
        int id = Integer.parseInt(idBur);
        try (Statement stmt = dbConnect.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT *  FROM APIEMPLOYE WHERE ID_BUREAU = " + id );){
            boolean trouve = false;
            while (rs.next()) {
                trouve = true;
                String email = rs.getString("EMAIL");
                int n = rs.getInt("ID_EMPLOYE");
                String nom = rs.getString("NOM");
                System.out.println( nom + " id : " + n + " email : " + email);
            }
            if (!trouve) {
                System.out.println("client inconnu");
            }

        } catch (SQLException e) {
            System.out.println("erreur SQL =" + e);
        }
        DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        ex01 pgm = new ex01();
        pgm.demo();
    }
}

