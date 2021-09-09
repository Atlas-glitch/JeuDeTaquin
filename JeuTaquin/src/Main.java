import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) throws Exception {
        JeuTaquin jeu = new JeuTaquin(2,2);
        //System.out.println(jeu);

       /* Animation animation = new Animation(jeu);
        animation.run("SENSEO");*/

        new GrilleInterface(jeu);
    }
}
