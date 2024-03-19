package modele;

import Observer.Observable;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;

public class Joueur extends Observable {
  private int niveauEau;
  
  private String nom;
  
  private int actions;
  
  private ClassJoueur classJ;
  
  private Case currentCase;
  
  private ArrayList<Desert.PieceMachine> pieces;
  
  private Joueur alpinisteGoJoueur;
  
  private Joueur navigatriceGoJoueur;
  
  private int countDeplacementNavigatrice;
  
  private ArrayList<CarteEquipement> equipements;
  
  public enum ClassJoueur {
    ARCHEOLOGUE("Archeologue", new Color(201, 47, 47), 4),
    ALPINISTE("Alpiniste", new Color(10, 10, 10), 4),
    EXPLORATEUR("Explorateur", new Color(60, 171, 56), 5),
    METEOROLOGUE("Meterologue", new Color(176, 182, 176), 5),
    NAVIGATRICE("Navigatrice", new Color(223, 218, 71), 5),
    PORTEUSE_EAU("Porteuse d'eau", new Color(66, 195, 220), 6);
    
    private String nom;
    
    private Color colorClass;
    
    private int maxEau;
    
    ClassJoueur(String n, Color classColor, int maxEau) {
      this.nom = n;
      this.colorClass = classColor;
      this.maxEau = maxEau;
    }
    
    public Color getClassColor() {
      return this.colorClass;
    }
    
    public int getMaxEau() {
      return this.maxEau;
    }
  }
  
  public Joueur(String nom, ClassJoueur cj) {
    this.nom = nom;
    this.actions = 4;
    this.classJ = cj;
    this.niveauEau = 4;
    if (cj == ClassJoueur.PORTEUSE_EAU)
      this.niveauEau++; 
    this.currentCase = null;
    this.pieces = new ArrayList<>();
    this.alpinisteGoJoueur = null;
    this.navigatriceGoJoueur = null;
    this.countDeplacementNavigatrice = 0;
    this.equipements = new ArrayList<>();
  }
  
  public void addEquipement(CarteEquipement c) {
    this.equipements.add(c);
  }
  
  public void removeEquipement(CarteEquipement c) {
    this.equipements.remove(c);
  }
  
  public ArrayList<CarteEquipement> getEquipements() {
    return this.equipements;
  }
  
  public void setAlpinisteGoJoueur(Joueur j) {
    if (this.classJ == ClassJoueur.ALPINISTE) {
      this.alpinisteGoJoueur = j;
    } else {
      throw new IllegalArgumentException("Le joueur courant n'est pas l'alpiniste, il est " + this);
    } 
  }
  
  public void setNavigatriceGoJoueur(Joueur j) {
    if (this.classJ == ClassJoueur.NAVIGATRICE) {
      this.navigatriceGoJoueur = j;
    } else {
      throw new IllegalArgumentException("Le joueur courant n'est pas la navigatrice, il est " + this);
    } 
  }
  
  public Joueur getAlpinisteGoJoueur() {
    if (this.classJ == ClassJoueur.ALPINISTE)
      return this.alpinisteGoJoueur; 
    throw new IllegalArgumentException("Le joueur courant n'est pas l'alpiniste, il est " + this);
  }
  
  public Joueur getNavigatriceGoJoueur() {
    if (this.classJ == ClassJoueur.NAVIGATRICE)
      return this.navigatriceGoJoueur; 
    throw new IllegalArgumentException("Le joueur courant n'est pas l'alpiniste, il est " + this);
  }
  
  public void resetCountDeplacementNavigatrice() {
    if (this.countDeplacementNavigatrice == 0)
      this.countDeplacementNavigatrice = 3; 
  }
  
  public void decrCountDeplacementNavigatrice() {
    if (this.countDeplacementNavigatrice > 0)
      this.countDeplacementNavigatrice--; 
  }
  
  public int getCountDeplacementNavigatrice() {
    return this.countDeplacementNavigatrice;
  }
  
  public void decrActions() {
    if (this.actions > 0)
      this.actions--; 
  }
  
  public void incrActions() {
    this.actions++;
  }
  
  public void resetActions() {
    this.actions = 4;
  }
  
  public int getActions() {
    return this.actions;
  }
  
  public String getNom() {
    return this.nom;
  }
  
  public ClassJoueur getClassJoueur() {
    return this.classJ;
  }
  
  public int getNiveauEau() {
    return this.niveauEau;
  }
  
  public void decrNiveauEau() {
    if (this.niveauEau > 0) {
      this.niveauEau--;
      notifyObservers();
    } 
  }
  
  public void incrNiveauEau() {
    if (this.niveauEau < this.classJ.getMaxEau()) {
      this.niveauEau++;
      notifyObservers();
    } 
  }
  
  public void setCase(Case c) {
    this.currentCase = c;
  }
  
  public Case getCurrentCase() {
    return this.currentCase;
  }
  
  public void prendrePiece() {
    this.pieces.add(this.currentCase.prendrePiece());
  }
  
  public ArrayList<Desert.PieceMachine> getPieces() {
    return this.pieces;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (o == null || getClass() != o.getClass())
      return false; 
    Joueur joueur = (Joueur)o;
    return (this.niveauEau == joueur.niveauEau && this.actions == joueur.actions && Objects.equals(this.nom, joueur.nom) && this.classJ == joueur.classJ && Objects.equals(this.pieces, joueur.pieces));
  }
  
  public int hashCode() {
    return Objects.hash(new Object[] { Integer.valueOf(this.niveauEau), this.nom, Integer.valueOf(this.actions), this.classJ, this.pieces });
  }
  
  public String toString() {
    return "Joueur{niveauEau=" + this.niveauEau + ", nom='" + this.nom + "', actions=" + this.actions + ", classJ=" + this.classJ + ", pieces=" + this.pieces + "}";
  }
}
