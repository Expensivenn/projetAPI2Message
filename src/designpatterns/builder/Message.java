package designpatterns.builder;

import classemetiers.Employe;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Message {
    protected int id;
    protected String objet;
    protected String contenu;
    protected LocalDate date;
    protected Message message;
    protected Employe emmetteur;
    protected boolean lu;
    protected LocalDate dateRec;
    protected List<Employe> recepteurs;

    private Message(MessageBuilder mb) {
        this.id = mb.id;
        this.objet = mb.objet;
        this.contenu = mb.contenu;
        this.date = mb.date;
        this.message = mb.message;
        this.emmetteur = mb.emmetteur;
        this.lu = mb.lu;
        this.dateRec = mb.dateRec;
        this.recepteurs = mb.recepteurs;
    }
    public int getId() {
        return id;
    }

    public String getObjet() {
        return objet;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDate getDate() {
        return date;
    }

    public Message getMessage() {
        return message;
    }

    public Employe getEmmetteur() {
        return emmetteur;
    }

    public Employe getRecepteurIndex(int i) {
        return recepteurs.get(i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id;
    }

    public List<Employe> getRecepteurs() {
        return recepteurs;
    }
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", objet='" + objet + '\'' +
                ", contenu='" + contenu + '\'' +
                ", date=" + date +
                ", message=" + message +
                ", emmetteur=" + emmetteur +
                ", lu=" + lu +
                ", dateRec=" + dateRec +
                ", recepteurs=" + recepteurs +
                '}';
    }
    public static class MessageBuilder {
        protected int id;
        protected String objet;
        protected String contenu;
        protected LocalDate date;
        protected Message message;
        protected Employe emmetteur;
        protected boolean lu;
        protected LocalDate dateRec;
        protected List<Employe> recepteurs;
        public MessageBuilder setId(int id){
            this.id=id;
            return this;
        }
        public MessageBuilder setObjet(String objet){
            this.objet = objet;
            return this;
        }
        public MessageBuilder setContenu(String contenu) {
            this.contenu = contenu;
            return this;
        }

        public MessageBuilder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public MessageBuilder setMessage(Message message) {
            this.message = message;
            return this;
        }

        public MessageBuilder setEmmetteur(Employe emmetteur) {
            this.emmetteur = emmetteur;
            return this;
        }

        public MessageBuilder setLu(boolean lu) {
            this.lu = lu;
            return this;
        }

        public MessageBuilder setDateRec(LocalDate dateRec) {
            this.dateRec = dateRec;
            return this;
        }

        public MessageBuilder setRecepteurs(List<Employe> recepteurs) {
            this.recepteurs = recepteurs;
            return this;
        }
        public Message build() throws Exception{
            if(objet==null || contenu==null || date==null || recepteurs==null) throw new
                    Exception("informations de construction incomplètes");
            return new Message(this);
        }

    }
}
