import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;
import modele.Desert;
import modele.PaquetCarte;
import vue.Fenetre;
import vue.MenuCreationJoueur;
import vue.MenuPrincipale;
import vue.VueJeu;

public class DesertInterdit {
  private static Desert creationPanneauJeu(Fenetre f, JPanel rootPanel) {
    Desert d = new Desert();
    PaquetCarte pt = new PaquetCarte(d, null, PaquetCarte.TypePaquet.Tempete);
    PaquetCarte pe = new PaquetCarte(d, null, PaquetCarte.TypePaquet.Equipement);
    VueJeu vj = new VueJeu(f, d, pt, pe);
    rootPanel.add((Component)vj, "vue_jeu");
    return d;
  }
  
  public static void main(String[] args) {
    Fenetre desertInterdit = new Fenetre("Desert Interdit", new Dimension(600, 480));
    JPanel rootPanel = new JPanel(new CardLayout());
    MenuPrincipale mn = new MenuPrincipale(rootPanel);
    rootPanel.add((Component)mn, "menu_principal");
    Desert d = creationPanneauJeu(desertInterdit, rootPanel);
    MenuCreationJoueur mcj = new MenuCreationJoueur(desertInterdit, rootPanel, d);
    rootPanel.add((Component)mcj, "menu_creation_joueur");
    CardLayout cards = (CardLayout)rootPanel.getLayout();
    cards.show(rootPanel, "menu_principal");
    desertInterdit.ajouteElement(rootPanel);
    desertInterdit.dessineFenetre();
  }
}
