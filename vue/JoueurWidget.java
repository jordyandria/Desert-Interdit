package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import modele.Joueur;

public class JoueurWidget extends JPanel {
  private Joueur j;
  
  public JoueurWidget(Joueur j) {
    this.j = j;
    setPreferredSize(new Dimension(20, 20));
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.j != null) {
      g.setColor(this.j.getClassJoueur().getClassColor());
      g.fillRect(2, 0, 20, 20);
      g.setColor(new Color(0, 0, 0));
    } 
  }
}
