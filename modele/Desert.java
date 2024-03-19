package modele;

import Observer.Observable;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;

public class Desert extends Observable {
  private Case[][] grilleDesert;
  
  private ArrayList<Case> caseDesert;
  
  private Coordonnee coord_oeil;
  
  private int niveauTemp;
  
  private int newNiveauTemp;
  
  private ArrayList<Joueur> joueurs;
  
  private int joueurCourant;
  
  private Hashtable<String, Coordonnee> mapIndice;
  
  private static Joueur srcAction;
  
  private static boolean actionTerrascope;
  
  private static boolean actionBlaster;
  
  private static boolean actionJetpack;
  
  private static Joueur joueurEmmenerJetpack;
  
  private static boolean activationBouclierCeTour;
  
  private static Joueur srcActivationBouclierSolaire;
  
  public enum PieceMachine {
    MOTEUR("Moteur"),
    GOUVERNAIL("Gouvernail"),
    ENERGIE("Energie"),
    HELICE("Helice");
    
    private String nom;
    
    PieceMachine(String nom) {
      this.nom = nom;
    }
    
    public String getNom() {
      return this.nom;
    }
    
    public void drawPiece(Graphics g, int x, int y, int i) {
      int xCentre, yCentre, xCoord[], yCoord[], sizePiece = 20;
      int yPosition = y;
      int xPosition = (sizePiece + 5) * i + x;
      switch (this) {
        case LIGNE_HELICE:
          g.setColor(new Color(160, 160, 160));
          g.fillRect(xPosition, yPosition, sizePiece, sizePiece);
          g.setColor(new Color(0, 0, 0));
          break;
        case COLONNE_HELICE:
          g.setColor(new Color(153, 76, 0));
          g.drawOval(xPosition, yPosition, sizePiece, sizePiece);
          g.setColor(new Color(0, 0, 0));
          break;
        case LIGNE_ENERGIE:
          g.setColor(new Color(204, 102, 0));
          xCentre = xPosition + 5;
          yCentre = yPosition + 10;
          g.drawLine(xCentre, yCentre, xCentre - sizePiece / 4, yCentre);
          g.drawLine(xCentre, yCentre, xCentre + sizePiece / 4, yCentre);
          g.drawLine(xCentre, yCentre, xCentre, yCentre - sizePiece / 4);
          g.drawLine(xCentre, yCentre, xCentre, yCentre + sizePiece / 4);
          g.setColor(new Color(0, 0, 0));
          break;
        case COLONNE_ENERGIE:
          g.setColor(new Color(255, 255, 51));
          xCoord = new int[] { (xPosition + xPosition + sizePiece) / 2, xPosition + sizePiece, xPosition + sizePiece, xPosition, xPosition };
          yCoord = new int[] { yPosition - sizePiece - 5, yPosition, yPosition + sizePiece - 10, yPosition + sizePiece - 10, yPosition };
          g.fillPolygon(xCoord, yCoord, 5);
          g.setColor(new Color(0, 0, 0));
          break;
      } 
    }
    
    public String toString() {
      return "PieceMachine{nom='" + this.nom + "'}";
    }
  }
  
