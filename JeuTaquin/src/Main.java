import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) throws Exception {

        GrilleInterface[] listeners = {new GrilleInterface()};
        JeuTaquin jeu = new JeuTaquin(3,3, listeners);



        //System.out.println(jeu);

       /* Animation animation = new Animation(jeu);
        animation.run("SENSEO");*/

    }
}
