package modele;

import java.util.Objects;

public class Coordonnee {
  private int x;
  
  private int y;
  
  public static Coordonnee[] diagonnale = new Coordonnee[] { new Coordonnee(0, 2), new Coordonnee(1, 1), new Coordonnee(1, 3), new Coordonnee(2, 0), new Coordonnee(2, 4), new Coordonnee(3, 1), new Coordonnee(3, 3), new Coordonnee(4, 2) };
  
  public Coordonnee(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (o == null || getClass() != o.getClass())
      return false; 
    Coordonnee that = (Coordonnee)o;
    return (this.x == that.x && this.y == that.y);
  }
  
  public int hashCode() {
    return Objects.hash(new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y) });
  }
  
  public String toString() {
    return "Coordonnee{x=" + this.x + ", y=" + this.y + "}";
  }
}
