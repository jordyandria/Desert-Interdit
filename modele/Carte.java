package modele;

public abstract class Carte {
  private Desert d;
  
  public Carte(Desert d) {
    this.d = d;
  }
  
  public Desert getD() {
    return this.d;
  }
  
  public abstract void effetCarte();
  
  public String messageCarte() {
    String message = "<html>";
    if (this instanceof CarteEquipement) {
      message = message + "Vous avez piochl''" + message + "'<br/><br/>";
      switch (((CarteEquipement)this).getType()) {
        case ALPINISTE:
          message = message + "Enltout le sable sur votre case ou une case adjacente.";
          break;
        case NAVIGATRICE:
          message = message + "Dep sur toute case accessible du dsert";
          break;
        case PORTEUSE_EAU:
          message = message + "Gain de 2 votre rd'eau pour vous et tout les joueurs sur votre case";
          break;
        case EXPLORATEUR:
          message = message + "Permet de voir sous une case ddistance";
          break;
        case METEOROLOGUE:
          message = message + "Vous protainsi que tous les joueurs<br/>sur la mcase contre<br/>les effets des cartes 'Vague de chaleur'<br/>jusqu'votre prochain tour.";
          break;
        case ARCHEOLOGUE:
          message = message + "Gain de deux actions durant le tour d'activation de l'equip";
          break;
      } 
    } else if (this instanceof CarteJoueur) {
      message = message + "Vous avez piochclasse '";
      switch (((CarteJoueur)this).getClassCarte()) {
        case ALPINISTE:
          message = message + "<b><font color='black'>" + message + "</font></b>'<br/><br/>";
          message = message + "Lpeut aller sur les tuiles bloqutuiles ayant au moins 2 marqueurs Sable).<br/>Elle peut aussi emmener un autre joueur avec elle<br/>chaque fois quse dTous les pions<br/>sur la tuile de lne sont jamais enlispeuvent quitter la tuile de lmy a 2 marqueurs Sable ou plus.";
          break;
        case NAVIGATRICE:
          message = message + "<b><font color='#ad9726'>" + message + "</font></b>'<br/><br/>";
          message = message + "La navigatrice peut dun autre joueur jusqutuiles non bloqupar action, Tunnels inclus.<br/>Elle peut dldiagonalement<br/>et peut dlsur les tuiles bloquainsi, lpeut aussi utiliser son<br/>pouvoir et emmener un autre joueur (dont la<br/>navigatrice) !";
          break;
        case PORTEUSE_EAU:
          message = message + "<b><font color='#26a2ad'>" + message + "</font></b>'<br/><br/>";
          message = message + "La porteuse dpeut prendre 2 portions dtuiles Point ddrpour 1 action.<br/>Elle peut aussi donner de laux joueurs sur les<br/>tuiles adjacentes gratuitement et tout moment.<br/>Sa gourde commence avec 5 portions d(au lieu<br/>de 4).";
          break;
        case EXPLORATEUR:
          message = message + "<b><font color='green'>" + message + "</font></b>'<br/><br/>";
          message = message + "Lpeut se denlever du sable<br/>et utiliser les Blasters diagonalement.";
          break;
        case METEOROLOGUE:
          message = message + "<b><font color='#a9afb0'>" + message + "</font></b>'<br/><br/>";
          message = message + "La mpeut ddes actions pour<br/>tirer, la fin de son tour, moins de cartes Tempcarte par action) que ne le nle niveau actuel<br/>de la Tempde sable. Elle peut aussi daction pour regarder autant de cartes Tempque<br/>son niveau actuel, puis en placer sous la pile. Les autres cartes sont remises<br/>sur le dessus de la pile dans lde son choix";
          break;
        case ARCHEOLOGUE:
          message = message + "<b><font color='red'>" + message + "</font></b>'<br/><br/>";
          message = message + "Lpeut enlever 2 marqueurs<br/>Sable sur la mtuile pour 1 action.";
          break;
      } 
    } 
    return message + "</html>";
  }
}