  public Desert() {
    this.grilleDesert = new Case[5][5];
    this.joueurs = new ArrayList<>();
    this.joueurCourant = 0;
    this.mapIndice = new Hashtable<>();
    this.niveauTemp = 2;
    this.caseDesert = new ArrayList<>();
    this.caseDesert.add(new Case(this, 0, Case.TypeCase.LIGNE_HELICE));
    this.caseDesert.add(new Case(this, 0, Case.TypeCase.COLONNE_HELICE));
    this.caseDesert.add(new Case(this, 0, Case.TypeCase.LIGNE_ENERGIE));
    this.caseDesert.add(new Case(this, 0, Case.TypeCase.COLONNE_ENERGIE));
    this.caseDesert.add(new Case(this, 0, Case.TypeCase.LIGNE_GOUVERNAIL));
    this.caseDesert.add(new Case(this, 0, Case.TypeCase.COLONNE_GOUVERNAIL));
    this.caseDesert.add(new Case(this, 0, Case.TypeCase.LIGNE_MOTEUR));
    this.caseDesert.add(new Case(this, 0, Case.TypeCase.COLONNE_MOTEUR));
    int i;
    for (i = 0; i < 2; i++)
      this.caseDesert.add(new Case(this, 0, Case.TypeCase.POINT_EAU)); 
    this.caseDesert.add(new Case(this, 0, Case.TypeCase.MIRAGE));
    for (i = 0; i < 3; i++)
      this.caseDesert.add(new Case(this, 0, Case.TypeCase.TUNNELS)); 
    this.caseDesert.add(new Case(this, 0, Case.TypeCase.PISTE_DECOLLAGE));
    for (i = 0; i < 9; i++)
      this.caseDesert.add(new Case(this, 0, Case.TypeCase.CITE)); 
    shuffleArrayList();
    shuffleArrayList();
    Case tmpSwap = this.caseDesert.get(12);
    this.caseDesert.set(12, new Case(this, 0, Case.TypeCase.OEIL));
    this.coord_oeil = new Coordonnee(2, 2);
    this.caseDesert.add(tmpSwap);
    HashSet<Coordonnee> coordonneeEngrenage = randomListCoord(12, 0, 5);
    for (int j = 0; j < 5; j++) {
      for (int k = 0; k < 5; k++) {
        Case c = this.caseDesert.get(j * 5 + k);
        Coordonnee tmpCoord = new Coordonnee(j, k);
        if (coordonneeEngrenage.contains(tmpCoord))
          c.contientEngrenage(); 
        this.grilleDesert[j][k] = c;
        c.setCoordCase(j, k);
      } 
    } 
    this.grilleDesert[0][2].incrTonneSable();
    this.grilleDesert[1][1].incrTonneSable();
    this.grilleDesert[1][3].incrTonneSable();
    this.grilleDesert[2][0].incrTonneSable();
    this.grilleDesert[2][4].incrTonneSable();
    this.grilleDesert[3][1].incrTonneSable();
    this.grilleDesert[3][3].incrTonneSable();
    this.grilleDesert[4][2].incrTonneSable();
  }
  
  public static void setActionTerrascope() {
    if (actionTerrascope) {
      actionTerrascope = false;
    } else {
      actionTerrascope = true;
    } 
  }
  
  public static boolean getActionTerrascope() {
    return actionTerrascope;
  }
  
  public static void setActionBlaster() {
    if (actionBlaster) {
      actionBlaster = false;
    } else {
      actionBlaster = true;
    } 
  }
  
  public static boolean getActionBlaster() {
    return actionBlaster;
  }
  
  public static void setActionJetpack() {
    if (actionJetpack) {
      actionJetpack = false;
    } else {
      actionJetpack = true;
    } 
  }
  
  public static boolean getActionJetpack() {
    return actionJetpack;
  }
  
  public static Joueur getSrcAction() {
    return srcAction;
  }
  
  public static void setSrcAction(Joueur j) {
    srcAction = j;
  }
  
  public static void setJoueurEmmenerJetpack(Joueur j) {
    joueurEmmenerJetpack = j;
  }
  
  public static Joueur getJoueurEmmenerJetpack() {
    return joueurEmmenerJetpack;
  }
  
  public static Joueur getSrcActivationBouclierSolaire() {
    return srcActivationBouclierSolaire;
  }
  
  public static void setActivationBouclierCeTour() {
    if (activationBouclierCeTour) {
      activationBouclierCeTour = false;
    } else {
      activationBouclierCeTour = true;
    } 
  }
  
  public static boolean getActivationBouclierCeTour() {
    return activationBouclierCeTour;
  }
  
  public static void setSrcActivationBouclierSolaire(Joueur j) {
    srcActivationBouclierSolaire = j;
  }
  
  private void swapArrayList(int i, int j) {
    Case tmp = this.caseDesert.get(i);
    this.caseDesert.set(i, this.caseDesert.get(j));
    this.caseDesert.set(j, tmp);
  }
  
