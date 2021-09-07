import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.*;



public class Taquin {

    public static int[][] initGrille(){
        int[][] laGrille = new int[4][4];
        ArrayList<Integer> vals = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0));
        Random rand = new Random();

        for(int i = 0;i<laGrille.length;i++){
            for(int j = 0;j<laGrille[i].length;j++){
                int index = rand.nextInt(vals.size());
                laGrille[i][j] = vals.get(index);
                vals.remove(index);
            }
        }

        return laGrille;
    }

    public static int[] getInitPos(int[][] laGrille){
        int[] pos = new int[2];

        for(int i = 0;i<laGrille.length;i++){
            for(int j = 0;j<laGrille[i].length;j++){
                if(laGrille[i][j] == 0){
                    pos[0] = i; pos[1] = j;
                }
            }
        }

        return pos;
    }

    public static void main(String[] args)
    {


        //Fonctionnel

        int[][] grille = initGrille();
        int[] initPos = getInitPos(grille);
        int[] finPos = {3,3};




        //Graphique

        JFrame cadre = new JFrame("Jeu du Taquin");
        JPanel panneau = new JPanel (new GridLayout (4,4));
        Border blackline = BorderFactory.createLineBorder(Color.black,1);
        for(int i = 0; i< grille.length;i++){

            for(int j = 0 ; j < grille[i].length ; j++)
            {
                JPanel ptest = new JPanel();
                ptest.setBorder(blackline);
                JLabel chiffreGrille = new JLabel(""+grille[i][j]);
                if(grille[i][j] != 0)
                {
                    ptest.add(chiffreGrille);
                        ptest.setBackground(new Color(176, 255, 237));
                }
                else
                {
                    ptest.setBackground(Color.LIGHT_GRAY);
                }

                panneau.add(ptest);
            }


        }
        panneau.setBorder(blackline);
        cadre.add(panneau);
        panneau.setLayout(new GridLayout(4, // nb de cases dans une ligne
                4, // nb de cases dans une colonne
                0, // épaisseur des traits horizontaux
                0)); // épaisseur des traits verticaux
        panneau.setBackground(Color.BLACK); // Couleur des traits

        panneau.setPreferredSize(new Dimension(400, 250));

        panneau.setBackground(Color.WHITE);


        cadre.setContentPane(panneau);
        cadre.setLocation(400, 300);
        cadre.pack();
        cadre.setVisible(true);
        cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);







    }
}
