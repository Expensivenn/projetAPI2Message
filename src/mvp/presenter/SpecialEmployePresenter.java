package mvp.presenter;

import classemetiers.Bureau;
import classemetiers.Employe;
import classemetiers.Message;

import java.time.LocalDate;
import java.util.List;

public interface SpecialEmployePresenter {
     void startAppli();
     Employe searchID(int id);
      Employe identify();
      List<Bureau> Bureau();
     List<Employe> listeEmp();
     List<Message> getMessEmp(Employe e);
     void bonjour(Employe e);
     int nombrePersonnes();
    void OpSpeMessEnvoyes(Employe employe);
    void OpSpeMessDates(Employe employe, LocalDate d1,LocalDate d2);
    void OpSpeMessDest(Employe employe);
    void OpSpeMessRep(Employe employe);
    void OpSpeAppliMessNonLu(Employe employe);
    void OpSpeAppliToutMessRecu(Employe employe);
    void OpSpeAppliToutMessEnv(Employe employe);
    void OpSpeAppliRepondre(Message m, Employe employe);
    void OpSpeAppliEnvoyerMess(Employe employe);
    void SgbdAjouterEmploye(Employe employe);

    void affMsgList(List<Message> lm);
}
