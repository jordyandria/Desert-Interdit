package vue;

import Observer.Observer;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Carte;
import modele.CarteEquipement;
import modele.Case;
import modele.Coordonnee;
import modele.Desert;
import modele.GestionEquipement;
import modele.Joueur;
import modele.PaquetCarte;

public class VueJoueur extends JPanel implements Observer {
  private ArrayList<VueJoueurAux> allVueJoueur;
  
  private Desert desert;
  
  private boolean initJoueur;
  
  private PaquetCarte paquetTemp;
  
  private PaquetCarte paquetEquipement;
  
  private Fenetre f;
  
  public class VueJoueurAux extends JPanel implements Observer {
    private VueJoueur vueJoueurs;
    
    private Joueur joueur;
    
    private JLabel nomJoueur;
    
    private JPanel vueClass;
    
    private JLabel classJoueur;
    
    private JoueurWidget widgetClass;
    
    private JPanel vuePieceRecupere;
    
    private int nbPieceAffiche;
    
    private JLabel actions;
    
    private JLabel niveauEau;
    
    private JButton donnerEau;
    
    private JButton quiDonnerEau;
    
    private JButton alpinisteSelectPlayer;
    
    private VueJoueurAux alpinisteGoPlayer;
    
    private JLabel infoAlpiniste;
    
    private JButton navigatriceSelectPlayer;
    
    private VueJoueurAux navigatriceGoPlayer;
    
    private JLabel infoNavigatrice;
    
    private JButton meteorologueSkipCarte;
    
    private JButton meteorologueVueCarte;
    
    private JDialog meterologueVoirTemp;
    
    private JButton goVueEquipements;
    
    private JDialog vueCarteEquipement;
    
    private JButton quiDonnerEquipement;
    
    private JButton emmenerJetPack;
    
    private JLabel textEmmenerJetpack;
    
    private JLabel protectionSolaire;
    
