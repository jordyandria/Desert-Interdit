package controleur;

import Observer.Observer;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modele.Case;
import modele.Desert;
import modele.Joueur;
import modele.PaquetCarte;

public class FinDeTour extends JButton implements Observer {
  private Desert d;
  
  private JDialog finJeu;
  
  private PaquetCarte paquetTempete;
  
  public FinDeTour(Desert d, PaquetCarte paquetTempete, JDialog finJeu) {
    this.paquetTempete = paquetTempete;
    this.d = d;
    this.finJeu = finJeu;
    setText("Fin de Tour");
    setEnabled(false);
    addActionListener(e -> {
          boolean joueurMort = false;
          for (Joueur j : this.d.getJoueurs()) {
            if (j.getNiveauEau() == 0) {
              joueurMort = true;
              break;
            } 
          } 
          if (this.d.getNiveauTemp() >= 7 || this.d.countSable() >= 48 || joueurMort) {
            JOptionPane gameOver = new JOptionPane("Game Over");
            this.finJeu.setContentPane(gameOver);
            this.finJeu.setVisible(true);
          } 
          Joueur courant = this.d.getJoueurCourant();
          Case caseJoueurCourant = courant.getCurrentCase();
          if (caseJoueurCourant.getTypeCase() == Case.TypeCase.PISTE_DECOLLAGE && this.d.getJoueurs().size() == caseJoueurCourant.getJoueurs().size()) {
            int countPieceJoueur = 0;
            for (Joueur j : caseJoueurCourant.getJoueurs())
              countPieceJoueur += j.getPieces().size(); 
            if (countPieceJoueur == 4) {
              JOptionPane gameOver = new JOptionPane("Bravo !! Vous avez au desert sur votre machine volante.");
              this.finJeu.setContentPane(gameOver);
              this.finJeu.setVisible(true);
            } 
          } 
          if (Desert.getActivationBouclierCeTour() && Desert.getSrcActivationBouclierSolaire() != null) {
            Desert.setActivationBouclierCeTour();
          } else {
            Joueur tmpJ = Desert.getSrcActivationBouclierSolaire();
            if (tmpJ != null) {
              Desert.setSrcActivationBouclierSolaire(null);
              tmpJ.notifyObservers();
            } 
          } 
          this.d.changeCurrentPlayer();
          this.d.incrNiveauTempete();
          this.paquetTempete.resetCountCartePioche();
          setEnabled(false);
        });
    paquetTempete.addObserver(this);
  }
  
  public void update() {
    if (this.paquetTempete.getCountCartePioche() == this.paquetTempete.getDesert().getNiveauTemp() && this.d
      .getJoueurCourant().getActions() == 0)
      setEnabled(true); 
  }
}
