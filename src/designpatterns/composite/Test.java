package designpatterns.composite;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        Employe e1 = new Employe(1, "Remco");
        Employe e2 = new Employe(2, "Wout");
        Employe e3 = new Employe(3, "Julian");

        Categorie c1 = new Categorie(4,"JAVA");
        Categorie c2 = new Categorie(5,"C#");

        c1.add(e1);
        c1.add(e2);
        c2.add(e3);

        List<Element> elements = new ArrayList<>();
        elements.add(c1);
        elements.add(c2);

        for (Element element : elements) {
            if (element instanceof Categorie) {
                System.out.println("Catégorie " + element.getId() + "-"+((Categorie)element).getNom() + ":");
                int total = 0;
                for (Element e : ((Categorie) element).getElements()) {
                    if (e instanceof Employe) {
                        System.out.println(" - " + ((Employe) e).getNom());
                        total++;
                    }
                }
                System.out.println("Nombre total d'employés dans cette catégorie : " + total);
            }
        }
    }
}
