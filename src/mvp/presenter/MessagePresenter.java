package mvp.presenter;

import classemetiers.Employe;
import classemetiers.Message;
import mvp.model.DAOMessage;
import mvp.model.MessagesSpecial;
import mvp.view.MessageViewInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MessagePresenter {
    private Presenter<Employe> employePresenter;

    public void setEmployePresenter(EmployePresenter employePresenter) {
        this.employePresenter = employePresenter;
    }

    private DAOMessage model;
    private MessageViewInterface view;

    public MessagePresenter(DAOMessage model,MessageViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }
    public void start() {
        List<Message> messages = model.getMessages();
        for (Message m : messages) {
            List<Integer> ids = model.findIds(m);
            Employe exp = ((SpecialEmployePresenter)employePresenter).searchID(ids.get(0));
            m.setEmmetteur(exp);
            List<Employe> recepteurs = new ArrayList<>();
            for (int i = 1; i < ids.size(); i++) {
                Employe rec = ((SpecialEmployePresenter)employePresenter).searchID(ids.get(i));
                recepteurs.add(rec);
            }
            m.setRecepteurs(recepteurs);
        }
        view.setListDatas(messages);
    }
    public List<Message> getEmmRec(List<Message> messages) {
        for (Message m : messages) {
            List<Integer> ids = model.findIds(m);
            Employe exp = ((SpecialEmployePresenter)employePresenter).searchID(ids.get(0));
            m.setEmmetteur(exp);
            List<Employe> recepteurs = new ArrayList<>();
            for (int i = 1; i < ids.size(); i++) {
                Employe rec = ((SpecialEmployePresenter)employePresenter).searchID(ids.get(i));
                recepteurs.add(rec);
            }
            m.setRecepteurs(recepteurs);
        }
        return messages;
        //view.setListDatas(messages);
    }
    public void addMessage(Message m) {
        view.affMsg("Qui envoie le message :");
        Employe emmeteur = employePresenter.selection();
        m.setEmmetteur(emmeteur);
        List<Employe> l = new ArrayList<>();
        int n = ((SpecialEmployePresenter)employePresenter).nombrePersonnes();
        for (int i = 0 ; i<n;i++){
            view.affMsg("Qui reçoit le message :");
            Employe recepteur = employePresenter.selection();
            l.add(recepteur);
        }
        m.setRecepteurs(l);
        Message mess = model.addMessage(m);
        if(mess!=null) view.affMsg("envoi du message à "+m.getRecepteurs());
        else view.affMsg("message non envoyé");
        start();
    }
    public void ennvoyerMessage(Employe e){
        Message m = view.saisieMessage();
        m.setEmmetteur(e);
        List<Employe> l = new ArrayList<>();
        int n = ((SpecialEmployePresenter)employePresenter).nombrePersonnes();
        for (int i = 0 ; i<n;i++){
            view.affMsg("à qui voulez vous envoyer le message :");
            Employe recepteur = employePresenter.selection();
            l.add(recepteur);
        }
        m.setRecepteurs(l);
        Message mess = model.addMessage(m);
        if(mess!=null) view.affMsg("envoi du message à "+m.getRecepteurs());
        else view.affMsg("message non envoyé");

    }
    public void removeMessage(Message message) {
        boolean ok = model.removeMessage(message);
        if(ok) view.affMsg("Message effacé");
        else view.affMsg("Message non effacé");
        start();
    }
    public void updateMessage(Message message){
        Message m = model.updateMessage(message);
        if(m!=null) view.affMsg("modification de : "+m);
        else view.affMsg("erreur de modif");
        start();
    }
    public void rechercher(Message message){
        Message m = model.searchMess(message.getId());
        if(m!=null){
            List<Integer> ids = model.findIds(m);
            Employe exp = ((SpecialEmployePresenter)employePresenter).searchID(ids.get(0));
            m.setEmmetteur(exp);
            List<Employe> recepteurs = new ArrayList<>();
            for (int i = 1; i < ids.size(); i++) {
                Employe rec = ((SpecialEmployePresenter)employePresenter).searchID(ids.get(i));
                recepteurs.add(rec);
            }
            m.setRecepteurs(recepteurs);
            view.affMsg("Message recherché : " + m );
        }
        else view.affMsg("message non trouvé");
    }
    public List<Message> messageDates(LocalDate d1, LocalDate d2,Employe employe){
        List<Message> lm = ((MessagesSpecial)model).messageDates(d1,d2,employe);
        return getEmmRec(lm);
    }
    public List<Message> messagesDest(Employe employe){
        List<Message> lm = ((MessagesSpecial)model).messageDest(employe);
        return getEmmRec(lm);
    }
    public List<Message> messagesRep(Employe employe){
        List<Message> lm = ((MessagesSpecial)model).messagesRep(employe);
        return getEmmRec(lm);
    }
    public List<Message> messagesNonLuEmpl(Employe employe){
        List<Message> lm = ((MessagesSpecial)model).messagesNonLuEmpl(employe);
        return getEmmRec(lm);
    }
    public Message envoyerReponse(Message m,Employe employe){
        Message reponse = view.saisieMessage();
        reponse.setEmmetteur(employe);
        List<Employe> le = new ArrayList<>();
        le.add(m.getEmmetteur());
        reponse.setRecepteurs(le);
        Message mess = ((MessagesSpecial)model).repondre(m,reponse);
        if(mess!=null){
            view.affMsg("Reponse envoyé") ;
            return mess;
        }
        else {
            view.affMsg("erreur de reponse");
            return null;
        }
    }

}
