package vue;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame {
  private JPanel elements;
  
  public Fenetre(String nom, Dimension minSize) {
    super(nom);
    setMinimumSize(minSize);
    this.elements = new JPanel();
    add(this.elements);
  }
  
  public void ajouteElement(JComponent element) {
    this.elements.add(element);
  }
  
  public void dessineFenetre() {
    pack();
    setVisible(true);
    setDefaultCloseOperation(3);
  }
}
