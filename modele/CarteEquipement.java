package modele;

public class CarteEquipement extends Carte {
  private TypeEquipement typeEquipement;
  
  public enum TypeEquipement {
    AccelerateurTemps("<html>Acctemps</html>"),
    ReserveEau("Rd'eau"),
    BouclierSolaire("Bouclier solaire"),
    Terrascope("Terrascope"),
    Blaster("Blaster"),
    JetPack("Jetpack");
    
    private String name;
    
    TypeEquipement(String n) {
      this.name = n;
    }
    
    public String toString() {
      return this.name;
    }
  }
  
  public CarteEquipement(Desert d, TypeEquipement te) {
    super(d);
    this.typeEquipement = te;
  }
  
  public TypeEquipement getType() {
    return this.typeEquipement;
  }
  
  public void effetCarte() {}
  
  public String toString() {
    return this.typeEquipement.toString();
  }
}
