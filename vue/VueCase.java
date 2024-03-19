package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modele.Case;
import modele.Coordonnee;
import modele.Desert;
import modele.Joueur;

public class VueCase extends JPanel implements MouseListener {
  private Fenetre f;
  
  private Case c;
  
  private JLabel labelCase;
  
  public VueCase(Fenetre f, Case c) {
    this.f = f;
    this.c = c;
    setPreferredSize(new Dimension(100, 100));
    addMouseListener(this);
    this.labelCase = new JLabel(this.c.toString());
    if (this.c.getTypeCase() == Case.TypeCase.OEIL)
      this.labelCase.setText(""); 
    add(this.labelCase);
  }
  
  public boolean accessibleDeplacement(Joueur current, ArrayList<Coordonnee> voisins) {
    Case currentPlayerCase = current.getCurrentCase();
    boolean currentCaseContainsAlpiniste = false;
    ArrayList<Joueur> playersCurrentCase = current.getCurrentCase().getJoueurs();
    for (Joueur j : playersCurrentCase) {
      if (j.getClassJoueur() == Joueur.ClassJoueur.ALPINISTE) {
        currentCaseContainsAlpiniste = true;
        break;
      } 
    } 
    boolean accessibleAlpiniste = (this.c.getTonneSable() < 2 || current.getClassJoueur() == Joueur.ClassJoueur.ALPINISTE);
    boolean accessibleCurrentAlpiniste = (current.getCurrentCase().getTonneSable() < 2 || current.getClassJoueur() == Joueur.ClassJoueur.ALPINISTE || currentCaseContainsAlpiniste);
    boolean allPlayerAccessible = (this.c.getTypeCase() != Case.TypeCase.OEIL && accessibleCurrentAlpiniste && current.getActions() > 0 && accessibleAlpiniste && voisins.contains(this.c.getCoordCase()));
    boolean tunnelAccessible = (currentPlayerCase.getTypeCase() == Case.TypeCase.TUNNELS && currentPlayerCase.getExploreCase() && accessibleCurrentAlpiniste && this.c.getTypeCase() == Case.TypeCase.TUNNELS && accessibleAlpiniste && this.c.getExploreCase() && current.getActions() > 0);
    return (allPlayerAccessible || tunnelAccessible);
  }
  
  public boolean accessibleDesensablement(Joueur current, ArrayList<Coordonnee> voisins) {
    Case caseJoueurCourant = current.getCurrentCase();
    return (current.getActions() > 0 && ((voisins.contains(this.c.getCoordCase()) && this.c
      .getTonneSable() > 0) || (this.c
      .getCoordCase() == caseJoueurCourant.getCoordCase() && caseJoueurCourant
      .getTonneSable() > 0)));
  }
  
  public void setCase(Case c) {
    this.c = c;
  }
  
  private void deplacementAlpiniste(Joueur current, Case currentPlayerCase) {
    if (current.getClassJoueur() == Joueur.ClassJoueur.ALPINISTE) {
      Joueur alpinisteGoJoueur = current.getAlpinisteGoJoueur();
      if (alpinisteGoJoueur != null) {
        currentPlayerCase.removeJoueur(alpinisteGoJoueur);
        this.c.addJoueur(alpinisteGoJoueur);
        alpinisteGoJoueur.notifyObservers();
      } 
    } 
  }
  
