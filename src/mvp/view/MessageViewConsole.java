package mvp.view;

import classemetiers.Employe;
import classemetiers.Message;
import mvp.presenter.EmployePresenter;
import mvp.presenter.MessagePresenter;
import utilitaires.Utilitaires;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MessageViewConsole implements MessageViewInterface{

    private MessagePresenter presenter;
    private List<Message> lm;
    private Scanner sc = new Scanner(System.in);
    public MessageViewConsole(){}

    @Override
    public void setPresenter(MessagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Message> messages) {
        this.lm=messages;
        int i = 1;
        for(Message e : lm){
            System.out.println((i++)+"."+e);
        }
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : "+msg);
    }
    public void menu() {
        int ch;
        do {
            System.out.println("1.ajout\n2.retrait\n3.modifier\n4.recherche\n5.fin\n");
            String choix = Utilitaires.verifEntreeBoucle("[1-5]", "Choix : ", "Pas entre 1 et 5");
            ch = Integer.parseInt(choix);

            switch (ch) {
                case 1:
                    ajouter();
                    break;
                case 2:
                    retirer();
                    break;
                case 3:
                    modifier();
                    break;
                case 4:
                    rechercher();
                    break;
                case 5:
                    System.out.println("Retour");
                    return;
            }
        } while (ch != 5);
    }
    public void ajouter(){
        System.out.print("Objet : ");
        String objet = sc.nextLine();
        System.out.print("Contenu : ");
        String contenu = sc.nextLine();
        //String objet = Utilitaires.verifEntreeBoucle("[^a-zA-Z]","Objet : ","Ne doit contenir que des lettres");
        //String contenu = Utilitaires.verifEntreeBoucle(".","Contenu : ","");
        LocalDate date = LocalDate.now();
        Message m = new Message(1,objet,contenu,date);
        presenter.addMessage(m);
    }
    public Message saisieMessage(){
        System.out.print("Objet : ");
        String objet = sc.nextLine();
        System.out.print("Contenu : ");
        String contenu = sc.nextLine();
        //String objet = Utilitaires.verifEntreeBoucle("[^a-zA-Z]","Objet : ","Ne doit contenir que des lettres");
        //String contenu = Utilitaires.verifEntreeBoucle(".","Contenu : ","");
        LocalDate date = LocalDate.now();
        Message m = new Message(-1,objet,contenu,date);
        return m;
    }
    private void retirer() {
        int nl = Utilitaires.choixListe(lm);
        if (nl != -5) {
            Message message = lm.get(nl-1);
            presenter.removeMessage(message);
        }
        else {
            affMsg("Liste vide pas de suppression possible !");
        }
    }
    public void modifier(){
        int nl = Utilitaires.choixListe(lm);
        if(nl != -5){
            Message message = lm.get(nl-1);
            int idMess = message.getId();
            System.out.println("Modification des informations pour le message avec l'id : " + idMess);
            String objet= Utilitaires.modifyIfNotBlank("Objet actuel : ",message.getObjet());
            String contenu = Utilitaires.modifyIfNotBlank("Contenu actuel : ",message.getContenu());
            message.setContenu(contenu);
            message.setObjet(objet);
            presenter.updateMessage(message);
        }
        else {
            affMsg("Liste vide pas de modification possible !");
        }

    }

    public void rechercher(){
        int nl = Utilitaires.choixListe(lm);
        if(nl != -5){
            presenter.rechercher(lm.get(nl-1));
        }
        else {
            affMsg("Liste vide recherche impossible !");
        }
    }

}
