package vue;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import modele.Desert;
import modele.Joueur;
import modele.PaquetCarte;

public class MenuCreationJoueur extends JPanel {
  private JSlider nbJoueurComponent;
  
  private int currentPlayer;
  
  private Desert desert;
  
  private PaquetCarte paquetAventurier;
  
  private Joueur.ClassJoueur tmpClassJoueur;
  
  private final class LengthRestrictedDocument extends PlainDocument {
    private final int limit;
    
    public LengthRestrictedDocument(int limit) {
      this.limit = limit;
    }
    
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
      if (str == null)
        return; 
      if (getLength() + str.length() <= this.limit)
        super.insertString(offs, str, a); 
    }
  }
  
  private JPanel initSelectionNbJoueur() {
    JPanel panneauNbJoueur = new JPanel();
    panneauNbJoueur.setLayout(new GridBagLayout());
    panneauNbJoueur.setPreferredSize(new Dimension(100, 100));
    JLabel nbJoueurText = new JLabel("Nombre de joueur : ");
    JButton next = new JButton("Suivant");
    next.addActionListener(e -> {
          int nbJoueur = this.nbJoueurComponent.getValue();
          CardLayout cards = (CardLayout)getLayout();
          cards.show(this, "creation_joueur_" + this.currentPlayer);
        });
    this.nbJoueurComponent = new JSlider(0, 2, 5, 2);
    this.nbJoueurComponent.setMajorTickSpacing(1);
    this.nbJoueurComponent.setMinorTickSpacing(1);
    this.nbJoueurComponent.setPaintTicks(true);
    this.nbJoueurComponent.setPaintLabels(true);
    panneauNbJoueur.add(nbJoueurText, new GridBagConstraints());
    panneauNbJoueur.add(this.nbJoueurComponent, new GridBagConstraints());
    panneauNbJoueur.add(next, new GridBagConstraints());
    return panneauNbJoueur;
  }
  
  private JPanel initCreationJoueur(Fenetre f, JPanel rootPanel) {
    VuePaquetCarte vuePaquetAventurier = new VuePaquetCarte(f, this.paquetAventurier, "<html>Paquet<br/>Aventurier</html>", false);
    JPanel creationJoueur = new JPanel();
    creationJoueur.setLayout(new GridBagLayout());
    creationJoueur.setPreferredSize(new Dimension(100, 100));
    JLabel infoNom = new JLabel("Nom Joueur : ");
    final JTextField nomJoueur = new JTextField(20);
    nomJoueur.setDocument(new LengthRestrictedDocument(15));
    final JButton next_player = new JButton("Suivant");
    next_player.setEnabled(false);
    vuePaquetAventurier.getPaquetListener().setTextAndButtonField(nomJoueur, next_player);
    nomJoueur.getDocument().addDocumentListener(new DocumentListener() {
          public void insertUpdate(DocumentEvent documentEvent) {
            if (nomJoueur.getText().length() == 0 || MenuCreationJoueur.this.tmpClassJoueur == null) {
              next_player.setEnabled(false);
            } else {
              next_player.setEnabled(true);
            } 
          }
          
          public void removeUpdate(DocumentEvent documentEvent) {
            insertUpdate(documentEvent);
          }
          
          public void changedUpdate(DocumentEvent documentEvent) {}
        });
    next_player.addActionListener(e -> {
          Joueur new_player = new Joueur(nomJoueur.getText(), this.tmpClassJoueur);
          this.desert.addJoueur(new_player);
          this.tmpClassJoueur = null;
          if (this.currentPlayer < this.nbJoueurComponent.getValue() - 1) {
            vuePaquetAventurier.getPaquetListener().setTextAndButtonField(nomJoueur, next_player);
            nomJoueur.setText("");
            CardLayout cards = (CardLayout)getLayout();
            cards.show(this, "creation_joueur_" + this.currentPlayer);
            this.currentPlayer++;
          } else {
            this.desert.crash();
            CardLayout cards = (CardLayout)rootPanel.getLayout();
            cards.show(rootPanel, "vue_jeu");
          } 
        });
    creationJoueur.add(infoNom, new GridBagConstraints());
    creationJoueur.add(nomJoueur, new GridBagConstraints());
    creationJoueur.add(vuePaquetAventurier, new GridBagConstraints());
    creationJoueur.add(next_player, new GridBagConstraints());
    return creationJoueur;
  }
  
  public MenuCreationJoueur(Fenetre f, JPanel rootPanel, Desert d) {
    this.desert = d;
    this.paquetAventurier = new PaquetCarte(this.desert, this, PaquetCarte.TypePaquet.Aventurier);
    this.tmpClassJoueur = null;
    setLayout(new CardLayout());
    this.currentPlayer = 0;
    JPanel panneauSelectionJoueur = initSelectionNbJoueur();
    add(panneauSelectionJoueur, "selection_joueur");
    for (int i = 0; i < 6; i++)
      add(initCreationJoueur(f, rootPanel), "creation_joueur_" + i); 
  }
  
  public void setTmpClassJoueur(Joueur.ClassJoueur cj) {
    this.tmpClassJoueur = cj;
  }
  
  public Joueur.ClassJoueur getTmpClassJoueur() {
    return this.tmpClassJoueur;
  }
}
