import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GrilleInterface {

    private JLabel[][] cases;
    private int[] emptySpace = new int[2];
    private int[][] solvedGrid;
    private boolean[][] validatedTiles;

    private final Dimension baseWindowSize = new Dimension(600,600);
    private final Dimension minWindowSize = new Dimension(350,350);
    private final Color emptyTileColor = new Color(159, 210, 252);
    private final Color gridTilesColor = new Color(103, 164, 214);
    private final Color validatedTileColor = new Color(136, 227, 166);

    private JFrame frame;
    private KeyListener gridKeyListener;

    public GrilleInterface(JeuTaquin jeu){
        int nbl = jeu.getNbl();
        int nbc = jeu.getNbc();

        gridKeyListener = new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int[] nextSpace = {emptySpace[0],emptySpace[1]};
                boolean hasChanged = false;

                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        if(nextSpace[1]<nbc-1){
                            nextSpace[1]++;
                            hasChanged=true;
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(nextSpace[1]>0) {
                            nextSpace[1]--;
                            hasChanged=true;
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if(nextSpace[0]<nbl-1) {
                            nextSpace[0]++;
                            hasChanged=true;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(nextSpace[0]>0) {
                            nextSpace[0]--;
                            hasChanged = true;
                        }
                        break;
                }

                if(hasChanged) {
                    JLabel becomeEmpty = cases[nextSpace[0]][nextSpace[1]];
                    JLabel wasEmpty = cases[emptySpace[0]][emptySpace[1]];

                    wasEmpty.setBackground(gridTilesColor);
                    wasEmpty.setText(becomeEmpty.getText());

                    becomeEmpty.setBackground(emptyTileColor);
                    becomeEmpty.setText("");

                    validateTilePosition(emptySpace);

                    emptySpace[0] = nextSpace[0];
                    emptySpace[1] = nextSpace[1];

                }
            }
        };

        int[][] values = jeu.returnValues();
        cases = new JLabel[nbl][nbc];

        solvedGrid = new int[nbl][nbc];
        validatedTiles = new boolean[nbl][nbc];
        int v=1;
        for(int i=0;i<nbl;i++){
            for(int j=0;j<nbc;j++){
                solvedGrid[i][j] = v++;
            }
        }

        frame = new JFrame("Jeu de Taquin");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(baseWindowSize);
        frame.setMinimumSize(minWindowSize);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(nbl, nbc, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        for(int i=0;i<nbl;i++){
            for(int j=0;j<nbc;j++){
                JLabel l;
                if(values[i][j] != 0) {
                    l = new JLabel(String.valueOf(values[i][j]), SwingConstants.CENTER);
                    l.setBackground(gridTilesColor);
                    /*int[] tbVal = {i,j};
                    validateTilePosition(tbVal);*/
                }
                else
                {
                    l = new JLabel("", SwingConstants.CENTER);
                    l.setBackground(emptyTileColor);
                    emptySpace[0] = i;
                    emptySpace[1] = j;
                }

                l.setOpaque(true);
                l.setFont(l.getFont().deriveFont(64.0f));
                l.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panel.add(l);
                cases[i][j] = l;
            }
        }

        frame.add(panel);
        //System.out.println("WAS x: "+emptySpace[0]+", y: "+emptySpace[1]);
        frame.addKeyListener(gridKeyListener);
    }

    private void validateTilePosition(int[] tilePos){
        JLabel tile = cases[tilePos[0]][tilePos[1]];
        int tileNb = Integer.valueOf(tile.getText());
        int tileSpace = solvedGrid[tilePos[0]][tilePos[1]];

        if(tileNb == tileSpace){
            tile.setBackground(validatedTileColor);
            validatedTiles[tilePos[0]][tilePos[1]] = true;

            validateGrid();
        }
        else
        {
            validatedTiles[tilePos[0]][tilePos[1]] = false;
        }
    }

    private void validateGrid(){
        boolean finished = true;
        System.out.println("-----------------------");
        for(int i=0;i<validatedTiles.length;i++){
            String line="| ";
            for(int j=0;j<validatedTiles[i].length;j++){
                if(!validatedTiles[i][j]){
                    finished = false;
                }
                line+=(validatedTiles[i][j]+" |");
            }
            System.out.println(line);
            System.out.println("-----------------------");
        }

        if(finished){
            frame.removeKeyListener(gridKeyListener);
            System.out.println("finished!");
        }
        else
        {
            System.out.println("not finished!");
        }
    }
}
