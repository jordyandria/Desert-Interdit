package controleur;

import Observer.Observable;
import Observer.Observer;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import modele.Carte;
import modele.CarteEquipement;
import modele.Case;
import modele.PaquetCarte;
import vue.VuePaquetCarte;

public class PaquetCarteListener extends Observable implements MouseListener {
  private PaquetCarte paquet;
  
  private JTextField nomJoueur;
  
  private JButton next_player;
  
  private static boolean paquetEquipementClickable = false;
  
  public PaquetCarteListener(PaquetCarte pc, VuePaquetCarte vpc) {
    this.paquet = pc;
    addObserver((Observer)vpc);
  }
  
  public void setTextAndButtonField(JTextField tf, JButton b) {
    this.nomJoueur = tf;
    this.next_player = b;
  }
  
  public void mouseClicked(MouseEvent mouseEvent) {
    if (this.paquet.getType() == PaquetCarte.TypePaquet.Tempete)
      if (this.paquet.getDesert().getJoueurCourant().getActions() == 0 && this.paquet
        .getCountCartePioche() < this.paquet.getDesert().getNiveauTemp() && !paquetEquipementClickable) {
        this.paquet.incrCountCartePioche();
        Carte pioche = this.paquet.pioche();
        notifyObservers();
        pioche.effetCarte();
        if (this.paquet.getCountCartePioche() == this.paquet.getDesert().getNiveauTemp())
          this.paquet.notifyObservers(); 
        this.paquet.getDesert().notifyObservers();
        return;
      }  
    if (this.paquet.getType() == PaquetCarte.TypePaquet.Aventurier) {
      if (this.paquet.getMenuCreationJoueur().getTmpClassJoueur() == null) {
        Carte pioche = this.paquet.pioche();
        notifyObservers();
        pioche.effetCarte();
        if (this.paquet.getMenuCreationJoueur().getTmpClassJoueur() != null && this.nomJoueur
          .getText().length() != 0)
          this.next_player.setEnabled(true); 
      } 
      return;
    } 
    if (this.paquet.getType() == PaquetCarte.TypePaquet.Equipement && paquetEquipementClickable && this.paquet
      
      .getPaquet().size() > 0) {
      Carte pioche = this.paquet.pioche();
      notifyObservers();
      this.paquet.getDesert().getJoueurCourant().addEquipement((CarteEquipement)pioche);
      Case.becomeClickable();
      paquetEquipementClickable = false;
      this.paquet.getDesert().notifyObservers();
      this.paquet.getDesert().getJoueurCourant().notifyObservers();
    } 
  }
  
  public static void setPaquetClickable() {
    paquetEquipementClickable = true;
  }
  
  public static boolean getPaquetClickable() {
    return paquetEquipementClickable;
  }
  
  public void mousePressed(MouseEvent mouseEvent) {}
  
  public void mouseReleased(MouseEvent mouseEvent) {}
  
  public void mouseEntered(MouseEvent mouseEvent) {}
  
  public void mouseExited(MouseEvent mouseEvent) {}
}
