package designpatterns.observer;

public class Employe extends Subject implements Observer {
    private String nom;
    private int id;
    private String mail;
    public Employe(int id, String nom, String mail) {
        this.id = id;
        this.nom = nom;
        this.mail = mail;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
        notifyObservers();
    }


    public String getNom() {
        return nom;
    }

    @Override
    public void update(String msg) {
        System.out.println(nom+" a été notifié que "+msg);
    }

    @Override
    public String getNotification() {
        return  "le nouveau mail de "+nom+" est : "+mail;
    }
}
