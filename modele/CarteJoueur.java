package modele;

import vue.MenuCreationJoueur;

public class CarteJoueur extends Carte {
  private Joueur.ClassJoueur classCarte;
  
  private MenuCreationJoueur menuCreateJ;
  
  public CarteJoueur(Desert d, MenuCreationJoueur mcj, Joueur.ClassJoueur cj) {
    super(d);
    this.menuCreateJ = mcj;
    this.classCarte = cj;
  }
  
  public Joueur.ClassJoueur getClassCarte() {
    return this.classCarte;
  }
  
  public void effetCarte() {
    this.menuCreateJ.setTmpClassJoueur(this.classCarte);
  }
  
  public String toString() {
    return this.classCarte.toString();
  }
}
