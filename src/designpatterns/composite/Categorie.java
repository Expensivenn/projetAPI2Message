package designpatterns.composite;

import java.util.ArrayList;
import java.util.List;
public class Categorie extends Element{
    private List<Element> elements;
    private String nom;
    public Categorie(int id,String nom) {
        super(id);
        this.nom = nom;
        elements = new ArrayList<>();
    }
    public String getNom(){
        return this.nom;
    }
    public void add(Element element) {
        elements.add(element);
    }

    public List<Element> getElements() {
        return elements;
    }
}

