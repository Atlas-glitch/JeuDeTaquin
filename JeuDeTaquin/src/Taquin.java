import java.util.*;

public class Taquin {

    public static void afficherGrille(int[][] laGrille){
        String separator = "--------------------";
        for(int i=0;i<laGrille.length;i++){
            System.out.println(separator);
            String line="|";
            for(int j=0;j<laGrille[i].length;j++){
                line+= (" "+laGrille[i][j]+" |");
            }
            System.out.println(line);
        }
        System.out.println(separator);
    }

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

    public static void main(String[] args){
        int[][] grille = initGrille();
        System.out.println("Grille : ");
        afficherGrille(grille);

        int[] initPos = getInitPos(grille);
        int[] finPos = {3,3};

        System.out.println("Init pos : ["+initPos[0]+","+initPos[1]+"]");
        System.out.println("Final pos : ["+finPos[0]+","+finPos[1]+"]");
    }
}
