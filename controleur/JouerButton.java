package controleur;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

public class JouerButton extends JButton {
  private JPanel root;
  
  public JouerButton(JPanel root) {
    this.root = root;
    setText("Jouer");
    addActionListener(e -> {
          CardLayout cards = (CardLayout)this.root.getLayout();
          cards.show(root, "menu_creation_joueur");
        });
  }
}
