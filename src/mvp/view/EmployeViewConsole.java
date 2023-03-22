package mvp.view;

import classemetiers.Bureau;
import classemetiers.Employe;
import mvp.presenter.EmployePresenter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeViewConsole implements EmployeViewInterface {
    private EmployePresenter presenter;
    private List<Employe> le;
    private Scanner sc = new Scanner(System.in);
    public EmployeViewConsole(){}

    @Override
    public void setPresenter(EmployePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Employe> employes) {
        this.le=employes;
        int i = 1;
        for(Employe e : le){
            System.out.println((i++)+"."+e);
        }
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("information:"+msg);
    }
    public void menu(){
        do{
            System.out.println("1.ajout 2.retrait 3.modifier 4.recherche id 5.fin");

            int ch = sc.nextInt();
            sc.skip("\n");
            switch(ch){
                case 1: ajouter();
                    break;
                case 2 : retirer();
                    break;
                case 3 : modifier();
                    break;
                case 4 : rechercher();
                    break;
                case 5 : System.exit(0);
            }
        }while(true);
    }

    private void retirer() {
        System.out.println("numéro de ligne : ");
        int nl =  sc.nextInt()-1;
        sc.skip("\n");
        if (nl >= 0) {
            Employe employe = le.get(nl);
            presenter.removeEmploye(employe);
        }
    }

    private void ajouter() {
        List<Integer> l = listeBureau(presenter.Bureau());
        Integer idBurTmp;
        do {
            System.out.println("Id bureau dans lequel il travaille :");
            idBurTmp = sc.nextInt();
            if (!l.contains(idBurTmp)) {
                System.out.println("Pas un id existant recommencez !");
            }
        } while (!l.contains(idBurTmp));
        sc.skip("\n");
        System.out.print("nom : ");
        String nom = sc.nextLine();
        System.out.print("prenom: ");
        String prenom = sc.nextLine();
        System.out.print("email: ");
        String email = sc.nextLine();
        presenter.addEmploye(new Employe(0,email,nom,prenom,idBurTmp));
    }
    private void modifier(){
        int idEmpTmp = readId();

        System.out.println("Modification des informations pour l'employé avec l'id : " + idEmpTmp);
        System.out.print("Nouveau nom : ");
        String nom = sc.nextLine();
        System.out.print("Nouveau prénom : ");
        String prenom = sc.nextLine();
        System.out.print("Nouveau email : ");
        String email = sc.nextLine();
        List<Integer> lBureau = listeBureau(presenter.Bureau());
        Integer idBurTmp;
        do {
            System.out.println("Nouvel id bureau dans lequel il travaille :");
            idBurTmp = sc.nextInt();
            if (!lBureau.contains(idBurTmp)) {
                System.out.println("Pas un id existant, recommencez !");
            }
        } while (!lBureau.contains(idBurTmp));
        sc.skip("\n");
        Employe e = new Employe(idEmpTmp,email,nom,prenom,idBurTmp);
        presenter.updateEmploye(e);

    }
    private void rechercher(){
        presenter.rechercher(readId());
    }
    private List<Integer> listeBureau(List<Bureau> b) {
        List<Integer> l = new ArrayList<>();
        for (Bureau bur : b){
            System.out.println("-"+bur.getId()+" "+bur.getSigle());
            l.add(bur.getId());
        }
        return l;
    }
    private List<Integer> listEmpId(List<Employe> e){
        List<Integer> l = new ArrayList<>();
        for (Employe emp : e){
            System.out.println("-"+emp.getId()+" "+ emp.getNom());
            l.add(emp.getId());
        }
        return l;
    }
    private int readId() {
        List<Integer> l = listEmpId(presenter.listeEmp());
        Integer idEmpTmp;
        do {
            System.out.println("Id de l'employé :");
            idEmpTmp = sc.nextInt();
            if (!l.contains(idEmpTmp)) {
                System.out.println("Pas un id existant, recommencez !");
            }
        } while (!l.contains(idEmpTmp));
        sc.skip("\n");
        return idEmpTmp;
    }
}

