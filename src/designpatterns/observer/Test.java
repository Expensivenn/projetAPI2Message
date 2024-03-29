package designpatterns.observer;

public class Test {
    public static void main(String[] args) {
        Employe e1 = new Employe(1,"Remco","world-champion-2022@gmail.com");
        Employe e2 = new Employe(2,"Wout","wva@gmail.com");
        System.out.println("==========-MAIL ORIGINEL-==========");
        System.out.println("Mail originel Remco : "+e1.getMail());
        System.out.println("Mail originel Wout : "+e2.getMail());
        e1.addObserver(e2);
        e2.addObserver(e1);
        System.out.println("==========-APRES CHANGEMENT MAIL-==========");
        e2.setMail("wva-maillot-vert-2023@gmail.com");
        e1.setMail("remco-maillot-rose-2023@gmail.com");



    }
}
