package mvp;

import classemetiers.Bureau;
import classemetiers.Employe;
import classemetiers.Message;
import mvp.model.*;
import mvp.presenter.BureauPresenter;
import mvp.presenter.EmployePresenter;
import mvp.presenter.MessagePresenter;
import mvp.view.*;
import myconnections.DBConnection;
import utilitaires.Utilitaires;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GestEmpl {
    private  DAO<Employe> de;
    private ViewInterface<Employe> ev;
    private EmployePresenter ep;
    private DAO<Message> dm;
    private ViewInterface<Message> mv;
    private MessagePresenter mp;
    private DAO<Bureau> db;
    private ViewInterface<Bureau> bv;
    private BureauPresenter bp;

    public GestEmpl(){
        this.de = new EmployeModelDB();
        this.ev = new EmployeViewConsole();
        this.ep = new EmployePresenter(de,ev);//création et injection de dépendance

        this.dm  = new MessageModelDB();
        this.mv = new MessageViewConsole();
        this.mp = new MessagePresenter(dm,mv);

        this.db = new BureauModelDB();
        this.bv = new BureauViewConsole();
        this.bp = new BureauPresenter(db,bv);

        mp.setEmployePresenter(ep);
        ep.setBureauPresenter(bp);
        ep.setMessagePresenter(mp);
    }

    public void gestionAdmin(){
        //String code = Utilitaires.verifEntreeBoucle("[0-9][0-9][0-9][0-9]","Entrez le code admin","Code incorrect !");
            System.out.println("Menu Admin :");
            List<String> loptions = Arrays.asList("Employe","Messages","Bureau","EXIT");
            do {
                int ch = Utilitaires.choixListe(loptions);
                switch (ch){
                    case 1: ep.start();
                        break;
                    case 2 : mp.start();
                        break;
                    case 3: bp.start();
                        break;
                    case 4:
                        return;
                }
            }while(true);
        }



    public void gestionEmploye() {
        ep.startAppli();
    }
    public static void main(String[] args) {
        GestEmpl ge = new GestEmpl();
        List<String> loptions = Arrays.asList("Crud All + OpeSpe","Appli tel que demandée dans l'énoncé","Exit");
        do {
            int ch = Utilitaires.choixListe(loptions);
            switch (ch){
                case 1: ge.gestionAdmin();
                    break;
                case 2 : ge.gestionEmploye();
                    break;
                case 3: System.exit(0);
            }
        }while(true);
    }














}
