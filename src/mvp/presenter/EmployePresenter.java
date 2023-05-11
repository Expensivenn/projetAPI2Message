package mvp.presenter;

import classemetiers.Bureau;
import classemetiers.Employe;
import classemetiers.Message;
import mvp.model.DAO;
import mvp.model.EmployeSpecial;
import mvp.view.SpecialEmployeViewConsole;
import mvp.view.ViewInterface;

import java.time.LocalDate;
import java.util.List;

public class EmployePresenter extends Presenter<Employe> implements SpecialEmployePresenter {
    private BureauPresenter bureauPresenter;
    private MessagePresenter messagePresenter;
    public EmployePresenter(DAO<Employe> model,ViewInterface<Employe> view){
        super(model,view);

    }
    public void setBureauPresenter(BureauPresenter bureauPresenter) {
        this.bureauPresenter = bureauPresenter;
    }
    public void setMessagePresenter(MessagePresenter messagePresenter) {
        this.messagePresenter = messagePresenter;
    }






    //SPECIAL APLI
    @Override
    public Employe identify(){
        Employe e = ((SpecialEmployeViewConsole)view).identification();
        return  e;
    }
    @Override
    public Employe searchID(int id) {
        Employe e = ((EmployeSpecial) model).readID(id);
        if(e==null){
            view.affMsg("recherche infructueuse");
            return null;
        }
        else {
            return e;
        }
    }
    @Override
    public void startAppli(){
        Employe e = identify();
        List<Employe> employes = model.getAll();
        bonjour(e);
        ((SpecialEmployeViewConsole)view).setListDatas2(employes,e);
    }
    @Override
    public List<Bureau> Bureau(){
        List<Bureau>  l = bureauPresenter.getAll();
        return l;
    }
    @Override
    public List<Employe> listeEmp(){
        List<Employe> e = model.getAll();
        return e;
    }

    @Override
    public List<Message> getMessEmp(Employe e) {
        List<Message>  lm = messagePresenter.messagesDest(e);
        return lm;
    }

    @Override
    public void bonjour(Employe e){
        Bureau b = bureauPresenter.searchBureauId(e.getId_bureau());
        System.out.println("Bienvenue "+e.getPrenom()+ " qui travaille au bureau "+b.getSigle()+ " que souhaitez vous faire ?");
    }
    @Override
    public int nombrePersonnes(){
        int n = ((SpecialEmployeViewConsole)view).nombrePersonnes();
        return n;
    }
    //OPE SPECIALES PROF
    @Override
    public void OpSpeMessEnvoyes(Employe employe){
        List<Message> lm = ((EmployeSpecial)model).messagesEnvoye(employe);
        if(lm.size()!=0){
            List<Message> lmf = messagePresenter.getEmmRec(lm);
            ((SpecialEmployeViewConsole)view).affMsgEnvList(lmf);
        }
        else {
            System.out.println("Pas de messages envoyé !");
        }

    }

    @Override
    public void OpSpeMessDates(Employe employe,LocalDate d1, LocalDate d2) {
        List<Message> lm = messagePresenter.messageDates(d1,d2,employe);
        if(lm==null || lm.isEmpty()) view.affMsg("aucun message(s) envoyé !");
        else ((SpecialEmployeViewConsole)view).affMsgList(lm);
    }

    @Override
    public void OpSpeMessDest(Employe employe) {
        List<Message> lm = messagePresenter.messagesDest(employe);
        if(lm==null || lm.isEmpty()) view.affMsg("aucun message(s) reçu !");
        else ((SpecialEmployeViewConsole)view).affMsgList(lm);
    }

    @Override
    public void OpSpeMessRep(Employe employe) {
        List<Message> lm = messagePresenter.messagesRep(employe);
        if(lm==null || lm.isEmpty()) view.affMsg("aucune(s) réponse(s) envoyée !");
        else ((SpecialEmployeViewConsole)view).affMsgList(lm);
    }
    //OPE SPECIALES APPLI
    @Override
    public void OpSpeAppliMessNonLu(Employe employe) {
        List<Message> lm = messagePresenter.messagesNonLuEmpl(employe);
        List<Message> lmf = messagePresenter.getEmmRec(lm);
        ((SpecialEmployeViewConsole)view).affMsgList(lmf);
    }

    @Override
    public void OpSpeAppliToutMessRecu(Employe employe) {
        List<Message> lm = messagePresenter.messagesDest(employe);
        List<Message> lmf = messagePresenter.getEmmRec(lm);
        ((SpecialEmployeViewConsole)view).affMsgList(lmf);
    }

    @Override
    public void OpSpeAppliToutMessEnv(Employe employe) {
        //TODO TOUT LES MESSAGES ENVOYé PAR UN EMPLOYé
    }

    @Override
    public void OpSpeAppliRepondre(Message m, Employe employe) {
        Message mess = messagePresenter.envoyerReponse(m,employe);
    }

    @Override
    public void OpSpeAppliEnvoyerMess(Employe employe) {
        messagePresenter.ennvoyerMessage(employe);
    }

}



