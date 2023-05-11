package mvp.view;

import classemetiers.Bureau;
import classemetiers.Employe;
import classemetiers.Message;
import mvp.presenter.EmployePresenter;
import mvp.presenter.SpecialEmployePresenter;
import utilitaires.Utilitaires;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EmployeViewConsole extends AbstractViewConsole<Employe> implements SpecialEmployeViewConsole {
    //private EmployePresenter presenter;
    //private List<Employe> le;
    private Scanner sc = new Scanner(System.in);
    public EmployeViewConsole(){}


    @Override
    protected void rechercher() {
        ((SpecialEmployePresenter)presenter).searchID(readId());
    }

    @Override
    protected void modifier() {
        int idEmpTmp = readId();
        System.out.println("Modification des informations pour l'employé avec l'id : " + idEmpTmp);
        System.out.print("Nouveau nom : ");
        String nom = sc.nextLine();
        System.out.print("Nouveau prénom : ");
        String prenom = sc.nextLine();
        System.out.print("Nouveau email : ");
        String email = sc.nextLine();
        List<Integer> lBureau = listeBureau(((SpecialEmployePresenter)presenter).Bureau());
        Integer idBurTmp;
        do {
            String id = Utilitaires.verifEntreeBoucle("[0-9]*","Nouveau Id du bureau dans lequel il travaille : ","Pas un nombre");
            idBurTmp = Integer.parseInt(id);
            if (!lBureau.contains(idBurTmp)) {
                System.out.println("Pas un id existant, recommencez !");
            }
        } while (!lBureau.contains(idBurTmp));
        Employe e = new Employe(idEmpTmp,email,nom,prenom,idBurTmp);
        presenter.update(e);
    }

    @Override
    protected void ajouter() {
        List<Integer> l = listeBureau(((SpecialEmployePresenter)presenter).Bureau());
        Integer idBurTmp;
        do {
            String id = Utilitaires.verifEntreeBoucle("[0-9]*","Id du bureau dans lequel il travaille : ","Pas un nombre");
            idBurTmp = Integer.parseInt(id);
            if (!l.contains(idBurTmp)) {
                System.out.println("Pas un id existant recommencez !");
            }
        } while (!l.contains(idBurTmp));
        System.out.print("nom : ");
        String nom = sc.nextLine();
        System.out.print("prenom: ");
        String prenom = sc.nextLine();
        System.out.print("email: ");
        String email = sc.nextLine();
        presenter.add(new Employe(0,email,nom,prenom,idBurTmp));
    }
    @Override
    protected void retirer() {
        int nl =  Utilitaires.choixListe(ldatas);
        if (nl != -5) {
            Employe employe = ldatas.get(nl-1);
            presenter.remove(employe);
        }
        else {
            affMsg("Liste vide pas de suppression possible !");
        }
    }

    @Override
    protected void special() { // ENONCE
        System.out.println("Choissisez un employé");
        Employe e = selectionner(ldatas);
        int ch;
        do {
            //int ch = Utilitaires.choixListe(Arrays.asList("\n1-Messages recu ","\n2-Messages envoyé","\n3-Menu principal"));
            System.out.println("\n1-Messages envoyé\n2-Messages envoyé entre deux dates\n3-Messages reçus\n4-Messages qui sont des réponses\n5-Menu principal");
            String choix = Utilitaires.verifEntreeBoucle("[1-5]", "Choix : ", "Pas entre 1 et 5");
            ch = Integer.parseInt(choix);
            switch (ch) {
                case 1:
                    ((SpecialEmployePresenter)presenter).OpSpeMessEnvoyes(e);
                    break;
                case 2:
                    LocalDate d1 = Utilitaires.lecDate();
                    LocalDate d2 = Utilitaires.lecDate();
                    ((SpecialEmployePresenter)presenter).OpSpeMessDates(e,d1,d2);
                    break;
                case 3:
                    ((SpecialEmployePresenter)presenter).OpSpeMessDest(e);
                    break;
                case 4:
                    ((SpecialEmployePresenter)presenter).OpSpeMessRep(e);
                case 5:
                    System.out.println("Retour");
                    return;

            }
        } while (true);
    }

    //APPLI
    @Override
    public void setListDatas2(List<Employe> employes,Employe e) {//APPLI
        this.ldatas=employes;
        menuAppli(e);
    }
    @Override
    public void menuAppli(Employe employe) { //MENU L'APPLI
        int ch;
        do {
            System.out.println("1.Consulter mes messages non-lus\n2.Consulter tout mes messages\n3.Consulter tout mes messages envoyés\n4.Repondre à un message\n5.Envoyer un nouveau message\n6.fin");
            String choix = Utilitaires.verifEntreeBoucle("[1-6]", "Choix : ", "Pas entre 1 et 6");
            ch = Integer.parseInt(choix);
            switch (ch) {
                case 1:
                    ((SpecialEmployePresenter)presenter).OpSpeAppliMessNonLu(employe);
                    break;
                case 2:
                    ((SpecialEmployePresenter)presenter).OpSpeAppliToutMessRecu(employe);
                    break;
                case 3:
                    ((SpecialEmployePresenter)presenter).OpSpeAppliToutMessEnv(employe);
                    break;
                case 4:
                    repondre(employe);
                    break;
                case 5:
                    ((SpecialEmployePresenter)presenter).OpSpeAppliEnvoyerMess(employe);
                    break;
                case 6:
                    System.out.println("Retour");
                    return;
            }
        } while (ch != 6);
    }
    @Override
    public Employe identification(){ //APPLI
        List<Employe> le = ((SpecialEmployePresenter)presenter).listeEmp();
        int idEmpTmp;
        int flag;
        do {
            flag = 0;
            String id = Utilitaires.verifEntreeBoucle("[0-9]*","Entrez votre id :","Pas un nombre");
            idEmpTmp = Integer.parseInt(id);
            for (Employe e:le) {
                if(e.getId() == idEmpTmp){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                System.out.println("Pas un id existant recommencez !");
            }
        } while(flag == 0);
        return ((SpecialEmployePresenter)presenter).searchID(idEmpTmp);

    }
    @Override
    public int nombrePersonnes(){
        System.out.println("A combien de personne voulez vous envoyer le message ?");
        String n = Utilitaires.verifEntreeBoucle("[0-9]*","Nombre : ","Pas un nombre");
        return Integer.parseInt(n);
    }

    //SPECIAL UTILISATION INTERNE A CETTE CLASSE
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
        List<Integer> l = listEmpId(((SpecialEmployePresenter)presenter).listeEmp());
        Integer idEmpTmp;
        do {
            String id = Utilitaires.verifEntreeBoucle("[0-9]*","Id de l'employé : ","Pas un nombre");
            idEmpTmp = Integer.parseInt(id);
            if (!l.contains(idEmpTmp)) {
                System.out.println("Pas un id existant, recommencez !");
            }
        } while (!l.contains(idEmpTmp));
        return idEmpTmp;
    }
    //METHODE AFFICHAGE
    @Override
    public void affMsgEnvList(List<Message> m) {
        int i = 1;
        for (Message mess: m) {

            StringBuilder sb = new StringBuilder();
            for (Employe e: mess.getRecepteurs()) {
                sb.append(e.getPrenom()+" ");
            }
            System.out.println("-------------------------------------------------------------------");
            if(mess.isLu()){
                System.out.printf("(Lu Ouvert le "+mess.getDateRec()+")\n");
                System.out.println(i+"--> Objet : "+mess.getObjet()+ "          Date Envoi : "+ mess.getDate()+"     Envoyé à "+ sb +"\n"+mess.getContenu());
            }
            else {
                System.out.printf("(Non lu)\n");
                System.out.println(i+"--> Objet : "+mess.getObjet()+ "          Date Envoi : "+ mess.getDate()+"     Envoyé à "+ sb +"\n"+mess.getContenu());
            }
            i++;

        }
    }
    @Override
    public void affMsgList(List<Message> m) {
        for (Message mess: m) {
            StringBuilder sb = new StringBuilder();
            for (Employe e: mess.getRecepteurs()) {
                sb.append(e.getPrenom()+" ");
            }
            System.out.println("-------------------------------------------------------------------");
            System.out.println("--> Objet : "+mess.getObjet()+ "          Date : "+ mess.getDate()+"     Envoyé à "+ sb + "Par "+ mess.getEmmetteur().getPrenom()+ "\n"+mess.getContenu()  );
        }
    }
    //OPE SPE APPLI
    @Override
    public void repondre(Employe employe) {
        List<Message> lm = ((SpecialEmployePresenter)presenter).getMessEmp(employe);
        int nl =  Utilitaires.choixListe(lm);
        if (nl != -5) {
            Message m = lm.get(nl-1);
            ((SpecialEmployePresenter)presenter).OpSpeAppliRepondre(m,employe);
        }
        else {
            affMsg("Liste vide pas de reponse possible !");
        }

    }
}

