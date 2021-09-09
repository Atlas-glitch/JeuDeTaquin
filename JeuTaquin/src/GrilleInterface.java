import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GrilleInterface {

    private final JLabel[][] cases;
    private final int nbl;
    private final int nbc;
    private final int[] emptySpace = new int[2];
    private final int[][] solvedGrid;
    private final boolean[][] validatedTiles;

    private final Color emptyTileColor = new Color(159, 210, 252);
    private final Color gridTilesColor = new Color(103, 164, 214);
    private final Color validatedTileColor = new Color(136, 227, 166);
    private final Color finishedTileColor = new Color(224, 242, 102);

    private final JFrame frame;
    private final KeyListener gridKeyListener;

    public GrilleInterface(JeuTaquin jeu) {
        nbl = jeu.getNbl();
        nbc = jeu.getNbc();

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

                    int[] movedTile = {emptySpace[0], emptySpace[1]};

                    emptySpace[0] = nextSpace[0];
                    emptySpace[1] = nextSpace[1];

                    if(validateTilePosition(movedTile)){
                        if(validateGrid()){
                            final int[] c = { 0, 0 };
                            Timer timer = new Timer(80, null);
                            ActionListener ac = e1 -> {
                                cases[c[0]][c[1]].setBackground(finishedTileColor);
                                c[1]++;
                                if(c[1] == nbc){
                                    c[1] = 0;
                                    c[0]++;
                                    if(c[0] == nbl){
                                        timer.stop();
                                    }
                                }
                            };
                            timer.addActionListener(ac);
                            timer.setRepeats(true);
                            timer.start();
                        }
                    }

                }
            }
        };

        int[][] values = jeu.returnValues();
        cases = new JLabel[nbl][nbc];
        solvedGrid = jeu.getSolvedGrid();
        validatedTiles = new boolean[nbl][nbc];

        frame = new JFrame("Jeu de Taquin");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension baseWindowSize = new Dimension(800, 800);
        frame.setSize(baseWindowSize);
        Dimension minWindowSize = new Dimension(650, 650);
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

                int tileSpace = solvedGrid[i][j];

                if(values[i][j] == tileSpace){
                    l.setBackground(validatedTileColor);
                    validatedTiles[i][j] = true;

                    validateGrid();
                }
            }
        }

        frame.add(panel);
        frame.addKeyListener(gridKeyListener);
    }

    private boolean validateTilePosition(int[] tilePos) {
        boolean result = false;
        JLabel tile = cases[tilePos[0]][tilePos[1]];
        int tileNb = Integer.parseInt(tile.getText());
        int tileSpace = solvedGrid[tilePos[0]][tilePos[1]];

        if(tileNb == tileSpace){
            tile.setBackground(validatedTileColor);
            validatedTiles[tilePos[0]][tilePos[1]] = true;

            result = true;
        }
        else
        {
            validatedTiles[tilePos[0]][tilePos[1]] = false;
        }

        return result;
    }

    private boolean validateGrid() {
        boolean finished = true;
        System.out.println("-----------------------");
        for (int i = 0; i < validatedTiles.length; i++) {
            boolean[] validatedTile = validatedTiles[i];
            //StringBuilder line = new StringBuilder("| ");
            for (int j = 0; j < validatedTile.length; j++) {
                boolean b = validatedTile[j];
                if (!b) {
                    if(i != nbl-1 || j != nbc-1) {
                        finished = false;
                    }
                }
                //line.append(b).append(" |");
            }
            /*System.out.println(line);
            System.out.println("-----------------------");*/
        }

        if(finished){
            frame.removeKeyListener(gridKeyListener);
            JLabel fSquare = cases[nbl-1][nbc-1];
            fSquare.setBackground(validatedTileColor);
            fSquare.setText(String.valueOf(solvedGrid[nbl-1][nbc-1]));
        }

        return finished;
    }
}
