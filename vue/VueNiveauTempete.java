package vue;

import Observer.Observer;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import modele.Desert;

public class VueNiveauTempete extends JPanel implements Observer {
  private Desert d;
  
  private JLabel nomBarre;
  
  private JProgressBar progresTempete;
  
  public VueNiveauTempete(Desert d) {
    this.d = d;
    this.nomBarre = new JLabel("Niveau de la tempete ");
    add(this.nomBarre);
    this.progresTempete = new JProgressBar();
    this.progresTempete.setMinimum(0);
    this.progresTempete.setMaximum(7);
    this.progresTempete.setStringPainted(true);
    this.progresTempete.setValue(d.getNiveauTemp());
    this.progresTempete.setString("" + d.getNiveauTemp() + "/" + d.getNiveauTemp());
    add(this.progresTempete);
    d.addObserver(this);
  }
  
  public void update() {
    repaint();
  }
  
  public void paintComponent(Graphics g) {
    this.progresTempete.setValue(this.d.getNiveauTemp());
    this.progresTempete.setString("" + this.d.getNiveauTemp() + "/" + this.d.getNiveauTemp());
    super.paintComponent(g);
  }
}
