package controleur;

import Observer.Observer;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import modele.Desert;
import modele.Joueur;

public class PrisePieceButton extends JButton implements Observer {
  private Desert desert;
  
  public PrisePieceButton(Desert d) {
    this.desert = d;
    setText("Prendre une piece");
    setEnabled(false);
    addActionListener(e -> {
          Joueur courant = this.desert.getJoueurCourant();
          courant.prendrePiece();
          courant.decrActions();
          courant.notifyObservers();
          this.desert.notifyObservers();
        });
    this.desert.addObserver(this);
  }
  
  public void update() {
    Joueur courant = this.desert.getJoueurCourant();
    if (courant.getCurrentCase().getPiece().size() > 0 && courant.getActions() > 0) {
      setEnabled(true);
    } else {
      setEnabled(false);
    } 
  }
}
