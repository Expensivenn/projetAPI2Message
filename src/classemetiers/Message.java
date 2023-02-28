package classemetiers;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Cette classe définit un modèle de message avec un identifiant, un objet, un contenu et une date.
 * @author Cedric
 *
 */
public class Message {
    /**
     * id du Message
     */
    private int id;
    /**
     * objet du Message
     */
    private String objet;
    /**
     * contenu du Message
     */
    private String contenu;
    /**
     * date du Message
     */
    private LocalDate date;

    /**
     * message précedent celui-ci
     */
    private Message message;
    /**
     * Employe qui envoie ce message
     */
    private Employe emmetteur;
    /**
     * Employe qui reçoi ce message
     */
    private Employe recepteur;

    /**
     * Crée un nouveau message avec l'identifiant, l'objet, le contenu et la date donnés.
     * @param id l'identifiant du message
     * @param objet l'objet du message
     * @param contenu le contenu du message
     * @param date la date du message
     */
    public Message(int id, String objet, String contenu, LocalDate date) {
        this.id = id;
        this.objet = objet;
        this.contenu = contenu;
        this.date = date;
    }

    /**
     * Renvoie l'identifiant du message.
     * @return l'identifiant du message
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du message.
     * @param id le nouvel identifiant du message
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Renvoie l'objet du message.
     * @return l'objet du message
     */
    public String getObjet() {
        return objet;
    }

    /**
     * Modifie l'objet du message.
     * @param objet le nouvel objet du message
     */
    public void setObjet(String objet) {
        this.objet = objet;
    }

    /**
     * Renvoie le contenu du message.
     * @return le contenu du message
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * Modifie le contenu du message.
     * @param contenu le nouveau contenu du message
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    /**
     * Renvoie la date du message.
     * @return la date du message
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Modifie la date du message.
     * @param date la nouvelle date du message
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
    /**
     * Getter pour le message precedent celui-ci.
     * @return le message
     */
    public Message getMessage() {
        return message;
    }
    /**
     * Setter pour le message precedent celui-ci.
     * @param  message le message precedent celui-ci.
     */
    public void setMessage(Message message) {
        this.message = message;
    }
    /**
     * Getter pour l'emmeteur du message.
     * @return l'emmeteur
     */
    public Employe getEmmetteur() {
        return emmetteur;
    }
    /**
     * Setter pour le emmeteur du message.
     * @param  emmetteur le emmeteur du message.
     */
    public void setEmmetteur(Employe emmetteur) {
        this.emmetteur = emmetteur;
    }
    /**
     * Getter pour le recepteur du message.
     * @return le recepteur
     */
    public Employe getRecepteur() {
        return recepteur;
    }
    /**
     * Setter pour le recepteur du message.
     * @param  recepteur le recepteur du message.
     */
    public void setRecepteur(Employe recepteur) {
        this.recepteur = recepteur;
    }

    /**
     * Egalité de deux messages basée sur l'id Message
     * @param o l'autre objet
     * @return égalité ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id;
    }
    /**
     * calcul du hashcode basé sur l'id
     * @return valeur du hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    /**
     * méthode toString
     *
     * @return informations du message
     */
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", objet='" + objet + '\'' +
                ", contenu='" + contenu + '\'' +
                ", date=" + date +
                ", message=" + message +
                ", emmeteur=" + emmetteur +
                ", recepteur=" + recepteur+
                '}';
    }
}

