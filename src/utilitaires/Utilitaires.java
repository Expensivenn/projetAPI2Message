package utilitaires;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Utilitaires {
    private static Scanner sc = new Scanner(System.in);

    public static LocalDate lecDate(){
        String regexDate = "^((0?[1-9])|([12][0-9])|(3[01]))-((0?[1-9])|(1[0-2]))-(\\d{4})$";
        String msg = "Veuillez entrer une date au format jj-mm-aaaa : ";
        String erreur = "La date doit être au format jj-mm-aaaa !";
        String date = verifEntreeBoucle(regexDate, msg, erreur);
        return convertToDate(date);
    }
    public static int choixListe(List l){
        affListe(l);
        return choixElt(l);
    }
    public static int choixElt(List l){
        int choix;
        if(l.size() == 0){
            return -5;
        }
        do {
            String ch = Utilitaires.verifEntreeBoucle("[0-9]*","Choix : ","Pas un nombre!");
            choix = Integer.parseInt(ch);
            if(choix < 1 || choix > l.size()){
                System.out.println("Pas dans la liste !");
            }
        } while(choix <1 || choix > l.size());
        return choix;
    }
    public static void affListe(List l){
        int i =1;
        for(Object o :l) {
            System.out.println((i++)+"."+o);
        }
    }
    public static String modifyIfNotBlank(String label,String oldValue){
        System.out.println(label+" : "+oldValue);
        System.out.print("nouvelle valeur (enter si pas de changement) : ");
        String newValue= sc.nextLine();
        if(newValue.isBlank()) return oldValue;
        return newValue;
    }
    public static String modifyIfNotBlankTel(String label,String oldValue){
        System.out.println(label+" : "+oldValue);
        System.out.print("nouvelle valeur (enter si pas de changement) : ");
        String newValue= Utilitaires.verifEntreeBoucle("0[1-9][0-9]{8}","Numero de tel  :","Pas un numéro de tel valable ! --> format : (0 --- -- -- --)");
        if(newValue.isBlank()) return oldValue;
        return newValue;
    }
    public static Boolean verifierEntree (String entree, String regex){
        return entree.matches (regex);
    }
    public static String verifEntreeBoucle(String regex,String msg,String erreur){
        String s;
        do {
            System.out.println(msg);
            s = sc.nextLine();
            if (!verifierEntree(s,regex)) {
                System.out.println(erreur);
            }
        } while (!verifierEntree(s, regex));
        return s;
    }
    public static LocalDate convertToDate(String dateString) {
        // Définir le format de la chaîne de date d'entrée
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // Parser la chaîne de date en LocalDate
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }

}
