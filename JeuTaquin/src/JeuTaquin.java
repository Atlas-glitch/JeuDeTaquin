import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class JeuTaquin implements Jeu {
    private int[][] val, val2;
    private int li,lj,nbl,nbc, indice;

    public JeuTaquin(int nbl, int nbc){
        this.nbl = nbl;
        this.nbc = nbc;

        val = new int[nbl][nbc];
        val2 = new int[nbl][nbc];
        int nbEchanges = nbEchangesFaits();
        int nbNbs = nbl*nbc;
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random rand = new Random();

        do
        {
            indice = 1;
            for(int i=0;i<nbNbs;i++){
                numbers.add(i);
            }

            for(int i=0;i<nbl;i++){
                for(int j=0;j<nbc;j++){
                    int k = rand.nextInt(numbers.size());
                    int nb = numbers.get(k);
                    val[i][j] = nb;
                    if(indice < (nbl)*(nbc))
                    {
                        val2[i][j] = indice ;
                    }
                    else
                    {
                        val2[i][j] = 0 ;
                    }
                    numbers.remove(k);
                    if(nb==0){
                        lj = j; li=i;
                    }
                    indice++;
                }
            }
        }while(nbEchanges == 1);
    }

    @Override
    public String toString() {
        StringBuffer res = new StringBuffer();

        for(int i=0;i<val.length;i++){
            for(int j=0;j<val[0].length;j++){
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

    public void succ(char action){
        switch(action){
            case 'O':
                if(lj > 0) {
                    val[li][lj] = val[li][lj - 1];
                    val[li][lj - 1] = 0;
                    lj--;
                }
                break;
            case 'E':
                if(lj < val[0].length-1) {
                    val[li][lj] = val[li][lj + 1];
                    val[li][lj + 1] = 0;
                    lj++;
                }
                break;
            case 'N':
                if(li > 0) {
                    val[li][lj] = val[li - 1][lj];
                    val[li - 1][lj] = 0;
                    li--;
                }
                break;
            case 'S':
                if(li < val.length-1) {
                    val[li][lj] = val[li + 1][lj];
                    val[li + 1][lj] = 0;
                    li++;
                }
                break;
        }

    };

    public boolean enable(char action){
        return true;
    };
    public void pred(char action){};
    public void reset(){};
    public Jeu copy(){return null;};


    public int[][] returnValues(){
        return this.val;
    }

    public int getNbl(){
        return this.nbl;
    }

    public int getNbc(){
        return this.nbc;
    }

    public int[] getPosition(int numero)
    {
        int[] tab = new int[2];
        for(int i = 0 ; i < val.length; i++)
        {
            for(int j = 0 ; j < val[i].length; j++)
            {
                if(val[i][j] == numero){
                    tab[0] = i;
                    tab[1] = j;
                }
            }
        }
        return tab;
    }

    public int getNumero(int x, int y)
    {
        return val[x][y];
    }

    public void swapNumbers(int numeroEntree, int[] positionSortie)
    {
        int numeroSortie = this.getNumero(positionSortie[0], positionSortie[1]);
        int[] positionEntree = this.getPosition(numeroEntree);
        int memoire = val[positionEntree[0]][positionEntree[1]];

        val[positionEntree[0]][positionEntree[1]] = val[positionSortie[0]][positionSortie[1]];
        val[positionSortie[0]][positionSortie[1]] = memoire ;
    }

    public int nbEchangesFaits()
    {
        int nbEchanges = 0 ;
        for(int i = 0; i< val.length; i++)
        {
            for(int j = 0; j< val.length; j++)
            {
                if (val[i][j] != val2[i][j])
                {
                    this.swapNumbers(val[i][j], getPosition(val2[i][j]));
                    nbEchanges++;
                }

            }
        }
        return (nbEchanges%2);
    }
}
