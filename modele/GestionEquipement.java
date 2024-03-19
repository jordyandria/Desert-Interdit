package modele;

public class GestionEquipement {
  private static Joueur donEauSrc = null;
  
  private static CarteEquipement carteAEchanger = null;
  
  private static Joueur srcEchangeEquipement = null;
  
  public static void setDonEauSrc(Joueur j) {
    donEauSrc = j;
  }
  
  public static Joueur getDonEauSrc() {
    return donEauSrc;
  }
  
  public static void setCarteAEchanger(CarteEquipement ce) {
    carteAEchanger = ce;
  }
  
  public static CarteEquipement getCarteAEchanger() {
    return carteAEchanger;
  }
  
  public static void setSrcEchangeEquipement(Joueur j) {
    srcEchangeEquipement = j;
  }
  
  public static Joueur getSrcEchangeEquipement() {
    return srcEchangeEquipement;
  }
}
