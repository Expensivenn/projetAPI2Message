package mvp.presenter;

import classemetiers.Employe;
import classemetiers.Message;

import java.time.LocalDate;
import java.util.List;

public interface SpecialMessagePresenter {
    Message setEmmRec(Message message);
    List<Message> getEmmRec(List<Message> list);
     void ennvoyerMessage(Employe e);
     void rechercher(Message message);
    List<Message> OpeSpeMessDeuxDates(LocalDate d1, LocalDate d2, Employe employe);
    List<Message> OpeSpeMessDest(Employe employe);
    List<Message> OpeSpeMessReponses(Employe employe);
    List<Message> OpeSpeMessNonLu(Employe employe);
    Message OpeSpeAppliEnvoyerRep(Message m,Employe employe);
    List<Message> OpeSpeMessEnv(Employe employe);
    //SGBD
    void SgbdEnvoyerMessage(Message m,int idEm,int idRe);
    void SgbdEnvoyerReponse(Message m,int idE,Message message);
    void SgbdAsReponse(Message message);
    void SgbdNombreReponse(Message message);
    void SgbdReponseMessage(Message message);



}
