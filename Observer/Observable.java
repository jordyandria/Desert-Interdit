package Observer;

import java.util.ArrayList;

public class Observable {
  private ArrayList<Observer> observers = new ArrayList<>();
  
  public void addObserver(Observer o) {
    this.observers.add(o);
  }
  
  public void notifyObservers() {
    for (Observer o : this.observers)
      o.update(); 
  }
}
