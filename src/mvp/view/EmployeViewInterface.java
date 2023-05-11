package mvp.view;

import classemetiers.Employe;
import classemetiers.Message;
import mvp.presenter.EmployePresenter;

import java.util.List;

public interface EmployeViewInterface {
    public void setPresenter(EmployePresenter presenter);

    public void setListDatas(List<Employe> clients);

    public void affMsg(String msg);

    public void affMsgList(List<Message> m);

    public Employe selectionner(List<Employe> employes);

    public int nombrePersonnes();

    void setListDatas2(List<Employe> employes,Employe employe);
    Employe identification();

    void affMsgEnvList(List<Message> lm);
}

