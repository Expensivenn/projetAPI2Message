package mvp.view;

import classemetiers.Bureau;
import mvp.presenter.Presenter;

import java.util.*;

import static utilitaires.Utilitaires.*;

public abstract class AbstractViewConsole<T> implements ViewInterface<T> {
    protected Presenter<T> presenter;
    protected List<T> ldatas;
    protected Scanner sc = new Scanner(System.in);

    @Override
    public void setPresenter(Presenter<T> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<T> ldatas) {
        this.ldatas = ldatas;
        //affListe(ldatas);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("information:" + msg);
    }

    @Override
    public void affList(List lelts) {
        affListe(lelts);
    }

    public void menu() {
        List options = new ArrayList<>(Arrays.asList("ajouter", "retirer", "rechercher","modifier","special","SGBD","fin"));
        do {
            int ch = choixListe(options);
            switch (ch) {
                case 1:
                    ajouter();
                    break;
                case 2:
                    retirer();
                    break;
                case 3:
                    rechercher();
                    break;
                case 4:
                    modifier();
                    break;
                case 5:
                    special();
                    break;
                case 6:
                    sgbd();
                case 7:
                    return;
            }
        } while (true);
    }
    protected void retirer() {
        int choix = choixElt(ldatas);
        T elt= ldatas.get(choix-1);
        presenter.remove(elt);
        ldatas=presenter.getAll();//rafraichissement
        affListe(ldatas);
    }
    @Override
    public T selectionner(List<T> l) {
        int nl = choixListe(l);
        T elt = l.get(nl - 1);
        return elt;
    }
    protected abstract void rechercher();

    protected  abstract void modifier();

    protected abstract  void ajouter();
    protected abstract void special();
    protected  void sgbd(){
        System.out.println("Aucune(s) méthode(s) !");
    };
}