  private void shuffleArrayList() {
    for (int i = this.caseDesert.size() - 1; i > 1; i--) {
      int randomNum = ThreadLocalRandom.current().nextInt(0, i);
      swapArrayList(i, randomNum);
    } 
  }
  
  private HashSet<Coordonnee> randomListCoord(int nbCoord, int min, int max) {
    HashSet<Coordonnee> liste_nombre = new HashSet<>();
    while (liste_nombre.size() < nbCoord) {
      Coordonnee c = new Coordonnee(ThreadLocalRandom.current().nextInt(min, max), ThreadLocalRandom.current().nextInt(min, max));
      if (c.getX() == 2 && c.getY() == 2)
        continue; 
      liste_nombre.add(c);
    } 
    return liste_nombre;
  }
  
  public int countSable() {
    int nbSable = 0;
    for (Case[] ligneCase : this.grilleDesert) {
      for (Case c : ligneCase)
        nbSable += c.getTonneSable(); 
    } 
    return nbSable;
  }
  
  public void incrNewNiveauTemp() {
    this.newNiveauTemp++;
  }
  
  public void incrNiveauTempete() {
    if (this.niveauTemp < 7) {
      this.niveauTemp += this.newNiveauTemp;
      this.newNiveauTemp = 0;
      notifyObservers();
    } 
  }
  
  public int getNiveauTemp() {
    return this.niveauTemp;
  }
  
  public Coordonnee getCoordOeil() {
    return this.coord_oeil;
  }
  
  public Case getCase(int i, int j) {
    return this.grilleDesert[i][j];
  }
  
  public Case getCase(Coordonnee c) {
    return this.grilleDesert[c.getX()][c.getY()];
  }
  
  public void swapOeilCase(CarteTempete.DirectionVent dv) {
    int nextX = 0, nextY = 0;
    switch (dv) {
      case LIGNE_HELICE:
        nextX = this.coord_oeil.getX() - 1;
        nextY = this.coord_oeil.getY();
        break;
      case COLONNE_HELICE:
        nextX = this.coord_oeil.getX() + 1;
        nextY = this.coord_oeil.getY();
        break;
      case LIGNE_ENERGIE:
        nextX = this.coord_oeil.getX();
        nextY = this.coord_oeil.getY() + 1;
        break;
      case COLONNE_ENERGIE:
        nextX = this.coord_oeil.getX();
        nextY = this.coord_oeil.getY() - 1;
        break;
    } 
    if (nextX < 0 || nextX > 4 || nextY < 0 || nextY > 4)
      return; 
    Case oeil = getCase(getCoordOeil());
    Case tmp = this.grilleDesert[nextX][nextY];
    if (oeil.getPiece().size() != 0) {
      for (PieceMachine pm : oeil.getPiece())
        tmp.addPieceToCase(pm); 
      oeil.resetPieceCase();
    } 
    tmp.incrTonneSable();
    oeil.setCoordCase(nextX, nextY);
    this.grilleDesert[nextX][nextY] = oeil;
    tmp.setCoordCase(this.coord_oeil.getX(), this.coord_oeil.getY());
    this.grilleDesert[this.coord_oeil.getX()][this.coord_oeil.getY()] = tmp;
    this.coord_oeil = new Coordonnee(nextX, nextY);
    if (tmp.getExploreCase() && tmp.isCaseIndice()) {
      String key = getIndiceKey(tmp.getTypeCase());
      boolean isLigne = getIndiceLigne(tmp.getTypeCase());
      Coordonnee c = this.mapIndice.remove(key);
      if (c != null)
        if (isLigne) {
          this.mapIndice.put(key, new Coordonnee(tmp.getCoordCase().getX(), c.getY()));
        } else {
          this.mapIndice.put(key, new Coordonnee(c.getX(), tmp.getCoordCase().getY()));
        }  
    } 
    notifyObservers();
  }
  
  public void addJoueur(Joueur j) {
    this.joueurs.add(j);
  }
  
  public void changeCurrentPlayer() {
    getJoueurCourant().resetActions();
    if (this.joueurCourant == this.joueurs.size() - 1) {
      this.joueurCourant = 0;
    } else {
      this.joueurCourant++;
    } 
  }
  