  public void mouseClicked(MouseEvent mouseEvent) {
    if (!Case.isClickable())
      return; 
    if (Desert.getActionBlaster() && Desert.getSrcAction() != null) {
      Joueur j = Desert.getSrcAction();
      ArrayList<Coordonnee> arrayList = j.getCurrentCase().getVoisins(j, (j.getClassJoueur() == Joueur.ClassJoueur.EXPLORATEUR));
      if (accessibleDesensablement(j, arrayList)) {
        this.c.blasterSable();
        Desert.setActionBlaster();
        Desert.setSrcAction(null);
        this.c.getDesert().notifyObservers();
      } 
      return;
    } 
    if (Desert.getActionTerrascope() && !this.c.getExploreCase() && this.c.getTypeCase() != Case.TypeCase.OEIL) {
      JOptionPane.showMessageDialog(this.f, "<html>Case: " + this.c
          
          .getTypeCase() + "<br/>ContientEquipement: " + this.c.hasEngrenage() + "</html>", "Terrascope", 1);
      Desert.setActionTerrascope();
      this.c.getDesert().notifyObservers();
      return;
    } 
    if (Desert.getActionJetpack() && Desert.getSrcAction() != null && this.c.getTonneSable() < 2) {
      Joueur srcJetpack = Desert.getSrcAction();
      Case currentSrcCase = srcJetpack.getCurrentCase();
      currentSrcCase.removeJoueur(srcJetpack);
      this.c.addJoueur(srcJetpack);
      if (Desert.getJoueurEmmenerJetpack() != null) {
        Joueur compagnonJetpack = Desert.getJoueurEmmenerJetpack();
        currentSrcCase.removeJoueur(compagnonJetpack);
        this.c.addJoueur(compagnonJetpack);
        compagnonJetpack.notifyObservers();
        Desert.setJoueurEmmenerJetpack(null);
      } 
      srcJetpack.notifyObservers();
      Desert.setActionJetpack();
      Desert.setSrcAction(null);
      this.c.getDesert().notifyObservers();
      return;
    } 
    Joueur current = this.c.getDesert().getJoueurCourant();
    boolean isExplorateur = (current.getClassJoueur() == Joueur.ClassJoueur.EXPLORATEUR);
    ArrayList<Coordonnee> voisins = this.c.getVoisins(current, isExplorateur);
    if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
      if (current.getClassJoueur() == Joueur.ClassJoueur.NAVIGATRICE)
        if (current.getNavigatriceGoJoueur() != null) {
          Joueur navGoJoueur = current.getNavigatriceGoJoueur();
          boolean navGoJoueurIsExplorateur = (navGoJoueur.getClassJoueur() == Joueur.ClassJoueur.EXPLORATEUR);
          ArrayList<Coordonnee> navGovoisins = this.c.getVoisins(navGoJoueur, navGoJoueurIsExplorateur);
          if (accessibleDeplacement(navGoJoueur, navGovoisins)) {
            current.decrCountDeplacementNavigatrice();
            navGoJoueur.getCurrentCase().removeJoueur(navGoJoueur);
            Joueur alpinisteGoPlayer = current.getNavigatriceGoJoueur();
            if (alpinisteGoPlayer != null)
              deplacementAlpiniste(alpinisteGoPlayer, alpinisteGoPlayer.getCurrentCase()); 
            this.c.addJoueur(navGoJoueur);
            navGoJoueur.notifyObservers();
            if (current.getCountDeplacementNavigatrice() == 0) {
              current.decrActions();
              current.notifyObservers();
            } 
            this.c.getDesert().notifyObservers();
          } 
          return;
        }  
      if (accessibleDeplacement(current, voisins)) {
        current.decrActions();
        Case currentPlayerCase = current.getCurrentCase();
        currentPlayerCase.removeJoueur(current);
        this.c.addJoueur(current);
        deplacementAlpiniste(current, currentPlayerCase);
        current.notifyObservers();
        this.c.getDesert().notifyObservers();
      } 
      return;
    } 
    if (SwingUtilities.isRightMouseButton(mouseEvent))
      if (accessibleDesensablement(current, voisins)) {
        current.decrActions();
        this.c.decrTonneSable();
        if (current.getClassJoueur() == Joueur.ClassJoueur.ARCHEOLOGUE)
          this.c.decrTonneSable(); 
        this.c.getDesert().notifyObservers();
        return;
      }  
  }
  
  public void mousePressed(MouseEvent mouseEvent) {}
  
  public void mouseReleased(MouseEvent mouseEvent) {}
  
  public void mouseEntered(MouseEvent mouseEvent) {}
  
  public void mouseExited(MouseEvent mouseEvent) {}
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.c.getTypeCase() == Case.TypeCase.OEIL) {
      this.labelCase.setText("");
      setBackground(new Color(255, 255, 255));
      return;
    } 
    this.labelCase.setText(this.c.toString());
    setBackground(new Color(255, 153, 51));
    switch (this.c.getTypeCase()) {
      case MIRAGE:
      case POINT_EAU:
        g.setColor(new Color(14, 84, 222, 255));
        g.fillOval(25, 30, 50, 50);
        g.setColor(new Color(0, 0, 0));
        break;
    } 
    int x = 10;
    int y = 75;
    int i = 0;
    for (Desert.PieceMachine p : this.c.getPiece()) {
      p.drawPiece(g, x, y, i);
      i++;
    } 
    Joueur current = this.c.getDesert().getJoueurCourant();
    if (current.getClassJoueur() == Joueur.ClassJoueur.NAVIGATRICE && 
      current.getNavigatriceGoJoueur() != null)
      current = current.getNavigatriceGoJoueur(); 
    boolean isExplorateur = (current.getClassJoueur() == Joueur.ClassJoueur.EXPLORATEUR);
    ArrayList<Coordonnee> voisins = this.c.getVoisins(current, isExplorateur);
    boolean accessibleDeplacement = accessibleDeplacement(current, voisins);
    boolean accessibleDesensablement = accessibleDesensablement(current, voisins);
    if (Case.isClickable())
      if (Desert.getActionBlaster() && Desert.getSrcAction() != null) {
        Joueur j = Desert.getSrcAction();
        ArrayList<Coordonnee> Blastervoisins = j.getCurrentCase().getVoisins(j, (j.getClassJoueur() == Joueur.ClassJoueur.EXPLORATEUR));
        if (accessibleDesensablement(j, Blastervoisins))
          setBackground(new Color(51, 255, 255)); 
      } else if (Desert.getActionTerrascope() && !this.c.getExploreCase() && this.c.getTypeCase() != Case.TypeCase.OEIL) {
        setBackground(new Color(108, 243, 185, 255));
      } else if (Desert.getActionJetpack() && Desert.getSrcAction() != null && this.c.getTonneSable() < 2) {
        setBackground(new Color(243, 9, 44, 66));
      } else if (accessibleDeplacement) {
        if (accessibleDesensablement) {
          setBackground(new Color(255, 0, 127));
        } else {
          setBackground(new Color(0, 204, 0));
        } 
      } else if (accessibleDesensablement) {
        setBackground(new Color(51, 255, 255));
      }  
    if (this.c.hasEngrenage() && this.c.getExploreCase()) {
      g.setColor(new Color(129, 118, 118));
      g.fillRoundRect(80, 5, 15, 15, 2, 2);
      g.setColor(new Color(0, 0, 0));
    } 
    drawJoueur(g, this.c.getJoueurs());
  }
  
  public void drawJoueur(Graphics g, ArrayList<Joueur> joueurs) {
    int i = 2;
    for (Joueur j : joueurs) {
      g.setColor(j.getClassJoueur().getClassColor());
      g.fillRect(i * 11, 60, 10, 10);
      g.setColor(new Color(0, 0, 0));
      i++;
    } 
  }
}
