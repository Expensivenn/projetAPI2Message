package mvp.presenter;

import classemetiers.Bureau;
import classemetiers.Employe;
import classemetiers.Message;
import mvp.model.DAO;
import mvp.model.EmployeSpecial;
import mvp.model.MessagesSpecial;
import mvp.view.SpecialEmployeViewConsole;
import mvp.view.ViewInterface;

import java.time.LocalDate;
import java.util.List;

public class EmployePresenter extends Presenter<Employe> implements SpecialEmployePresenter {
    private Presenter<Bureau> bureauPresenter;
    private Presenter<Message> messagePresenter;
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
            //view.affMsg(e.toString());
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
        List<Message>  lm = ((SpecialMessagePresenter)messagePresenter).OpeSpeMessDest(e);
        return lm;
    }

    @Override
    public void bonjour(Employe e){
        Bureau b = ((SpecialBureauPresenter)bureauPresenter).searchBureauId(e.getId_bureau());
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
        List<Message> lm = ((SpecialMessagePresenter)messagePresenter).OpeSpeMessEnv(employe);
        if(lm.size()!=0){
            //List<Message> lmf = ((SpecialMessagePresenter)messagePresenter).getEmmRec(lm);
            ((SpecialEmployeViewConsole)view).affMsgEnvList(lm);
        }
        else {
            System.out.println("Pas de messages envoyé !");
        }

    }

    @Override
    public void OpSpeMessDates(Employe employe,LocalDate d1, LocalDate d2) {
        List<Message> lm = ((SpecialMessagePresenter)messagePresenter).OpeSpeMessDeuxDates(d1,d2,employe);
        if(lm==null || lm.isEmpty()) view.affMsg("aucun message(s) envoyé !");
        else ((SpecialEmployeViewConsole)view).affMsgList(lm);
    }

    @Override
    public void OpSpeMessDest(Employe employe) {
        List<Message> lm = ((SpecialMessagePresenter)messagePresenter).OpeSpeMessDest(employe);
        if(lm==null || lm.isEmpty()) view.affMsg("aucun message(s) reçu !");
        else ((SpecialEmployeViewConsole)view).affMsgList(lm);
    }

    @Override
    public void OpSpeMessRep(Employe employe) {
        List<Message> lm = ((SpecialMessagePresenter)messagePresenter).OpeSpeMessReponses(employe);
        if(lm==null || lm.isEmpty()) view.affMsg("aucune(s) réponse(s) envoyée !");
        else ((SpecialEmployeViewConsole)view).affMsgList(lm);
    }
    //OPE SPECIALES APPLI
    @Override
    public void OpSpeAppliMessNonLu(Employe employe) {
        List<Message> lm = ((SpecialMessagePresenter)messagePresenter).OpeSpeMessNonLu(employe);
        //List<Message> lmf = ((SpecialMessagePresenter)messagePresenter).getEmmRec(lm);
        ((SpecialEmployeViewConsole)view).affMsgList(lm);
    }

    @Override
    public void OpSpeAppliToutMessRecu(Employe employe) {
        List<Message> lm = ((SpecialMessagePresenter)messagePresenter).OpeSpeMessDest(employe);
        //List<Message> lmf = ((SpecialMessagePresenter)messagePresenter).getEmmRec(lm);
        ((SpecialEmployeViewConsole)view).affMsgList(lm);
    }

    @Override
    public void OpSpeAppliToutMessEnv(Employe employe) {
        List<Message> lm = ((SpecialMessagePresenter)messagePresenter).OpeSpeMessEnv(employe);
        //List<Message> lmf = ((SpecialMessagePresenter)messagePresenter).getEmmRec(lm);
        ((SpecialEmployeViewConsole)view).affMsgEnvList(lm);
    }

    @Override
    public void OpSpeAppliRepondre(Message m, Employe employe) {
        ((SpecialMessagePresenter)messagePresenter).OpeSpeAppliEnvoyerRep(m,employe);
    }

    @Override
    public void OpSpeAppliEnvoyerMess(Employe employe) {
        ((SpecialMessagePresenter)messagePresenter).ennvoyerMessage(employe);
    }
    @Override
    public void SgbdAjouterEmploye(Employe employe){
        Employe emp = ((EmployeSpecial)model).SgbdAjouterEmploye(employe);
        if(emp!= null){
            view.affMsg("Ajout de "+emp.getPrenom()+"id : "+emp.getId());
        }
        else view.affMsg("erreur");
    }
    @Override
    public void affMsgList(List<Message> lm){
        ((SpecialEmployeViewConsole)view).affMsgList(lm);
    }

}



