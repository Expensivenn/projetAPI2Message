import java.time.LocalDate;

public class Message {

    /**
     * @author Cedric Ruitenbeek
     *
     *
     */
    private int id;
    private String objet;
    private String contenu;
    private LocalDate date;

    public Message(int id, String objet, String contenu, LocalDate date) {
        this.id = id;
        this.objet = objet;
        this.contenu = contenu;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
