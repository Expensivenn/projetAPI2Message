package classemetiers;

import java.util.List;
import java.util.Objects;

/**
 * La classe Employe représente un employé.
 * Un employé a un identifiant unique, un e-mail, un nom et un prénom.
 * @author Cedric
 */
public class Employe {

    // Les champs de la classe Employe.
    /**
     * id de l'employe
     */
    private int id;
    /**
     * email de l'employe
     */
    private String mail;
    /**
     * nom de l'employe
     */
    private String nom;
    /**
     * prenom de l'employe
     */
    private String prenom;
    /**
     * liste des messages reçu
     */
    private List<Message> messRecu;
    /**
     * liste des messages envoyé
     */
    private List<Message> messEnv;



    /**
     * Constructeur de la classe Employe.
     * @param id l'identifiant unique de l'employé.
     * @param mail l'e-mail de l'employé.
     * @param nom le nom de l'employé.
     * @param prenom le prénom de l'employé.
     */
    public Employe(int id, String mail, String nom, String prenom) {
        this.id = id;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Getter pour l'identifiant de l'employé.
     * @return l'identifiant de l'employé.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pour l'identifiant de l'employé.
     * @param id le nouvel identifiant de l'employé.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter pour l'e-mail de l'employé.
     * @return l'e-mail de l'employé.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Setter pour l'e-mail de l'employé.
     * @param mail le nouvel e-mail de l'employé.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Getter pour le nom de l'employé.
     * @return le nom de l'employé.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter pour le nom de l'employé.
     * @param nom le nouveau nom de l'employé.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter pour le prénom de l'employé.
     * @return le prénom de l'employé.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter pour le prénom de l'employé.
     * @param prenom le nouveau prénom de l'employé.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    /**
     * Getter pour la liste des messages reçu.
     * @return la liste des messages reçu.
     */
    public List<Message> getMessRecu() {
        return messRecu;
    }
    /**
     * Setter our la liste des messages reçu.
     * @param messRecu la liste des messages reçu
     */
    public void setMessRecu(List<Message> messRecu) {
        this.messRecu = messRecu;
    }
    /**
     * Getter pour la liste des messages envoyé.
     * @return la liste des messages encoyé.
     */
    public List<Message> getMessEnv() {
        return messEnv;
    }
    /**
     * Setter our la liste des messages envoyé.
     * @param messEnv la liste des messages envoyé
     */
    public void setMessEnv(List<Message> messEnv) {
        this.messEnv = messEnv;
    }
    /**
     * Egalité de deux employés basée sur l'id employé
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employe employe = (Employe) o;
        return id == employe.id;
    }
    /**
     * calcul du hashcode basé sur l'id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

