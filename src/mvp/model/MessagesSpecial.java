package mvp.model;

import classemetiers.Employe;
import classemetiers.Message;

import java.time.LocalDate;
import java.util.List;

public interface MessagesSpecial {
    List<Message> messageDates(LocalDate d1, LocalDate d2, Employe employe);
    List<Message> messageDest(Employe employe);

    List<Message> messagesNonLuEmpl(Employe employe);
    List<Message> messagesRep(Employe employe);
    Message repondre(Message orig,Message reponse);
}