  public Joueur getJoueurCourant() {
    if (this.joueurs.size() > 0)
      return this.joueurs.get(this.joueurCourant); 
    return null;
  }
  
  public ArrayList<Joueur> getJoueurs() {
    return this.joueurs;
  }
  
  public void crash() {
    for (Coordonnee c : randomListCoord(1, 0, 5)) {
      this.grilleDesert[c.getX()][c.getY()].lieuDuCrash();
      for (Joueur j : this.joueurs)
        getCase(c).addJoueur(j); 
    } 
    notifyObservers();
  }
  
  public String getIndiceKey(Case.TypeCase tc) {
    String key;
    switch (tc) {
      case LIGNE_HELICE:
      case COLONNE_HELICE:
        key = "Indice Helice";
        return key;
      case LIGNE_ENERGIE:
      case COLONNE_ENERGIE:
        key = "Indice Energie";
        return key;
      case LIGNE_MOTEUR:
      case COLONNE_MOTEUR:
        key = "Indice Moteur";
        return key;
      case LIGNE_GOUVERNAIL:
      case COLONNE_GOUVERNAIL:
        key = "Indice Gouvernail";
        return key;
    } 
    throw new IllegalArgumentException("Le type de la case n'est pas un indice !!");
  }
  
  public PieceMachine getIndicePiece(Case.TypeCase tc) {
    PieceMachine pm;
    switch (tc) {
      case LIGNE_HELICE:
      case COLONNE_HELICE:
        pm = PieceMachine.HELICE;
        return pm;
      case LIGNE_ENERGIE:
      case COLONNE_ENERGIE:
        pm = PieceMachine.ENERGIE;
        return pm;
      case LIGNE_MOTEUR:
      case COLONNE_MOTEUR:
        pm = PieceMachine.MOTEUR;
        return pm;
      case LIGNE_GOUVERNAIL:
      case COLONNE_GOUVERNAIL:
        pm = PieceMachine.GOUVERNAIL;
        return pm;
    } 
    throw new IllegalArgumentException("Le type de la case n'est pas un indice !!");
  }
  
  public boolean getIndiceLigne(Case.TypeCase tc) {
    boolean isLigne;
    switch (tc) {
      case LIGNE_HELICE:
      case LIGNE_ENERGIE:
      case LIGNE_MOTEUR:
      case LIGNE_GOUVERNAIL:
        isLigne = true;
        return isLigne;
      case COLONNE_HELICE:
      case COLONNE_ENERGIE:
      case COLONNE_MOTEUR:
      case COLONNE_GOUVERNAIL:
        isLigne = false;
        return isLigne;
    } 
    throw new IllegalArgumentException("Le type de la case n'est pas un indice !!");
  }
  
  public void addIndice(Case caseExplore) {
    String key = getIndiceKey(caseExplore.getTypeCase());
    boolean isLigne = getIndiceLigne(caseExplore.getTypeCase());
    PieceMachine pm = getIndicePiece(caseExplore.getTypeCase());
    if (this.mapIndice.containsKey(key)) {
      Coordonnee c = this.mapIndice.remove(key);
      if (isLigne) {
        this.grilleDesert[caseExplore.getCoordCase().getX()][c.getY()].addPieceToCase(pm);
      } else {
        this.grilleDesert[c.getX()][caseExplore.getCoordCase().getY()].addPieceToCase(pm);
      } 
    } else {
      Coordonnee c;
      if (isLigne) {
        c = new Coordonnee(caseExplore.getCoordCase().getX(), -1);
      } else {
        c = new Coordonnee(-1, caseExplore.getCoordCase().getY());
      } 
      this.mapIndice.put(key, c);
    } 
  }
  
  public Case[][] getGrilleDesert() {
    return this.grilleDesert;
  }
  
  public String toString() {
    String s = "";
    s = s + "+-----+\n";
    for (int i = 0; i < 5; i++) {
      s = s + "|";
      for (int j = 0; j < 5; j++)
        s = s + s; 
      s = s + "|\n";
    } 
    s = s + "+-----+\n";
    return s;
  }
}
