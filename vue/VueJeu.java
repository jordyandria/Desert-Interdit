package vue;

import Observer.Observer;
import controleur.ExplorationButton;
import controleur.FinDeTour;
import controleur.PaquetCarteListener;
import controleur.PrisePieceButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Desert;
import modele.Joueur;
import modele.PaquetCarte;

public class VueJeu extends JPanel implements Observer {
  private Fenetre mainWindow;
  
  private BorderLayout jeuLayout;
  
  private Desert d;
  
  private JLabel totalSable;
  
  private JLabel textJoueurCourant;
  
  private JLabel infoTour;
  
  private VuePaquetCarte vuePaquetEquipement;
  
  private VuePaquetCarte vuePaquetTempete;
  
  private VueNiveauTempete vueNiveauTempete;
  
  private FinDeTour finDeTour;
  
  private JDialog finJeu;
  
  private class JoueurWidgetAux extends JoueurWidget {
    public JoueurWidgetAux() {
      super(null);
    }
    
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(VueJeu.this.d.getJoueurCourant().getClassJoueur().getClassColor());
      g.fillRect(2, 0, 20, 20);
      g.setColor(new Color(0, 0, 0));
    }
  }
  
  public VueJeu(Fenetre f, Desert d, PaquetCarte paquetTempete, PaquetCarte paquetEquipement) {
    this.mainWindow = f;
    this.jeuLayout = new BorderLayout();
    this.jeuLayout.setHgap(5);
    this.jeuLayout.setVgap(5);
    setLayout(this.jeuLayout);
    this.d = d;
    this.vuePaquetTempete = new VuePaquetCarte(f, paquetTempete, "<html>Paquet<br/>Tempete </html>", true);
    this.vuePaquetEquipement = new VuePaquetCarte(f, paquetEquipement, "<html>Paquet<br/>Equipement</html>", false);
    JPanel panneauHaut = new JPanel();
    panneauHaut.setLayout(new BoxLayout(panneauHaut, 2));
    panneauHaut.add(this.vuePaquetEquipement);
    VueInfoJeu InfoJeu = new VueInfoJeu(this.d, paquetTempete);
    panneauHaut.add(InfoJeu);
    panneauHaut.add(this.vuePaquetTempete);
    this.totalSable = new JLabel("Total Sable: " + d.countSable());
    this.vueNiveauTempete = new VueNiveauTempete(d);
    JPanel infoDesert = new JPanel();
    infoDesert.setLayout(new BoxLayout(infoDesert, 1));
    VueJoueur vj = new VueJoueur(f, this.d, paquetTempete, paquetEquipement);
    infoDesert.add(this.totalSable);
    infoDesert.add(this.vueNiveauTempete);
    infoDesert.add(vj);
    VueDesert vueDesert = new VueDesert(f, d, 5, 5);
    this.finJeu = new JDialog(this.mainWindow, "Fin du Jeu");
    this.finJeu.setSize(200, 200);
    this.finJeu.setLocationRelativeTo(null);
    this.finJeu.setModal(true);
    this.finJeu.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            System.exit(0);
          }
        });
    JPanel panneauDroit = new JPanel();
    JPanel tmpPanneauDroit = new JPanel();
    tmpPanneauDroit.setLayout(new BoxLayout(tmpPanneauDroit, 1));
    this.finDeTour = new FinDeTour(this.d, paquetTempete, this.finJeu);
    ExplorationButton expButton = new ExplorationButton(this.d);
    PrisePieceButton priseButton = new PrisePieceButton(this.d);
    JPanel tmpPanel = new JPanel();
    tmpPanel.setLayout(new GridBagLayout());
    JPanel vueJoueurCourant = new JPanel();
    vueJoueurCourant.setLayout(new BoxLayout(vueJoueurCourant, 0));
    this.textJoueurCourant = new JLabel("Tour du joueur ");
    vueJoueurCourant.add(this.textJoueurCourant);
    JoueurWidgetAux jw = new JoueurWidgetAux();
    vueJoueurCourant.add(jw);
    tmpPanel.add(vueJoueurCourant, new GridBagConstraints());
    this.infoTour = new JLabel();
    tmpPanneauDroit.add((Component)expButton);
    tmpPanneauDroit.add((Component)priseButton);
    tmpPanneauDroit.add((Component)this.finDeTour);
    tmpPanneauDroit.add(tmpPanel);
    tmpPanneauDroit.add(this.infoTour);
    panneauDroit.add(tmpPanneauDroit);
    add(infoDesert, "Before");
    add(vueDesert, "Center");
    add(panneauHaut, "First");
    add(panneauDroit, "After");
    d.addObserver(this);
  }
  
  public String descriptionJeu() {
    String res = "<html>";
    if (PaquetCarteListener.getPaquetClickable()) {
      res = res + "Vous avez explore une case contenant un engrenage<br/>Piochez une carte equipement" ;
    } else if (this.d.getJoueurCourant().getActions() > 0) {
      res = res + "Phase d'action, vous pouvez faire 4 actions parmis:<br/> * Dep de 1 cases (Clique gauche)<br/> * Dune case (Clique droit)<br/> * Explorer une case<br/> * Prendre une piece";
    } else if (this.d.getJoueurCourant().getActions() == 0) {
      int restePioche = this.d.getNiveauTemp() - this.vuePaquetTempete.getPaquet().getCountCartePioche();
      res = res + "Phase de la temp Piochez un nombre de carte de tempau niveau de la temp Restant piocher: " + res + " carte(s)";
      if (restePioche == 0)
        res = res + "<br/><br/>Aucune carte temppiocher, cliquez sur Fin du tour"; 
    } 
    return res = res + "</html>";
  }
  
  public void update() {
    this.totalSable.setText("Total Sable: " + this.d.countSable());
    Joueur courant = this.d.getJoueurCourant();
    if (courant != null)
      this.textJoueurCourant.setText("Tour du joueur : " + courant.getNom()); 
    this.infoTour.setText(descriptionJeu());
    repaint();
    revalidate();
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
