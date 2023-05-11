package mvp.view;

import classemetiers.Employe;
import classemetiers.Message;

import java.util.List;

public interface SpecialEmployeViewConsole {
     void setListDatas2(List<Employe> employes, Employe e);
     void menuAppli(Employe employe);
     Employe identification();
     void affMsgEnvList(List<Message> m);
     void affMsgList(List<Message> m);
     void repondre(Employe employe);
     int nombrePersonnes();
}
