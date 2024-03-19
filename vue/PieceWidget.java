package vue;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import modele.Desert;

public class PieceWidget extends JPanel {
  private Desert.PieceMachine piece;
  
  public PieceWidget(Desert.PieceMachine p) {
    this.piece = p;
    if (p == Desert.PieceMachine.ENERGIE) {
      setPreferredSize(new Dimension(22, 50));
    } else {
      setPreferredSize(new Dimension(22, 25));
    } 
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.piece == Desert.PieceMachine.ENERGIE) {
      this.piece.drawPiece(g, 0, 25, 0);
    } else {
      this.piece.drawPiece(g, 0, 0, 0);
    } 
  }
}
