package modele;

public class CarteTempete extends Carte {
  private TypeTempete type;
  
  private DirectionVent directionVent;
  
  private int niveauVent;
  
  public enum DirectionVent {
    NORD("Nord"),
    SUD("Sud"),
    EST("Est"),
    OUEST("Ouest");
    
    private String name;
    
    DirectionVent(String name) {
      this.name = name;
    }
  }
  
  public enum TypeTempete {
    DECHAINE("La tempete <br/> se dechaine"),
    VAGUE_CHALEUR("Vague<br/>de chaleur"),
    VENT_SOUFFLE("Vent souffle");
    
    private String nomCarteTempete;
    
    TypeTempete(String nom) {
      this.nomCarteTempete = nom;
    }
    
    public String toString() {
      return this.nomCarteTempete;
    }
  }
  
  public CarteTempete(Desert d, TypeTempete tp) {
    super(d);
    this.type = tp;
    this.directionVent = null;
    this.niveauVent = -1;
  }
  
  public CarteTempete(Desert d, DirectionVent dv, int nv) {
    super(d);
    this.type = TypeTempete.VENT_SOUFFLE;
    this.niveauVent = nv;
    this.directionVent = dv;
  }
  
  public TypeTempete getType() {
    return this.type;
  }
  
  public DirectionVent getDirectionVent() {
    return this.directionVent;
  }
  
  public int getNiveauVent() {
    return this.niveauVent;
  }
  
  public void effetCarte() {
    int i;
    switch (this.type) {
      case EST:
        getD().incrNewNiveauTemp();
        break;
      case SUD:
        for (i = 0; i < this.niveauVent; i++)
          getD().swapOeilCase(this.directionVent); 
        break;
      case NORD:
        for (Joueur j : getD().getJoueurs()) {
          Case caseJoueur = j.getCurrentCase();
          boolean contientBouclierSolaire = false;
          if (Desert.getSrcActivationBouclierSolaire() != null)
            for (Joueur tmpJ : caseJoueur.getJoueurs()) {
              if (tmpJ == Desert.getSrcActivationBouclierSolaire()) {
                contientBouclierSolaire = true;
                break;
              } 
            }  
          if ((caseJoueur.getTypeCase() != Case.TypeCase.TUNNELS || !caseJoueur.getExploreCase()) && !contientBouclierSolaire)
            j.decrNiveauEau(); 
        } 
        break;
    } 
  }
  
  public String toString() {
    String res = "<html>";
    switch (this.type) {
      case EST:
      case NORD:
        res = res + res;
        break;
      case SUD:
        res = res + res;
        res = res + "<br/>" + res;
        res = res + "<br/>" + res + " case(s)";
        break;
    } 
    return res + "</html>";
  }
  
  public String toStringAlt() {
    String res = "";
    switch (this.type) {
      case NORD:
        return "Vague de chaleur";
      case EST:
        return "La tempete se dzchainte";
      case SUD:
        res = res + "Le vent souffle en direction ";
        switch (this.directionVent) {
          case EST:
            res = res + "de l'est ";
            break;
          case SUD:
            res = res + "du sud ";
            break;
          case NORD:
            res = res + "du nord ";
            break;
          case OUEST:
            res = res + "de l'ouest ";
            break;
        } 
        break;
    } 
    return res + " sur " + res + " case(s)";
  }
}
