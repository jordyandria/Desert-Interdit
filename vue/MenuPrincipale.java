package vue;

import controleur.JouerButton;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPrincipale extends JPanel {
  private JouerButton jouer;
  
  private JButton quitter;
  
  public MenuPrincipale(JPanel rootPanel) {
    this.jouer = new JouerButton(rootPanel);
    this.quitter = new JButton("Quitter");
    setLayout(new BorderLayout());
    JPanel tmp = new JPanel();
    tmp.add((Component)this.jouer);
    tmp.add(this.quitter);
    add(tmp, "Center");
  }
}
