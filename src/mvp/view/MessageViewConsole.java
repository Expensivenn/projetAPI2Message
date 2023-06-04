package mvp.view;


import classemetiers.Message;

import mvp.presenter.SpecialMessagePresenter;
import utilitaires.Utilitaires;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaires.choixListe;

public class MessageViewConsole extends AbstractViewConsole<Message> implements SpecialMessageViewConsole{

    private Scanner sc = new Scanner(System.in);
    public MessageViewConsole(){}

    @Override
    public void ajouter(){
        System.out.print("Objet : ");
        String objet = sc.nextLine();
        System.out.print("Contenu : ");
        String contenu = sc.nextLine();
        LocalDate date = LocalDate.now();
        Message m = new Message(1,objet,contenu,date);
        presenter.add(m);
    }

    @Override
    protected void special() {
        System.out.println("Pas d'ope spe");
    }
    @Override
    protected void sgbd(){
        List options = new ArrayList<>(Arrays.asList("Envoyer Message", "Envoyer Reponse", "Voir si un message donné a une ou plusieurs réponse(s)","Voir le nombre de réponses a un message","Voir les réponse a un message donné ","fin"));
        do {
            int ch = choixListe(options);
            switch (ch) {
                case 1:
                    SgbdEnvoyerMessage();
                    break;
                case 2:
                    SgbdEnvoyerReponse();
                    break;
                case 3:
                    SgbdAsReponse();
                    break;
                case 4:
                    SgbdNombreReponse();
                    break;
                case 5:
                    SgbdReponseMessage();
                    break;
                case 6 :
                    return;
            }
        } while (true);

    }
    @Override
    public Message saisieMessage(){
        System.out.print("Objet : ");
        String objet = sc.nextLine();
        System.out.print("Contenu : ");
        String contenu = sc.nextLine();
        LocalDate date = LocalDate.now();
        Message m = new Message(-1,objet,contenu,date);
        return m;
    }
    @Override
    public void retirer() {
        int nl = Utilitaires.choixListe(ldatas);
        if (nl != -5) {
            Message message = ldatas.get(nl-1);
            presenter.remove(message);
        }
        else {
            affMsg("Liste vide pas de suppression possible !");
        }
    }
    @Override
    public void modifier(){
        int nl = Utilitaires.choixListe(ldatas);
        if(nl != -5){
            Message message = ldatas.get(nl-1);
            int idMess = message.getId();
            System.out.println("Modification des informations pour le message avec l'id : " + idMess);
            String objet= Utilitaires.modifyIfNotBlank("Objet actuel : ",message.getObjet());
            String contenu = Utilitaires.modifyIfNotBlank("Contenu actuel : ",message.getContenu());
            message.setContenu(contenu);
            message.setObjet(objet);
            presenter.update(message);
        }
        else {
            affMsg("Liste vide pas de modification possible !");
        }

    }
    @Override
    public void rechercher(){
        int nl = Utilitaires.choixListe(ldatas);
        if(nl != -5){
            presenter.search(ldatas.get(nl-1));
        }
        else {
            affMsg("Liste vide recherche impossible !");
        }
    }
    //SGBD
    @Override
    public void SgbdEnvoyerMessage(){
        Message m = saisieMessage();
        String idE = Utilitaires.verifEntreeBoucle("[0-9]*","Id de l'employé a qui vous voulez envoyer le message : ","Pas un nombre");
        int idEm = Integer.parseInt(idE);
        String idR = Utilitaires.verifEntreeBoucle("[0-9]*","Id de l'employé a qui vous voulez envoyer le message : ","Pas un nombre");
        int idRe = Integer.parseInt(idR);
        ((SpecialMessagePresenter)presenter).SgbdEnvoyerMessage(m,idEm,idRe);

    }
    @Override
    public void SgbdEnvoyerReponse(){
        Message m = saisieMessage();
        String idE = Utilitaires.verifEntreeBoucle("[0-9]*","Votre id : ","Pas un nombre");
        int idEmp = Integer.parseInt(idE);
        System.out.println("A quel message voulez vous repondre ?");
        Message mes = presenter.selection();
        ((SpecialMessagePresenter)presenter).SgbdEnvoyerReponse(m,idEmp,mes);

    }
    @Override
    public void SgbdAsReponse(){
        System.out.println("De quel message voulez savoir si il a des réponses ?");
        Message mes = presenter.selection();
        ((SpecialMessagePresenter)presenter).SgbdAsReponse(mes);
    }
    @Override
    public void SgbdNombreReponse(){
        System.out.println("De quel message voulez vous voir le nombre de réponses ?");
        Message mes = presenter.selection();
        ((SpecialMessagePresenter)presenter).SgbdNombreReponse(mes);
    }
    @Override
    public void SgbdReponseMessage(){
        System.out.println("De quel message voulez vous voir les réponses ?");
        Message mes = presenter.selection();
        ((SpecialMessagePresenter)presenter).SgbdReponseMessage(mes);
    }

}
