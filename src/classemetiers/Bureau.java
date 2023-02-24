package classemetiers;

import java.util.List;
import java.util.Objects;

/**
 * La classe Bureau représente un bureau.
 * Un bureau a un identifiant unique, un sigle et un numéro de téléphone.
 * @author Cedric
 */
public class Bureau {

    // Les champs de la classe Bureau.
    /**
     * id du bureau
     */
    private int id;
    /**
     * sigle du bureau
     */
    private String sigle;
    /**
     * tel du bureau
     */
    private String tel;
    /**
     * liste des employé du bureau
     */
    private List<Employe> listeEmp;


    /**
     * Constructeur de la classe Bureau.
     *
     * @param id    l'identifiant unique du bureau.
     * @param sigle le sigle du bureau.
     * @param tel   le numéro de téléphone du bureau.
     */
    public Bureau(int id, String sigle, String tel) {
        this.id = id;
        this.sigle = sigle;
        this.tel = tel;
    }

    /**
     * Getter pour l'identifiant du bureau.
     *
     * @return l'identifiant du bureau.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pour l'identifiant du bureau.
     *
     * @param id le nouvel identifiant du bureau.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter pour le sigle du bureau.
     *
     * @return le sigle du bureau.
     */
    public String getSigle() {
        return sigle;
    }

    /**
     * Setter pour le sigle du bureau.
     *
     * @param sigle le nouveau sigle du bureau.
     */
    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    /**
     * Getter pour le numéro de téléphone du bureau.
     *
     * @return le numéro de téléphone du bureau.
     */
    public String getTel() {
        return tel;
    }

    /**
     * Setter pour le numéro de téléphone du bureau.
     *
     * @param tel le nouveau numéro de téléphone du bureau.
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * Getter pour la liste d'employés du bureau.
     *
     * @return la liste d'employé du bureau.
     */
    public List<Employe> getListeEmp() {
        return listeEmp;
    }

    /**
     * Setter pour la liste d'employés du bureau.
     *
     * @param listeEmp la liste d'employés du bureau.
     */
    public void setListeEmp(List<Employe> listeEmp) {
        this.listeEmp = listeEmp;

    }
    /**
     * Egalité de deux bureaux basée sur l'id bureau
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bureau bureau = (Bureau) o;
        return id == bureau.id;
    }
    /**
     * calcul du hashcode basé sur l'id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

