package mvp.view;

import classemetiers.Bureau;
import classemetiers.Message;
import mvp.presenter.BureauPresenter;
import mvp.presenter.MessagePresenter;

import java.util.List;

public interface BureauViewInterface {
    public void setPresenter(BureauPresenter presenter);

    public void setListDatas(List<Bureau> bureaux);

    public void affMsg(String msg);
}