    public VueJoueurAux(Joueur j, VueJoueur vj) {
      this.vueJoueurs = vj;
      this.joueur = j;
      setLayout(new BoxLayout(this, 1));
      this.nomJoueur = new JLabel("Joueur : " + j.getNom());
      this.classJoueur = new JLabel("Classe : " + j.getClassJoueur());
      this.widgetClass = new JoueurWidget(this.joueur);
      this.vueClass = new JPanel();
      this.vueClass.add(this.classJoueur);
      this.vueClass.add(this.widgetClass);
      this.nbPieceAffiche = 0;
      this.vuePieceRecupere = new JPanel();
      JLabel labelPiece = new JLabel("Piece r");
      this.vuePieceRecupere.add(labelPiece);
      this.vuePieceRecupere.setVisible(false);
      this.actions = new JLabel("Actions : " + j.getActions());
      this.niveauEau = new JLabel("Niveau d'eau : " + j.getNiveauEau() + " (max Eau: " + this.joueur.getClassJoueur().getMaxEau() + ")");
      this.donnerEau = new JButton("Donner eau");
      this.quiDonnerEau = new JButton("Qui ?");
      this.alpinisteSelectPlayer = new JButton("Emmener");
      this.infoAlpiniste = new JLabel("Va emmener par l'alpiniste");
      this.infoAlpiniste.setVisible(false);
      this.alpinisteSelectPlayer.setVisible(false);
      this.alpinisteGoPlayer = null;
      this.navigatriceSelectPlayer = new JButton("Deplacer");
      this.infoNavigatrice = new JLabel("(" + VueJoueur.this.desert.getJoueurCourant().getCountDeplacementNavigatrice() + " deplacement restant)");
      this.infoNavigatrice.setVisible(false);
      this.navigatriceSelectPlayer.setVisible(false);
      this.navigatriceGoPlayer = null;
      this.meteorologueSkipCarte = new JButton("Skip Carte Tempete");
      this.meteorologueVueCarte = new JButton("Voir carte Tempete ");
      this.meterologueVoirTemp = null;
      this.goVueEquipements = new JButton("<html>Inventaire Equipement<br/>(nombre: " + this.joueur.getEquipements().size() + ")</html>");
      this.vueCarteEquipement = null;
      this.quiDonnerEquipement = new JButton("Qui ?");
      this.quiDonnerEquipement.setVisible(false);
      this.emmenerJetPack = new JButton("Emmener");
      this.emmenerJetPack.setVisible(false);
      this.textEmmenerJetpack = new JLabel("<html>Va emmener<br/>en jetpack</html");
      this.textEmmenerJetpack.setVisible(false);
      this.protectionSolaire = new JLabel("<html>Protdes effets<br/>Vague de chaleur</html>");
      this.protectionSolaire.setVisible(false);
      setBorder(BorderFactory.createLineBorder(this.joueur.getClassJoueur().getClassColor(), 5, true));
      add(this.nomJoueur);
      add(this.vueClass);
      add(this.vuePieceRecupere);
      add(this.actions);
      add(this.niveauEau);
      add(this.goVueEquipements);
      add(this.quiDonnerEquipement);
      add(this.emmenerJetPack);
      add(this.textEmmenerJetpack);
      add(this.protectionSolaire);
      add(this.quiDonnerEau);
      add(this.donnerEau);
      add(this.meteorologueVueCarte);
      add(this.meteorologueSkipCarte);
      add(this.alpinisteSelectPlayer);
      add(this.infoAlpiniste);
      add(this.navigatriceSelectPlayer);
      add(this.infoNavigatrice);
      this.donnerEau.setVisible(true);
      this.quiDonnerEau.setVisible(false);
      this.donnerEau.addActionListener(e -> {
            GestionEquipement.setDonEauSrc(this.joueur);
            Case.nonClickable();
            for (VueJoueurAux vjx : VueJoueur.this.allVueJoueur) {
              vjx.donnerEau.setVisible(false);
              if (vjx.joueur != this.joueur && this.joueur.getCurrentCase().getCoordCase() == vjx.joueur.getCurrentCase().getCoordCase())
                vjx.quiDonnerEau.setVisible(true); 
            } 
            if (this.joueur.getClassJoueur() == Joueur.ClassJoueur.PORTEUSE_EAU) {
              Case currentCase = this.joueur.getCurrentCase();
              for (Coordonnee voisin : currentCase.getVoisins(this.joueur, false)) {
                if (voisin.getX() >= 0 && voisin.getX() < 5 && voisin.getY() >= 0 && voisin.getY() < 5) {
                  Case tmpC = VueJoueur.this.desert.getGrilleDesert()[voisin.getX()][voisin.getY()];
                  for (Joueur tmpJ : tmpC.getJoueurs()) {
                    VueJoueurAux tmpVjx = VueJoueur.this.getVuePlayer(tmpJ);
                    tmpVjx.quiDonnerEau.setVisible(true);
                  } 
                } 
              } 
            } 
          });
      this.quiDonnerEau.addActionListener(e -> {
            Case.becomeClickable();
            if (this.joueur.getNiveauEau() < this.joueur.getClassJoueur().getMaxEau()) {
              GestionEquipement.getDonEauSrc().decrNiveauEau();
              this.joueur.incrNiveauEau();
            } 
            for (VueJoueurAux vjx : VueJoueur.this.allVueJoueur) {
              vjx.quiDonnerEau.setVisible(false);
              Joueur tmpJ = vjx.joueur;
              if (tmpJ.getCurrentCase().getJoueurs().size() > 1)
                vjx.donnerEau.setVisible(true); 
            } 
            VueJoueur.this.desert.notifyObservers();
          });
      this.alpinisteSelectPlayer.addActionListener(e -> {
            if (VueJoueur.this.desert.getJoueurCourant().getClassJoueur() == Joueur.ClassJoueur.NAVIGATRICE && VueJoueur.this.desert.getJoueurCourant().getNavigatriceGoJoueur() != null && VueJoueur.this.desert.getJoueurCourant().getNavigatriceGoJoueur().getClassJoueur() == Joueur.ClassJoueur.ALPINISTE) {
              this.alpinisteGoPlayer = this;
              Joueur alpinisteGoPlayer = VueJoueur.this.desert.getJoueurCourant().getNavigatriceGoJoueur();
              alpinisteGoPlayer.setAlpinisteGoJoueur(this.joueur);
              for (VueJoueurAux tmpVjx : VueJoueur.this.allVueJoueur)
                tmpVjx.alpinisteSelectPlayer.setVisible(false); 
              this.infoAlpiniste.setVisible(true);
              return;
            } 
            this.alpinisteGoPlayer = this;
            VueJoueur.this.desert.getJoueurCourant().setAlpinisteGoJoueur(this.joueur);
            for (VueJoueurAux tmpVjx : VueJoueur.this.allVueJoueur)
              tmpVjx.alpinisteSelectPlayer.setVisible(false); 
            this.infoAlpiniste.setVisible(true);
          });
      this.navigatriceSelectPlayer.addActionListener(e -> {
            this.navigatriceGoPlayer = this;
            VueJoueur.this.desert.getJoueurCourant().setNavigatriceGoJoueur(this.joueur);
            VueJoueur.this.desert.getJoueurCourant().resetCountDeplacementNavigatrice();
            for (VueJoueurAux tmpVjx : VueJoueur.this.allVueJoueur)
              tmpVjx.navigatriceSelectPlayer.setVisible(false); 
            this.infoNavigatrice.setText("(" + VueJoueur.this.desert.getJoueurCourant().getCountDeplacementNavigatrice() + " deplacement restant)");
            this.infoNavigatrice.setVisible(true);
            VueJoueur.this.desert.notifyObservers();
          });
      this.meteorologueSkipCarte.addActionListener(e -> {
            if (VueJoueur.this.paquetTemp.getCountCartePioche() < VueJoueur.this.desert.getNiveauTemp() && this.joueur.getActions() > 0) {
              this.joueur.decrActions();
              VueJoueur.this.paquetTemp.incrCountCartePioche();
              this.joueur.notifyObservers();
              VueJoueur.this.paquetTemp.notifyObservers();
            } 
          });
      this.meteorologueVueCarte.addActionListener(e -> {
            if (this.joueur.getActions() > 0) {
              this.meterologueVoirTemp = new JDialog(VueJoueur.this.f, "Observation de la tempete");
              this.meterologueVoirTemp.setSize(500, 500);
              this.meterologueVoirTemp.setLocationRelativeTo((Component)null);
              this.meterologueVoirTemp.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
              this.joueur.decrActions();
              JPanel vueDessusPaquet = new JPanel();
              vueDessusPaquet.setPreferredSize(new Dimension(300, 300));
              vueDessusPaquet.setLayout(new BoxLayout(vueDessusPaquet, 2));
              int tmpCount = 0;
              for (Carte c : VueJoueur.this.paquetTemp.getPaquet()) {
                VueCarte vc = new VueCarte(VueJoueur.this.paquetTemp, this.meterologueVoirTemp, c, 40, 65);
                vueDessusPaquet.add(vc);
                if (++tmpCount == VueJoueur.this.desert.getNiveauTemp())
                  break; 
              } 
              this.meterologueVoirTemp.add(vueDessusPaquet);
              this.meterologueVoirTemp.setVisible(true);
              this.joueur.notifyObservers();
              VueJoueur.this.desert.notifyObservers();
            } 
          });
      this.goVueEquipements.addActionListener(e -> {
            if (!Desert.getActionBlaster() && !Desert.getActionJetpack() && !Desert.getActionTerrascope() && Desert.getSrcAction() == null) {
              this.vueCarteEquipement = new JDialog(VueJoueur.this.f, "Inventaire equipement");
              this.vueCarteEquipement.setSize(500, 500);
              this.vueCarteEquipement.setLocationRelativeTo((Component)null);
              this.vueCarteEquipement.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
              JPanel vueEquipement = new JPanel(new GridLayout(2, 6, 5, 5));
              for (CarteEquipement ce : this.joueur.getEquipements())
                vueEquipement.add(new VueCarte(VueJoueur.this.paquetEquipement, (Carte)ce, this.joueur, this.vueJoueurs, this.vueCarteEquipement, 50, 50)); 
              this.vueCarteEquipement.add(vueEquipement);
              this.vueCarteEquipement.setVisible(true);
            } 
          });
      this.quiDonnerEquipement.addActionListener(e -> {
            Case.becomeClickable();
            GestionEquipement.getSrcEchangeEquipement().removeEquipement(GestionEquipement.getCarteAEchanger());
            this.joueur.addEquipement(GestionEquipement.getCarteAEchanger());
            GestionEquipement.setCarteAEchanger(null);
            GestionEquipement.getSrcEchangeEquipement().notifyObservers();
            GestionEquipement.setSrcEchangeEquipement(null);
            this.joueur.notifyObservers();
            VueJoueur.this.desert.notifyObservers();
          });
      this.emmenerJetPack.addActionListener(e -> {
            Desert.setJoueurEmmenerJetpack(this.joueur);
            this.textEmmenerJetpack.setVisible(true);
            for (VueJoueurAux vjx : VueJoueur.this.allVueJoueur)
              this.emmenerJetPack.setVisible(false); 
          });
      j.addObserver(this);
    }
    
