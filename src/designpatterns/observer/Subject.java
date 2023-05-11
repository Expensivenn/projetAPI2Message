package designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    protected List<Observer> myObservers = new ArrayList<>();
    public void addObserver(Observer o){
        myObservers.add(o);
    }
    public void removeObserver(Observer o){
        myObservers.remove(o);
    }
    public void notifyObservers(){
        String msg=getNotification();
        for(Observer o : myObservers) o.update(msg);
    }
    public abstract String getNotification();
}


