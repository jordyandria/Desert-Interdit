package modele;

import Observer.Observable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import vue.MenuCreationJoueur;

public class PaquetCarte extends Observable {
  private int countCartePioche;
  
  private Desert desert;
  
  private ArrayDeque<Carte> paquet;
  
  private LinkedList<Carte> defausse;
  
  private TypePaquet type;
  
  private MenuCreationJoueur menuCreationJoueur;
  
  public enum TypePaquet {
    Tempete, Equipement, Aventurier;
  }
  
  private ArrayDeque<Carte> initPaquetTempete(Desert d) {
    ArrayList<Carte> tmpPaquet = new ArrayList<>();
    int i;
    for (i = 0; i < 3; i++)
      tmpPaquet.add(new CarteTempete(d, CarteTempete.TypeTempete.DECHAINE)); 
    for (i = 0; i < 4; i++)
      tmpPaquet.add(new CarteTempete(d, CarteTempete.TypeTempete.VAGUE_CHALEUR)); 
    for (CarteTempete.DirectionVent dv : CarteTempete.DirectionVent.values()) {
      for (int j = 1; j < 4; j++) {
        tmpPaquet.add(new CarteTempete(d, dv, j));
        tmpPaquet.add(new CarteTempete(d, dv, j));
      } 
    } 
    Collections.shuffle(tmpPaquet);
    ArrayDeque<Carte> paquetTempete = new ArrayDeque<>(tmpPaquet);
    return paquetTempete;
  }
  
  private ArrayDeque<Carte> initPaquetAventurier(Desert d, MenuCreationJoueur mcj) {
    ArrayList<Carte> tmpPaquet = new ArrayList<>();
    for (Joueur.ClassJoueur cj : Joueur.ClassJoueur.values())
      tmpPaquet.add(new CarteJoueur(d, mcj, cj)); 
    Collections.shuffle(tmpPaquet);
    ArrayDeque<Carte> paquetAventurier = new ArrayDeque<>(tmpPaquet);
    return paquetAventurier;
  }
  
  private ArrayDeque<Carte> initPaquetEquipement(Desert d) {
    ArrayList<Carte> tmpPaquet = new ArrayList<>();
    tmpPaquet.add(new CarteEquipement(d, CarteEquipement.TypeEquipement.AccelerateurTemps));
    tmpPaquet.add(new CarteEquipement(d, CarteEquipement.TypeEquipement.ReserveEau));
    int i;
    for (i = 0; i < 2; i++) {
      tmpPaquet.add(new CarteEquipement(d, CarteEquipement.TypeEquipement.BouclierSolaire));
      tmpPaquet.add(new CarteEquipement(d, CarteEquipement.TypeEquipement.Terrascope));
    } 
    for (i = 0; i < 3; i++) {
      tmpPaquet.add(new CarteEquipement(d, CarteEquipement.TypeEquipement.Blaster));
      tmpPaquet.add(new CarteEquipement(d, CarteEquipement.TypeEquipement.JetPack));
    } 
    Collections.shuffle(tmpPaquet);
    return new ArrayDeque<>(tmpPaquet);
  }
  
  public PaquetCarte(Desert d, MenuCreationJoueur mcj, TypePaquet tp) {
    this.desert = d;
    this.countCartePioche = 0;
    this.menuCreationJoueur = mcj;
    this.defausse = new LinkedList<>();
    this.type = tp;
    switch (tp) {
      case Tempete:
        this.paquet = initPaquetTempete(d);
        break;
      case Aventurier:
        this.paquet = initPaquetAventurier(d, mcj);
        break;
      case Equipement:
        this.paquet = initPaquetEquipement(d);
        break;
    } 
  }
  
  public MenuCreationJoueur getMenuCreationJoueur() {
    return this.menuCreationJoueur;
  }
  
  public void incrCountCartePioche() {
    this.countCartePioche++;
  }
  
  public void resetCountCartePioche() {
    this.countCartePioche = 0;
  }
  
  public int getCountCartePioche() {
    return this.countCartePioche;
  }
  
  public Desert getDesert() {
    return this.desert;
  }
  
  public TypePaquet getType() {
    return this.type;
  }
  
  public ArrayDeque<Carte> getPaquet() {
    return this.paquet;
  }
  
  public LinkedList<Carte> getDefausse() {
    return this.defausse;
  }
  
  public void removeCarte(Carte c) {
    this.paquet.remove(c);
  }
  
  public void pushBack(Carte c) {
    this.paquet.addLast(c);
  }
  
  public Carte pioche() {
    Carte t = this.paquet.poll();
    if (t == null) {
      remelange();
      t = this.paquet.poll();
    } 
    this.defausse.add(t);
    return t;
  }
  
  public Carte getTeteDefausse() {
    return this.defausse.getLast();
  }
  
  public void addToDefausse(Carte c) {
    this.defausse.add(c);
  }
  
  public void remelange() {
    Collections.shuffle(this.defausse);
    this.paquet = new ArrayDeque<>(this.defausse);
    this.defausse = new LinkedList<>();
  }
  
  public String toString() {
    return "PaquetCarte{paquet=" + this.paquet + ", defausse=" + this.defausse + "}";
  }
}
