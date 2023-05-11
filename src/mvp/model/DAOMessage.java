package mvp.model;

import classemetiers.Employe;
import classemetiers.Message;

import java.util.List;

public interface DAOMessage {
    Message addMessage(Message message);
    boolean removeMessage(Message message);
    Message updateMessage(Message message);

    Message searchMess(int id);
    List<Message> getMessages();
    List<Integer> findIds(Message message);
}
