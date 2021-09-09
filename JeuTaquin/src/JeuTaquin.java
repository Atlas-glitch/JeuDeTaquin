import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class JeuTaquin implements Jeu {
    private int[][] val, solvedGrid;
    private int li, lj, nbl, nbc, indice;

    public JeuTaquin(int nbl, int nbc) {
        this.nbl = nbl;
        this.nbc = nbc;
        val = new int[nbl][nbc];
        solvedGrid = new int[nbl][nbc];
        this.initTabResolu();
        int nbEchanges, nbEchangesZero;
        int nbNbs = nbl * nbc;
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random rand = new Random();

        for (int i = 0; i < nbNbs; i++) {
            numbers.add(i);
        }
        for (int i = 0; i < nbl; i++) {
            System.out.println("-------------");
            String line="| ";
            for (int j = 0; j < nbc; j++) {
                int k = rand.nextInt(numbers.size());
                int nb = numbers.get(k);
                val[i][j] = nb;

                numbers.remove(k);
                if (nb == 0) {
                    lj = j;
                    li = i;
                }

                line+=(nb+" | ");
            }
            System.out.println(line);
        }
        System.out.println("--------------");

        nbEchanges = (nbEchangesFaits()%2);
        nbEchangesZero = (nbEchangesFaitsZero()%2);
        System.out.println(nbEchanges+nbEchangesZero==0 ? "SOLVABLE" : "NOT SOLVABLE");

        if (nbEchanges != nbEchangesZero) {
            int[] position1 = getPosition(1);
            int[] position2 = getPosition(2);
            val[position1[0]][position1[1]] = 2;
            val[position2[0]][position2[1]] = 1;
        }
    }

    @Override
    public String toString() {
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < val.length; i++) {
            for (int j = 0; j < val[0].length; j++) {
                res.append(val[i][j] + "\t");
            }
            res.append("\n");
        }
        return res.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JeuTaquin jeuTaquin = (JeuTaquin) o;
        return Arrays.equals(val, jeuTaquin.val);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(val);
    }

    public void succ(char action) {
        switch (action) {
            case 'O':
                if (lj > 0) {
                    val[li][lj] = val[li][lj - 1];
                    val[li][lj - 1] = 0;
                    lj--;
                }
                break;
            case 'E':
                if (lj < val[0].length - 1) {
                    val[li][lj] = val[li][lj + 1];
                    val[li][lj + 1] = 0;
                    lj++;
                }
                break;
            case 'N':
                if (li > 0) {
                    val[li][lj] = val[li - 1][lj];
                    val[li - 1][lj] = 0;
                    li--;
                }
                break;
            case 'S':
                if (li < val.length - 1) {
                    val[li][lj] = val[li + 1][lj];
                    val[li + 1][lj] = 0;
                    li++;
                }
                break;
        }
    }

    ;

    public boolean enable(char action) {
        return true;
    }

    ;

    public void pred(char action) {
    }

    ;

    public void reset() {
    }

    ;

    public Jeu copy() {
        return null;
    }

    ;

    public int[][] returnValues() {
        return this.val;
    }

    public int getNbl() {
        return this.nbl;
    }

    public int getNbc() {
        return this.nbc;
    }

    public int[] getPosition(int numero) {
        int[] tab = new int[2];

        for(int i=0;i<val.length;i++){
            for(int j=0;j<val[i].length;j++){
                if(numero == val[i][j]) {
                    tab[0] = i;
                    tab[1] = j;
                }
            }
        }

        return tab;
    }

    public int getNumero(int x, int y) {
        return val[x][y];
    }

    public int nbEchangesFaits() {
        int[] tab = new int[2];
        int distance = 0;
        for (int i = 0; i < solvedGrid.length; i++) {
            for (int j = 0; j < solvedGrid[i].length; j++) {
                tab = getPosition(solvedGrid[i][j]);
                distance += Math.abs((tab[0] - i)) + Math.abs((tab[1] - j));
            }
        }
        return distance;
    }


    public int nbEchangesFaitsZero() {
        int[] tab = new int[2];
        int nbEchangesZero = 0;
        for (int i = 0; i < solvedGrid.length; i++) {
            for (int j = 0; j < solvedGrid[i].length; j++) {
                if (solvedGrid[i][j] == 0) {
                    tab = getPosition(solvedGrid[i][j]);
                    nbEchangesZero = nbEchangesZero + Math.abs((tab[0] - i) + (tab[1] - j));
                }
            }
        }
        return nbEchangesZero;
    }

    public void initTabResolu() {
        indice = 1;
        for (int a = 0; a < solvedGrid.length; a++) {
            for (int b = 0; b < solvedGrid[a].length; b++) {
                if (indice < (nbl) * (nbc)) {
                    solvedGrid[a][b] = indice;
                } else {
                    solvedGrid[a][b] = 0;
                }
                indice++;
            }
        }

    }

    public int[][] getSolvedGrid(){
        return this.solvedGrid;
    }
}
