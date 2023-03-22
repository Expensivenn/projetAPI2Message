package mvp.presenter;

import classemetiers.Bureau;
import classemetiers.Employe;
import mvp.model.DAOEmploye;
import mvp.view.EmployeViewInterface;

import java.util.List;

public class EmployePresenter {
    private DAOEmploye model;
    private EmployeViewInterface view;

    public EmployePresenter(DAOEmploye model,EmployeViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }
    public void start(){
        List<Employe> employes = model.getEmployes();
        view.setListDatas(employes);
    }
    public void addEmploye(Employe employe) {
        Employe emp = model.addEmploye(employe);
        if(emp!=null) view.affMsg("création de :"+emp);
        else view.affMsg("erreur de création");
        List<Employe> employes = model.getEmployes();
        view.setListDatas(employes);
    }
    public void updateEmploye(Employe employe){
        Employe emp = model.updateEmploye(employe);
        if(emp!=null) view.affMsg("modification de : "+emp);
        else view.affMsg("erreur de modif");
        List<Employe> employes = model.getEmployes();
        view.setListDatas(employes);
    }

    public void removeEmploye(Employe employe) {
        boolean ok = model.removeEmploye(employe);
        if(ok) view.affMsg("employe effacé");
        else view.affMsg("employe non effacé");
        List<Employe> employes = model.getEmployes();
        view.setListDatas(employes);
    }
    public void rechercher(int id){
        Employe e = model.searchEmp(id);
        if(e!=null) view.affMsg("employe recherché : " + e );
        else view.affMsg("employe non trouvé");
    }
    public List<Bureau> Bureau(){
        List<Bureau>  l = model.getBureau();
        return l;
    }
    public List<Employe> listeEmp(){
        List<Employe> e = model.getEmployes();
        return e;
    }
}



