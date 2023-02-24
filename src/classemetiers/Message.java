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
     * Egalité de deux messages basée sur l'id Message
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
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

