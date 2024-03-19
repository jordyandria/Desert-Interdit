package controleur;

import Observer.Observer;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import modele.Case;
import modele.Desert;
import modele.Joueur;

public class ExplorationButton extends JButton implements Observer {
  private Desert desert;
  
  public ExplorationButton(Desert d) {
    this.desert = d;
    setText("Exploration");
    addActionListener(e -> {
          Joueur currentJoueur = this.desert.getJoueurCourant();
          if (!currentJoueur.getCurrentCase().getExploreCase() && currentJoueur.getActions() > 0 && currentJoueur.getCurrentCase().getTonneSable() == 0) {
            currentJoueur.getCurrentCase().effetCase();
            currentJoueur.decrActions();
            currentJoueur.getCurrentCase().exploreCase();
            if (currentJoueur.getCurrentCase().hasEngrenage()) {
              Case.nonClickable();
              PaquetCarteListener.setPaquetClickable();
            } 
            this.desert.notifyObservers();
          } 
          if (currentJoueur.getClassJoueur() == Joueur.ClassJoueur.PORTEUSE_EAU && currentJoueur.getCurrentCase().getExploreCase() && currentJoueur.getCurrentCase().getTypeCase() == Case.TypeCase.POINT_EAU && currentJoueur.getActions() > 0 && currentJoueur.getCurrentCase().getTonneSable() < 2) {
            currentJoueur.getCurrentCase().effetCase();
            currentJoueur.decrActions();
            this.desert.notifyObservers();
          } 
        });
    this.desert.addObserver(this);
  }
  
  public void update() {
    Joueur currentJoueur = this.desert.getJoueurCourant();
    if (currentJoueur.getClassJoueur() == Joueur.ClassJoueur.PORTEUSE_EAU && currentJoueur
      .getCurrentCase().getExploreCase() && currentJoueur
      .getCurrentCase().getTypeCase() == Case.TypeCase.POINT_EAU && currentJoueur
      .getActions() > 0 && currentJoueur
      .getCurrentCase().getTonneSable() == 0) {
      setEnabled(true);
    } else if (currentJoueur.getCurrentCase().getExploreCase() || currentJoueur
      .getActions() <= 0 || currentJoueur
      .getCurrentCase().getTonneSable() != 0) {
      setEnabled(false);
    } else {
      setEnabled(true);
    } 
  }
}
