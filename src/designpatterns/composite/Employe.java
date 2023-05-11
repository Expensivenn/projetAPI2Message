package designpatterns.composite;

public class Employe extends Element{
    private String nom;

    public Employe(int id, String nom) {
        super(id);
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}
