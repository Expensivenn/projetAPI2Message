package designpatterns.builder;

import classemetiers.Employe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Employe e1 = new Employe(1,"remco.evenepoel@gmail.com","Evenepoel","Remco",5);
        Employe e2 = new Employe(2,"vanaert.wout@gmail.com","Van Aert","Wout",6);
        Employe e3 = new Employe(3,"vanderpoel.mathieu@gmail.com","Van Der Poel","mathieu",5);
        LocalDate d1 = LocalDate.of(2022, 9, 5);
        //LocalDate d2 = LocalDate.of(2022, 8, 5);
        List<Employe> le = new ArrayList<>();
        le.add(e2);
        le.add(e3);
        try {
            Message m1 = new Message.MessageBuilder().
                    setId(1).
                    setObjet("Test").
                    setContenu("Ceci est un test").
                    setDate(d1).
                    setRecepteurs(le).
                    setEmmetteur(e1).
                    build();
            System.out.println(m1);
        } catch (Exception e) {
            System.out.println("erreur "+e);
        }
        try {
            Message m2 = new Message.MessageBuilder().
                    setId(1).
                    setObjet("Test2").
                    setDate(d1).
                    setRecepteurs(le).
                    build();
            System.out.println(m2);
        } catch (Exception e) {
            System.out.println("erreur "+e);
        }

    }
}