    public JButton getQuiDonnerEquipement() {
      return this.quiDonnerEquipement;
    }
    
    public void setProtectionSolaireVisible() {
      this.protectionSolaire.setVisible(true);
    }
    
    public void update() {
      if (this.joueur.getPieces().size() > 0)
        this.vuePieceRecupere.setVisible(true); 
      if (this.nbPieceAffiche != this.joueur.getPieces().size()) {
        this.nbPieceAffiche++;
        Desert.PieceMachine pm = this.joueur.getPieces().get(this.joueur.getPieces().size() - 1);
        PieceWidget pw = new PieceWidget(pm);
        this.vuePieceRecupere.add(pw);
      } 
      if (this.infoAlpiniste.isVisible())
        this.infoAlpiniste.setVisible(false); 
      if (this.joueur.getClassJoueur() == Joueur.ClassJoueur.ALPINISTE && this.joueur
        .getAlpinisteGoJoueur() != null)
        this.joueur.setAlpinisteGoJoueur(null); 
      if (this.infoNavigatrice.isVisible() && VueJoueur.this.desert.getJoueurCourant().getCountDeplacementNavigatrice() == 0)
        this.infoNavigatrice.setVisible(false); 
      if (this.joueur.getClassJoueur() == Joueur.ClassJoueur.NAVIGATRICE && this.joueur
        .getNavigatriceGoJoueur() != null && this.joueur.getCountDeplacementNavigatrice() == 0) {
        this.joueur.setNavigatriceGoJoueur(null);
        this.navigatriceGoPlayer = null;
      } 
      if (VueJoueur.this.desert.getJoueurCourant().getClassJoueur() == Joueur.ClassJoueur.METEOROLOGUE && this.joueur
        .getClassJoueur() == Joueur.ClassJoueur.METEOROLOGUE)
        VueJoueur.this.paquetTemp.notifyObservers(); 
      this.goVueEquipements.setText("<html>Inventaire Equipement<br/>(nombre: " + this.joueur.getEquipements().size() + ")</html>");
      this.quiDonnerEquipement.setVisible(false);
      if (this.textEmmenerJetpack.isVisible())
        this.textEmmenerJetpack.setVisible(false); 
      if (this.protectionSolaire.isVisible() && Desert.getSrcActivationBouclierSolaire() == null)
        this.protectionSolaire.setVisible(false); 
      revalidate();
      repaint();
    }
    
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      this.niveauEau.setText("Niveau d'eau : " + this.joueur.getNiveauEau() + " (max Eau: " + this.joueur.getClassJoueur().getMaxEau() + ")");
      this.actions.setText("Actions : " + this.joueur.getActions());
      this.infoNavigatrice.setText("(" + VueJoueur.this.desert.getJoueurCourant().getCountDeplacementNavigatrice() + " deplacement restant)");
    }
  }
  
  public VueJoueur(Fenetre f, Desert d, PaquetCarte temp, PaquetCarte paquetEquipement) {
    this.desert = d;
    this.paquetTemp = temp;
    this.paquetEquipement = paquetEquipement;
    this.f = f;
    this.initJoueur = false;
    this.allVueJoueur = new ArrayList<>();
    setLayout(new GridLayout(3, 2, 2, 2));
    this.desert.addObserver(this);
  }
  
  public VueJoueurAux getVuePlayer(Joueur j) {
    for (VueJoueurAux vjx : this.allVueJoueur) {
      if (vjx.joueur == j)
        return vjx; 
    } 
    return null;
  }
  
  public void update() {
    if (!this.initJoueur) {
      for (Joueur j : this.desert.getJoueurs()) {
        VueJoueurAux vueJoueur = new VueJoueurAux(j, this);
        this.allVueJoueur.add(vueJoueur);
        add(vueJoueur);
      } 
      this.initJoueur = true;
    } 
    for (VueJoueurAux vjx : this.allVueJoueur) {
      Joueur j = vjx.joueur;
      boolean voisinsExiste = false;
      if (j.getClassJoueur() == Joueur.ClassJoueur.PORTEUSE_EAU)
        for (Coordonnee v : j.getCurrentCase().getVoisins(j, false)) {
          if (v.getX() >= 0 && v.getX() < 5 && v.getY() >= 0 && v.getY() < 5 && 
            this.desert.getGrilleDesert()[v.getX()][v.getY()].getJoueurs().size() > 0)
            voisinsExiste = true; 
        }  
      if (j.getCurrentCase().getJoueurs().size() > 1 || (j.getClassJoueur() == Joueur.ClassJoueur.PORTEUSE_EAU && voisinsExiste)) {
        vjx.donnerEau.setVisible(true);
        continue;
      } 
      vjx.donnerEau.setVisible(false);
    } 
    Joueur current = this.desert.getJoueurCourant();
    if (current.getClassJoueur() == Joueur.ClassJoueur.ALPINISTE && current.getActions() > 0) {
      Case currentCase = current.getCurrentCase();
      for (Joueur j : this.desert.getJoueurs()) {
        VueJoueurAux tmpVjx = getVuePlayer(j);
        if (j != current && currentCase.getJoueurs().contains(j)) {
          tmpVjx.alpinisteSelectPlayer.setVisible(true);
          continue;
        } 
        tmpVjx.alpinisteSelectPlayer.setVisible(false);
      } 
    } else {
      for (VueJoueurAux tmpVjx : this.allVueJoueur)
        tmpVjx.alpinisteSelectPlayer.setVisible(false); 
    } 
    if (current.getClassJoueur() == Joueur.ClassJoueur.NAVIGATRICE && current.getNavigatriceGoJoueur() != null)
      if (current.getNavigatriceGoJoueur().getClassJoueur() == Joueur.ClassJoueur.ALPINISTE && current
        .getCountDeplacementNavigatrice() > 0 && current.getActions() > 0) {
        Joueur alpinisteGoNav = current.getNavigatriceGoJoueur();
        Case currentCaseAlpGoNav = alpinisteGoNav.getCurrentCase();
        for (Joueur j : this.desert.getJoueurs()) {
          VueJoueurAux tmpVjx = getVuePlayer(j);
          if (j != alpinisteGoNav && currentCaseAlpGoNav.getJoueurs().contains(j)) {
            tmpVjx.alpinisteSelectPlayer.setVisible(true);
            continue;
          } 
          tmpVjx.alpinisteSelectPlayer.setVisible(false);
        } 
      } else {
        for (VueJoueurAux tmpVjx : this.allVueJoueur)
          tmpVjx.alpinisteSelectPlayer.setVisible(false); 
      }  
    if (current.getClassJoueur() == Joueur.ClassJoueur.NAVIGATRICE && current.getActions() > 0) {
      for (Joueur j : this.desert.getJoueurs()) {
        VueJoueurAux tmpVjx = getVuePlayer(j);
        if (j != current && current.getNavigatriceGoJoueur() == null) {
          tmpVjx.navigatriceSelectPlayer.setVisible(true);
          continue;
        } 
        tmpVjx.navigatriceSelectPlayer.setVisible(false);
      } 
    } else {
      for (VueJoueurAux tmpVjx : this.allVueJoueur)
        tmpVjx.navigatriceSelectPlayer.setVisible(false); 
    } 
    if (current.getClassJoueur() == Joueur.ClassJoueur.METEOROLOGUE) {
      for (VueJoueurAux vjx : this.allVueJoueur) {
        if (vjx.joueur == current) {
          vjx.meteorologueVueCarte.setVisible(true);
          vjx.meteorologueSkipCarte.setVisible(true);
          continue;
        } 
        vjx.meteorologueVueCarte.setVisible(false);
        vjx.meteorologueSkipCarte.setVisible(false);
      } 
    } else {
      for (VueJoueurAux vjx : this.allVueJoueur) {
        vjx.meteorologueVueCarte.setVisible(false);
        vjx.meteorologueSkipCarte.setVisible(false);
      } 
    } 
    if (Desert.getActionJetpack() && Desert.getSrcAction() != null) {
      Joueur srcEquipement = Desert.getSrcAction();
      for (Joueur tmpJ : srcEquipement.getCurrentCase().getJoueurs()) {
        VueJoueurAux tmpVjx = getVuePlayer(tmpJ);
        if (srcEquipement != tmpJ) {
          tmpVjx.emmenerJetPack.setVisible(true);
          continue;
        } 
        tmpVjx.emmenerJetPack.setVisible(false);
      } 
    } else {
      for (VueJoueurAux tmpVjx : this.allVueJoueur)
        tmpVjx.emmenerJetPack.setVisible(false); 
    } 
    revalidate();
    repaint();
  }
}
