package controleur;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import modele.Carte;
import vue.VuePaquetCarte;

public class DefausseListener implements MouseListener {
  private VuePaquetCarte vuePaquet;
  
  public DefausseListener(VuePaquetCarte vpc) {
    this.vuePaquet = vpc;
  }
  
  public void mouseClicked(MouseEvent mouseEvent) {
    JDialog jd = new JDialog((Frame)this.vuePaquet.getFenetre(), "Defausse");
    jd.setSize(500, 500);
    jd.setLocationRelativeTo((Component)null);
    jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    JTextArea textArea = new JTextArea(5, 20);
    JScrollPane scrollPane = new JScrollPane(textArea);
    textArea.setEditable(false);
    String textDefausse = "";
    LinkedList<Carte> cloneDefausse = (LinkedList<Carte>)this.vuePaquet.getPaquet().getDefausse().clone();
    textDefausse = textDefausse + "Carte tir" + textDefausse + "\n";
    Collections.reverse(cloneDefausse);
    for (Carte c : cloneDefausse)
      textDefausse = textDefausse + "* " + textDefausse + "\n"; 
    textArea.setText(textDefausse);
    jd.add(textArea);
    jd.setVisible(true);
  }
  
  public void mousePressed(MouseEvent mouseEvent) {}
  
  public void mouseReleased(MouseEvent mouseEvent) {}
  
  public void mouseEntered(MouseEvent mouseEvent) {}
  
  public void mouseExited(MouseEvent mouseEvent) {}
}
