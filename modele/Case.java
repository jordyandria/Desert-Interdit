package modele;

import java.util.ArrayList;

public class Case {
  private Coordonnee coordCase;
  
  private int tonneSable;
  
  private boolean contientEngrenage;
  
  private boolean isCrash;
  
  private boolean estExplore;
  
  private static boolean clickable;
  
  private TypeCase typeCase;
  
  private ArrayList<Desert.PieceMachine> Pieces;
  
  private ArrayList<Joueur> joueurs;
  
  private Desert desert;
  
  public enum TypeCase {
    OEIL("Oeil Tempete"),
    POINT_EAU("Point d'eau"),
    MIRAGE("Mirage"),
    LIGNE_HELICE("<html>Indice ligne<br/>Helice</html>"),
    COLONNE_HELICE("<html>Indice colonne<br/>Helice</html>"),
    LIGNE_MOTEUR("<html>Indice ligne<br/>Moteur</html>"),
    COLONNE_MOTEUR("<html>Indice colonne<br/>Moteur</html>"),
    LIGNE_ENERGIE("<html>Indice ligne<br/>Energie</html>"),
    COLONNE_ENERGIE("<html>Indice colonne<br/>Energie</html>"),
    LIGNE_GOUVERNAIL("<html>Indice ligne<br/>Gouvernail</html>"),
    COLONNE_GOUVERNAIL("<html>Indice colonne<br/>Gouvernail</html>"),
    TUNNELS("Tunnel"),
    PISTE_DECOLLAGE("<html>Piste de <br/>decollage </html>"),
    CITE("Cite");
    
    private final String nameCase;
    
    TypeCase(String s) {
      this.nameCase = s;
    }
    
    public boolean equalsName(String otherName) {
      return this.nameCase.equals(otherName);
    }
    
    public String toString() {
      return this.nameCase;
    }
  }
  
  public Case(Desert d, int tonneSable, TypeCase tc) {
    this.tonneSable = tonneSable;
    this.typeCase = tc;
    this.contientEngrenage = false;
    this.estExplore = false;
    this.isCrash = false;
    clickable = true;
    this.Pieces = new ArrayList<>();
    this.joueurs = new ArrayList<>();
    this.desert = d;
  }
  
  public boolean getExploreCase() {
    return this.estExplore;
  }
  
  public void exploreCase() {
    this.estExplore = true;
  }
  
  public void addPieceToCase(Desert.PieceMachine pm) {
    this.Pieces.add(pm);
  }
  
  public void resetPieceCase() {
    this.Pieces = new ArrayList<>();
  }
  
  public Desert.PieceMachine prendrePiece() {
    if (this.Pieces.size() > 0)
      return this.Pieces.remove(this.Pieces.size() - 1); 
    return null;
  }
  
  public ArrayList<Desert.PieceMachine> getPiece() {
    return this.Pieces;
  }
  
  public void setCoordCase(int i, int j) {
    this.coordCase = new Coordonnee(i, j);
  }
  
  public Coordonnee getCoordCase() {
    return this.coordCase;
  }
  
  public void addJoueur(Joueur j) {
    this.joueurs.add(j);
    j.setCase(this);
  }
  
  public Joueur removeJoueur(Joueur j) {
    return this.joueurs.remove(this.joueurs.indexOf(j));
  }
  
  public ArrayList<Joueur> getJoueurs() {
    return this.joueurs;
  }
  
  public void incrTonneSable() {
    this.tonneSable++;
  }
  
  public void contientEngrenage() {
    this.contientEngrenage = true;
  }
  
  public boolean hasEngrenage() {
    return this.contientEngrenage;
  }
  
  public void lieuDuCrash() {
    this.isCrash = true;
  }
  
  public void decrTonneSable() {
    if (this.tonneSable > 0)
      this.tonneSable--; 
  }
  
  public void blasterSable() {
    this.tonneSable = 0;
  }
  
  public int getTonneSable() {
    return this.tonneSable;
  }
  
  public TypeCase getTypeCase() {
    return this.typeCase;
  }
  
  public Desert getDesert() {
    return this.desert;
  }
  
  public ArrayList<Coordonnee> getVoisins(Joueur joueur, boolean diagonal) {
    Case caseCurrentPlayer = joueur.getCurrentCase();
    int i = caseCurrentPlayer.getCoordCase().getX();
    int j = caseCurrentPlayer.getCoordCase().getY();
    ArrayList<Coordonnee> res = new ArrayList<>();
    res.add(new Coordonnee(i + 1, j));
    res.add(new Coordonnee(i - 1, j));
    res.add(new Coordonnee(i, j + 1));
    res.add(new Coordonnee(i, j - 1));
    if (diagonal) {
      res.add(new Coordonnee(i + 1, j + 1));
      res.add(new Coordonnee(i + 1, j - 1));
      res.add(new Coordonnee(i - 1, j + 1));
      res.add(new Coordonnee(i - 1, j - 1));
    } 
    return res;
  }
  
  public boolean isCaseIndice() {
    switch (this.typeCase) {
      case LIGNE_HELICE:
      case COLONNE_HELICE:
      case LIGNE_ENERGIE:
      case COLONNE_ENERGIE:
      case LIGNE_MOTEUR:
      case COLONNE_MOTEUR:
      case LIGNE_GOUVERNAIL:
      case COLONNE_GOUVERNAIL:
        return true;
    } 
    return false;
  }
  
  public void effetCase() {
    switch (this.typeCase) {
      case POINT_EAU:
        for (Joueur j : this.joueurs) {
          j.incrNiveauEau();
          j.incrNiveauEau();
        } 
        break;
      case LIGNE_HELICE:
      case COLONNE_HELICE:
      case LIGNE_ENERGIE:
      case COLONNE_ENERGIE:
      case LIGNE_MOTEUR:
      case COLONNE_MOTEUR:
      case LIGNE_GOUVERNAIL:
      case COLONNE_GOUVERNAIL:
        this.desert.addIndice(this);
        break;
    } 
  }
  
  public static void nonClickable() {
    clickable = false;
  }
  
  public static void becomeClickable() {
    clickable = true;
  }
  
  public static boolean isClickable() {
    return clickable;
  }
  
  public String toString() {
    String res = "<html>Sable: " + getTonneSable();
    if (getTypeCase() == TypeCase.OEIL) {
      res = res + "<br/>" + res;
    } else if (this.estExplore) {
      res = res + "<br/>" + res;
    } 
    res = res + "</html>";
    return res;
  }
}
