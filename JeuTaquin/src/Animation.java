import java.awt.*;

public class Animation {

    Jeu jeu;

    public Animation(Jeu jeu){this.jeu = jeu;};

    public void run(String s) throws InterruptedException {
        for(int i=0; i<s.length();i++) {
            System.out.print((char) Event.ESCAPE + "[2J");
            System.out.print((char) Event.ESCAPE + "8");
            System.out.println();
            System.out.println(jeu);
            System.out.println("t = " + i);
            jeu.succ(s.charAt(i));
            Thread.sleep(500);
        }

        System.out.print((char) Event.ESCAPE + "[2J");
        System.out.print((char) Event.ESCAPE + "8");
        System.out.println();
        System.out.println(jeu);
        System.out.println("t = fin");
    }


}
