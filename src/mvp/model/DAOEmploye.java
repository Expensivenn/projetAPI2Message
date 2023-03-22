package mvp.model;

import classemetiers.Bureau;
import classemetiers.Employe;

import java.util.List;

public interface DAOEmploye {
    Employe addEmploye(Employe employe);
    boolean removeEmploye(Employe employe);
    Employe updateEmploye(Employe employe);

    Employe searchEmp(int id);

    List<Employe> getEmployes();

    List<Bureau> getBureau();
}
