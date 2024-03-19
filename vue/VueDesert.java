package vue;

import Observer.Observer;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import modele.Desert;

public class VueDesert extends JPanel implements Observer {
  private Desert d;
  
  private VueCase[][] vueCaseDesert;
  
  public VueDesert(Fenetre f, Desert d, int hauteur, int largeur) {
    this.d = d;
    this.vueCaseDesert = new VueCase[5][5];
    setLayout(new GridLayout(hauteur, largeur, 2, 2));
    setPreferredSize(new Dimension(700, 700));
    d.addObserver(this);
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        VueCase vc = new VueCase(f, d.getCase(i, j));
        this.vueCaseDesert[i][j] = vc;
        add(vc);
      } 
    } 
  }
  
  public void update() {
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++)
        this.vueCaseDesert[i][j].setCase(this.d.getCase(i, j)); 
    } 
    revalidate();
    repaint();
  }
}
