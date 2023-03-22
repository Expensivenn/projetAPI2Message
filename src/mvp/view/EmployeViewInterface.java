package mvp.view;

import classemetiers.Employe;
import mvp.presenter.EmployePresenter;

import java.util.List;

public interface EmployeViewInterface {
    public void setPresenter(EmployePresenter presenter);

    public void setListDatas(List<Employe> clients);

    public void affMsg(String msg);
}

