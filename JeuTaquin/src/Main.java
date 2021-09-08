import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        JeuTaquin jeu = new JeuTaquin(4,4);
        //System.out.println(jeu);

        /*Animation animation = new Animation(jeu);
        animation.run("SENSEO");*/

        GrilleInterface gi = new GrilleInterface(jeu);
    }
}
