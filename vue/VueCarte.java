package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modele.Carte;
import modele.CarteEquipement;
import modele.CarteTempete;
import modele.Case;
import modele.Desert;
import modele.GestionEquipement;
import modele.Joueur;
import modele.PaquetCarte;

public class VueCarte extends JPanel {
  private Carte carte;
  
  private PaquetCarte temp;
  
  private PaquetCarte paquetEquipement;
  
  private JLabel nomCarte;
  
  private JDialog meterologueVoirTemp;
  
  private JButton pushBackButton;
  
  private JButton jouerEquipement;
  
  private JButton echangerEquipement;
  
  private JButton infoEquipement;
  
  private Joueur joueur;
  
  private VueJoueur vueJoueur;
  
  public VueCarte(PaquetCarte temp, JDialog d, Carte c, int width, int heigth) {
    this.carte = c;
    this.temp = temp;
    this.meterologueVoirTemp = d;
    setPreferredSize(new Dimension(width, heigth));
    this.pushBackButton = new JButton("<html>Placer en <br/>dessous du paquet</html>");
    this.pushBackButton.addActionListener(e -> {
          if (this.carte instanceof CarteTempete) {
            this.temp.removeCarte(this.carte);
            this.temp.pushBack(this.carte);
            this.meterologueVoirTemp.setVisible(false);
          } 
        });
    this.nomCarte = new JLabel(stringCarte());
    add(this.nomCarte);
    add(this.pushBackButton);
  }
  
  private String stringCarte() {
    String affichageCarte = "";
    if (this.carte instanceof CarteTempete) {
      affichageCarte = affichageCarte + "<html>Carte tempete </html>";
      switch (((CarteTempete)this.carte).getType()) {
        case ReserveEau:
          affichageCarte = affichageCarte + affichageCarte + "<br/>";
          affichageCarte = affichageCarte + affichageCarte + "<br/>";
          affichageCarte = affichageCarte + affichageCarte + " case</html>";
          break;
        case Terrascope:
        case Blaster:
          affichageCarte = affichageCarte + affichageCarte + "<br/>";
          break;
      } 
    } else if (this.carte instanceof CarteEquipement) {
      affichageCarte = affichageCarte + affichageCarte;
    } 
    return affichageCarte;
  }
  
  public VueCarte(PaquetCarte equipement, Carte c, Joueur j, VueJoueur vj, JDialog d, int width, int heigth) {
    this.paquetEquipement = equipement;
    this.carte = c;
    this.meterologueVoirTemp = d;
    this.joueur = j;
    this.vueJoueur = vj;
    setPreferredSize(new Dimension(width, heigth));
    setLayout(new BorderLayout());
    this.nomCarte = new JLabel(stringCarte());
    this.jouerEquipement = new JButton("Jouer");
    this.echangerEquipement = new JButton("Echanger");
    this.infoEquipement = new JButton("Help");
    this.jouerEquipement.addActionListener(e -> {
          Case currentCase;
          CarteEquipement tmpEquipement = (CarteEquipement)this.carte;
          this.joueur.removeEquipement((CarteEquipement)this.carte);
          this.paquetEquipement.addToDefausse(this.carte);
          switch (tmpEquipement.getType()) {
            case ReserveEau:
              currentCase = this.joueur.getCurrentCase();
              for (Joueur tmpJ : currentCase.getJoueurs()) {
                tmpJ.incrNiveauEau();
                tmpJ.incrNiveauEau();
              } 
              break;
            case Terrascope:
              Desert.setActionTerrascope();
              break;
            case Blaster:
              Desert.setActionBlaster();
              Desert.setSrcAction(this.joueur);
              break;
            case AccelerateurTemps:
              this.joueur.incrActions();
              this.joueur.incrActions();
              break;
            case BouclierSolaire:
              Desert.setActivationBouclierCeTour();
              Desert.setSrcActivationBouclierSolaire(this.joueur);
              this.vueJoueur.getVuePlayer(this.joueur).setProtectionSolaireVisible();
              break;
            case JetPack:
              Desert.setActionJetpack();
              Desert.setSrcAction(this.joueur);
              break;
          } 
          this.joueur.notifyObservers();
          this.carte.getD().notifyObservers();
          this.meterologueVoirTemp.setVisible(false);
        });
    this.echangerEquipement.addActionListener(e -> {
          Case caseJoueur = this.joueur.getCurrentCase();
          if (caseJoueur.getJoueurs().size() > 1) {
            GestionEquipement.setCarteAEchanger((CarteEquipement)this.carte);
            GestionEquipement.setSrcEchangeEquipement(this.joueur);
            Case.nonClickable();
            for (Joueur tmpJ : caseJoueur.getJoueurs()) {
              if (tmpJ != this.joueur) {
                VueJoueur.VueJoueurAux vjx = this.vueJoueur.getVuePlayer(tmpJ);
                vjx.getQuiDonnerEquipement().setVisible(true);
              } 
            } 
            this.joueur.notifyObservers();
            this.meterologueVoirTemp.setVisible(false);
          } 
        });
    this.infoEquipement.addActionListener(e -> {
          JDialog dialogHelp = new JDialog();
          JOptionPane helpPane = new JOptionPane("Simple Information Message", 1, -1, null, new Object[0], null);
          helpPane.setMessage(this.carte.messageCarte());
          dialogHelp.setSize(600, 200);
          dialogHelp.setLocationRelativeTo(this);
          dialogHelp.setTitle("Pioche");
          dialogHelp.setModal(true);
          dialogHelp.setContentPane(helpPane);
          dialogHelp.setDefaultCloseOperation(2);
          dialogHelp.pack();
          dialogHelp.setVisible(true);
        });
    JPanel tmp = new JPanel(new GridBagLayout());
    tmp.add(this.nomCarte, new GridBagConstraints());
    add(tmp, "Center");
    tmp = new JPanel();
    tmp.add(this.jouerEquipement);
    tmp.add(this.echangerEquipement);
    tmp.add(this.infoEquipement);
    add(tmp, "Last");
  }
  
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.carte instanceof CarteTempete) {
      setBorder(BorderFactory.createLineBorder(new Color(204, 0, 0), 5, true));
    } else if (this.carte instanceof CarteEquipement) {
      setBorder(BorderFactory.createLineBorder(new Color(24, 75, 252), 5, true));
    } 
  }
}
