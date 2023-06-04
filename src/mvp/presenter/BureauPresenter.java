package mvp.presenter;


import classemetiers.Bureau;
import mvp.model.BureauSpecial;
import mvp.model.DAO;
import mvp.view.ViewInterface;

public class BureauPresenter extends Presenter<Bureau> implements SpecialBureauPresenter {

    public BureauPresenter(DAO<Bureau> model,ViewInterface<Bureau> view){
        super(model,view);

    }

    @Override
    public Bureau searchBureauId(int id){
        Bureau b = ((BureauSpecial)model).searchBur(id);
        if(b!= null){
            return b;
        }
        else {
            view.affMsg("Bureau non trouvé");
            return null;
        }
    }


}
