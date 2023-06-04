package mvp.presenter;

import classemetiers.Employe;
import classemetiers.Message;
import mvp.model.DAO;
import mvp.model.MessagesSpecial;
import mvp.view.SpecialMessageViewConsole;
import mvp.view.ViewInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MessagePresenter extends Presenter<Message> implements SpecialMessagePresenter {
    private Presenter<Employe> employePresenter;

    public MessagePresenter(DAO<Message> model, ViewInterface<Message> view){
        super(model,view);
    }
    public void setEmployePresenter(EmployePresenter employePresenter) {
        this.employePresenter = employePresenter;
    }
    @Override
    public void start() {
        List<Message> lm;
        lm = model.getAll();
        view.setListDatas(getEmmRec(lm));
    }

    @Override
    public void add(Message m) {
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
        Message mess = model.add(m);
        if(mess!=null) view.affMsg("envoi du message à "+m.getRecepteurs());
        else view.affMsg("message non envoyé");
        start();
    }
    @Override
    public void search(Message mess) {
        Message elt= model.read(mess);
        if(elt==null) view.affMsg("recherche infructueuse");
        else {
            setEmmRec(elt);
            view.affMsg(elt.toString());
        }
    }
    @Override
    public void ennvoyerMessage(Employe e){
        Message m = ((SpecialMessageViewConsole)view).saisieMessage();
        m.setEmmetteur(e);
        List<Employe> l = new ArrayList<>();
        int n = ((SpecialEmployePresenter)employePresenter).nombrePersonnes();
        for (int i = 0 ; i<n;i++){
            view.affMsg("à qui voulez vous envoyer le message :");
            Employe recepteur = employePresenter.selection();
            l.add(recepteur);
        }
        m.setRecepteurs(l);
        Message mess = model.add(m);
        if(mess!=null) view.affMsg("envoi du message à "+m.getRecepteurs());
        else view.affMsg("message non envoyé");

    }
    @Override
    public void rechercher(Message message){
        Message m = model.read(message);
        if(m!=null){
            Message mess = setEmmRec(m);
            view.affMsg("Message recherché : " + mess );
        }
        else view.affMsg("message non trouvé");
    }
    @Override
    public List<Message> OpeSpeMessDeuxDates(LocalDate d1, LocalDate d2,Employe employe){
        List<Message> lm = ((MessagesSpecial)model).OpeSpeMessageEntreDates(d1,d2,employe);
        return getEmmRec(lm);
    }
    @Override
    public List<Message> OpeSpeMessDest(Employe employe){
        List<Message> lm = ((MessagesSpecial)model).OpeSpeMessDest(employe);
        return getEmmRec(lm);
    }
    @Override
    public List<Message> OpeSpeMessReponses(Employe employe){
        List<Message> lm = ((MessagesSpecial)model).OpeSpeMessRep(employe);
        return getEmmRec(lm);
    }
    @Override
    public List<Message> OpeSpeMessNonLu(Employe employe){
        List<Message> lm = ((MessagesSpecial)model).OpeSpeAppliMessNonLu(employe);
        return getEmmRec(lm);
    }
    @Override
    public Message OpeSpeAppliEnvoyerRep(Message m,Employe employe){
        Message reponse = ((SpecialMessageViewConsole)view).saisieMessage();
        reponse.setEmmetteur(employe);
        List<Employe> le = new ArrayList<>();
        le.add(m.getEmmetteur());
        reponse.setRecepteurs(le);
        Message mess = ((MessagesSpecial)model).OpeSpeAppliRepondre(m,reponse);
        if(mess!=null){
            view.affMsg("Reponse envoyé") ;
            return mess;
        }
        else {
            view.affMsg("erreur de reponse");
            return null;
        }
    }

    @Override
    public List<Message> OpeSpeMessEnv(Employe employe) {
        List<Message> lm = ((MessagesSpecial)model).OpeSpeMessEnv(employe);
        return getEmmRec(lm);
    }
    @Override
    public Message setEmmRec(Message m) {
        List<Integer> ids = ((MessagesSpecial)model).findIds(m);
        Employe exp = ((SpecialEmployePresenter)employePresenter).searchID(ids.get(0));
        m.setEmmetteur(exp);
        List<Employe> recepteurs = new ArrayList<>();
        for (int i = 1; i < ids.size(); i++) {
            Employe rec = ((SpecialEmployePresenter)employePresenter).searchID(ids.get(i));
            recepteurs.add(rec);
        }
        m.setRecepteurs(recepteurs);
        return m;
    }

    @Override
    public List<Message> getEmmRec(List<Message> list) {
        List<Message> lm = new ArrayList<>();
        for (Message m:list) {
            Message mes = setEmmRec(m);
            lm.add(mes);
        }
        return lm;
    }
    //SGBD
    @Override
    public void SgbdEnvoyerMessage(Message m,int idEm,int idRe){
        Message mess = ((MessagesSpecial)model).SgbdEnvoyerMessage(m,idEm,idRe);
        if(mess != null){
            view.affMsg("Message envoyé ! id : "+mess.getId());
        }
        else view.affMsg("Ereur !");
    }

    @Override
    public void SgbdEnvoyerReponse(Message m, int idE,Message message) {
        setEmmRec(message);
        Message mess = ((MessagesSpecial)model).SgbdEnvoyerReponse(m,idE,message);
        if(mess != null){
            view.affMsg("Reponse envoyé ! id : "+ mess.getId());
        }
        else view.affMsg("Ereur !");
    }

    @Override
    public void SgbdAsReponse(Message message) {
        setEmmRec(message);
        boolean x = ((MessagesSpecial)model).SgbdAsReponse(message);
        if(x) System.out.println("Oui il a une/des reponse(s)");
        else System.out.println("Pas de réponses pour ce message");
    }
    @Override
    public void SgbdNombreReponse(Message message) {
        setEmmRec(message);
        int x = 0;
        x = ((MessagesSpecial)model).SgbdNombreReponse(message);
        if(x!= 0) System.out.println("Oui il a "+x+" reponse(s)");
        else System.out.println("Pas de réponses pour ce message");
    }
    @Override
    public void SgbdReponseMessage(Message message) {
        List<Message> lm = new ArrayList<>();
        lm = ((MessagesSpecial)model).SgbdReponseMessage(message);
        if(lm!= null) ((SpecialEmployePresenter)employePresenter).affMsgList(getEmmRec(lm));
        else System.out.println("Pas de réponses pour ce message");
    }




}
