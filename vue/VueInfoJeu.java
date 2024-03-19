package vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Desert;
import modele.PaquetCarte;

public class VueInfoJeu extends JPanel {
  private Desert desert;
  
  private PaquetCarte paquetTemp;
  
  private JLabel objectif;
  
  public VueInfoJeu(Desert d, PaquetCarte paquetTemp) {
    this.desert = d;
    this.paquetTemp = paquetTemp;
    setLayout(new GridBagLayout());
    JPanel panneauObjectif = new JPanel();
    panneauObjectif.setLayout(new BoxLayout(panneauObjectif, 0));
    this.objectif = new JLabel("<html>Trouver les 4 pieces de la machine volante cache dans le desert <br/> puis echapper vous du desert par la piste de decollage </html>");
    panneauObjectif.add(this.objectif);
    JPanel tmpPiece = new JPanel();
    tmpPiece.setLayout(new BoxLayout(tmpPiece, 1));
    for (Desert.PieceMachine p : Desert.PieceMachine.values()) {
      JPanel panneauPiece = new JPanel();
      panneauPiece.add(new JLabel(p.getNom()));
      panneauPiece.add(new PieceWidget(p));
      tmpPiece.add(panneauPiece);
    } 
    panneauObjectif.add(tmpPiece);
    add(panneauObjectif, new GridBagConstraints());
  }
}
