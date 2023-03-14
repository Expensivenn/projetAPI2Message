package utilitaires;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Utilitaires {
    private static Scanner sc = new Scanner(System.in);

    public static LocalDate lecDate(){
        String[] jma = sc.nextLine().split(" ");
        int j = Integer.parseInt(jma[0]);
        int m = Integer.parseInt(jma[1]);
        int a = Integer.parseInt(jma[2]);
        return LocalDate.of(a,m,j);
    }

}
