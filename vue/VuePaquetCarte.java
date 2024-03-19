package vue;

import Observer.Observer;
import controleur.DefausseListener;
import controleur.PaquetCarteListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modele.Carte;
import modele.PaquetCarte;

public class VuePaquetCarte extends JPanel implements Observer {
  private Fenetre fenetre;
  
  private PaquetCarte paquet;
  
  private JPanel vuePaquet;
  
  private JPanel vueDefausse;
  
  private JLabel namePaquet;
  
  private JLabel defausseText;
  
  private JOptionPane showMessagePioche;
  
  private JDialog dialogMessagePioche;
  
  private PaquetCarteListener paquetListener;
  
  private DefausseListener defausseListener;
  
  private void drawPaquetBorder(PaquetCarte pc, JPanel paquet) {
    switch (pc.getType()) {
      case Tempete:
        paquet.setBorder(BorderFactory.createLineBorder(new Color(204, 0, 0), 5, true));
        break;
      case Equipement:
        paquet.setBorder(BorderFactory.createLineBorder(new Color(24, 75, 252), 5, true));
        break;
      case Aventurier:
        paquet.setBorder(BorderFactory.createLineBorder(new Color(43, 148, 36), 5, true));
        break;
    } 
  }
  
  public VuePaquetCarte(Fenetre f, PaquetCarte pc, String name, boolean afficheDefausse) {
    setLayout(new BorderLayout());
    this.paquet = pc;
    this.namePaquet = new JLabel(name);
    this.paquetListener = new PaquetCarteListener(this.paquet, this);
    JPanel centerPanel = new JPanel();
    this.vuePaquet = new JPanel();
    this.vuePaquet.add(this.namePaquet);
    this.vuePaquet.setPreferredSize(new Dimension(100, 150));
    drawPaquetBorder(pc, this.vuePaquet);
    this.vuePaquet.addMouseListener((MouseListener)this.paquetListener);
    centerPanel.add(this.vuePaquet);
    if (afficheDefausse) {
      this.vueDefausse = new JPanel();
      this.vueDefausse.setPreferredSize(new Dimension(100, 150));
      drawPaquetBorder(pc, this.vueDefausse);
      this.defausseText = new JLabel("");
      this.vueDefausse.add(new JLabel("Defausse"));
      this.vueDefausse.add(this.defausseText);
      this.defausseListener = new DefausseListener(this);
      this.vueDefausse.addMouseListener((MouseListener)this.defausseListener);
      centerPanel.add(this.vueDefausse);
    } 
    add(centerPanel, "Center");
    this.showMessagePioche = new JOptionPane("Simple Information Message", 1, -1, null, new Object[0], null);
    this.showMessagePioche.setPreferredSize(new Dimension(600, 200));
    this.dialogMessagePioche = new JDialog();
    this.dialogMessagePioche.setSize(600, 200);
    this.dialogMessagePioche.setLocationRelativeTo(this);
    this.dialogMessagePioche.setTitle("Pioche");
    this.dialogMessagePioche.setModal(true);
    this.dialogMessagePioche.setContentPane(this.showMessagePioche);
    this.dialogMessagePioche.setDefaultCloseOperation(2);
    this.dialogMessagePioche.pack();
  }
  
  public PaquetCarteListener getPaquetListener() {
    return this.paquetListener;
  }
  
  public Fenetre getFenetre() {
    return this.fenetre;
  }
  
  public PaquetCarte getPaquet() {
    return this.paquet;
  }
  
  public void update() {
    Carte pioche = this.paquet.getTeteDefausse();
    if (this.defausseText != null)
      this.defausseText.setText(pioche.toString()); 
    this.showMessagePioche.setMessage(pioche.messageCarte());
    if (this.paquet.getType() != PaquetCarte.TypePaquet.Tempete)
      this.dialogMessagePioche.setVisible(true); 
  }
}
