package mvp.model;

import classemetiers.Employe;
import classemetiers.Message;

import java.util.List;

public interface EmployeSpecial {
    public List<Message> messagesRecu(Employe employe);

    //public List<Message> messagesEnvoye(Employe employe);
    Employe readID(int id);
    //SGBD
    Employe SgbdAjouterEmploye(Employe employe);
}
