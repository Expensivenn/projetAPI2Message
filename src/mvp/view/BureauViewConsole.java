package mvp.view;

import classemetiers.Bureau;
import classemetiers.Employe;
import classemetiers.Message;
import mvp.presenter.BureauPresenter;
import mvp.presenter.EmployePresenter;
import mvp.presenter.MessagePresenter;
import mvp.presenter.Presenter;
import utilitaires.Utilitaires;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BureauViewConsole extends AbstractViewConsole<Bureau> implements SpecialBureauViewConsole {

    private Presenter<Bureau> presenter;
    private Scanner sc = new Scanner(System.in);
    @Override
    public void setPresenter(Presenter<Bureau> presenter) {
        this.presenter = presenter;
    }

    @Override
    public Bureau selectionner(List<Bureau> l) {
        return null;
    }

    @Override
    protected void rechercher() {
        int nl = Utilitaires.choixListe(ldatas);
        if(nl != -5){
            presenter.search(ldatas.get(nl-1));
        }
        else {
            affMsg("Liste vide recherche impossible !");
        }
    }

    @Override
    protected void modifier() {
        int nl = Utilitaires.choixListe(ldatas);
        if(nl != -5){
            Bureau bureau = ldatas.get(nl-1);
            int idBur = bureau.getId();
            System.out.println("Modification des informations pour le bureau avec l'id : " + idBur);
            String sigle = Utilitaires.modifyIfNotBlank("Sigle actuel : ",bureau.getSigle());
            String tel = Utilitaires.modifyIfNotBlankTel("Tel actuel : ",bureau.getTel());
            bureau.setSigle(sigle);
            bureau.setTel(tel);
            presenter.update(bureau);
        }
        else {
            affMsg("Liste vide pas de modification possible !");
        }
    }
    @Override
    protected void retirer(){
        int nl = Utilitaires.choixListe(ldatas);
        if (nl != -5) {
            Bureau bureau = ldatas.get(nl-1);
            presenter.remove(bureau);
        }
        else {
            affMsg("Liste vide pas de suppression possible !");
        }
    }
    @Override
    protected void ajouter() {
        System.out.print("Sigle : ");
        String sigle = sc.nextLine();
        String tel = Utilitaires.verifEntreeBoucle("0[1-9][0-9]{8}","Numero de tel  :","Pas un numéro de tel valable ! --> format : (0 --- -- -- --)");
        Bureau b = new Bureau(0,sigle,tel);
        presenter.add(b);
    }

    @Override
    protected void special() {

    }
    @Override
    public void menu() {
        int ch;
        do {
            System.out.println("1.ajout\n2.retrait\n3.modifier\n4.recherche id\n5.fin\n");
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



}
