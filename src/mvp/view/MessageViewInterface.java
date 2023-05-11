package mvp.view;

import classemetiers.Employe;
import classemetiers.Message;
import mvp.presenter.EmployePresenter;
import mvp.presenter.MessagePresenter;

import java.util.List;

public interface MessageViewInterface {
    public void setPresenter(MessagePresenter presenter);

    public void setListDatas(List<Message> messages);

    public void affMsg(String msg);
    Message saisieMessage();

}
