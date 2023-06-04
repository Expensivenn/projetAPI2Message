package mvp.model;

import classemetiers.Employe;
import classemetiers.Message;

import java.time.LocalDate;
import java.util.List;

public interface MessagesSpecial {
    //Methode Spe
    List<Integer> findIds(Message m);
    //OPE SPE
    List<Message> OpeSpeMessageEntreDates(LocalDate d1, LocalDate d2, Employe employe);
    List<Message> OpeSpeMessDest(Employe employe);
    List<Message> OpeSpeAppliMessNonLu(Employe employe);
    List<Message> OpeSpeMessRep(Employe employe);
    Message OpeSpeAppliRepondre(Message orig,Message reponse);
    List<Message> OpeSpeMessEnv(Employe employe);
    //SGBD
    Message SgbdEnvoyerMessage(Message m, int idEm,int idRe);
    Message SgbdEnvoyerReponse(Message m, int idE,Message message);
    boolean SgbdAsReponse(Message mess);
    int SgbdNombreReponse(Message mess);
    List<Message> SgbdReponseMessage(Message message);
}
