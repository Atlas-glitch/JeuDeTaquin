import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GrilleInterface {

    private JLabel[][] cases;
    private int nbl;
    private int nbc;
    private final int[] emptySpace = new int[2];
    private int[][] solvedGrid;
    private boolean[][] validatedTiles;

    private final Color emptyTileColor = new Color(159, 210, 252);
    private final Color gridTilesColor = new Color(103, 164, 214);
    private final Color validatedTileColor = new Color(136, 227, 166);
    private final Color finishedTileColor = new Color(224, 242, 102);

    private JFrame frame;
    private KeyListener gridKeyListener;

    public GrilleInterface() {

    }

    public void createWithData(JeuTaquin jeu, boolean possible){
        this.nbl = jeu.getNbl();
        this.nbc = jeu.getNbc();

        this.gridKeyListener = new KeyListener(){
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
        this.cases = new JLabel[this.nbl][this.nbc];
        this.solvedGrid = jeu.getSolvedGrid();
        this.validatedTiles = new boolean[this.nbl][this.nbc];

        this.frame = new JFrame("Jeu de Taquin");
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension baseWindowSize = new Dimension(650, 650);
        this.frame.setSize(baseWindowSize);
        Dimension minWindowSize = new Dimension(650, 650);
        this.frame.setMinimumSize(minWindowSize);
        this.frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(this.nbl, this.nbc, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        for(int i=0;i<this.nbl;i++){
            for(int j=0;j<this.nbc;j++){
                JLabel l;
                if(values[i][j] != 0) {
                    l = new JLabel(String.valueOf(values[i][j]), SwingConstants.CENTER);
                    l.setBackground(this.gridTilesColor);
                }
                else
                {
                    l = new JLabel("", SwingConstants.CENTER);
                    l.setBackground(this.emptyTileColor);
                    this.emptySpace[0] = i;
                    this.emptySpace[1] = j;
                }

                l.setOpaque(true);
                l.setFont(l.getFont().deriveFont(64.0f));
                l.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panel.add(l);

                this.cases[i][j] = l;

                int tileSpace = this.solvedGrid[i][j];

                if(values[i][j] == tileSpace & tileSpace != 0){
                    l.setBackground(this.validatedTileColor);
                    this.validatedTiles[i][j] = true;

                    this.validateGrid();
                }
            }
        }

        if(!possible){
            this.swapNumbers();
        }

        this.frame.add(panel);
        this.frame.addKeyListener(this.gridKeyListener);
    }

    private boolean validateTilePosition(int[] tilePos) {
        boolean result = false;
        JLabel tile = this.cases[tilePos[0]][tilePos[1]];
        int tileNb = Integer.parseInt(tile.getText());
        int tileSpace = this.solvedGrid[tilePos[0]][tilePos[1]];

        if(tileNb == tileSpace){
            tile.setBackground(this.validatedTileColor);
            this.validatedTiles[tilePos[0]][tilePos[1]] = true;

            result = true;
        }
        else
        {
            this.validatedTiles[tilePos[0]][tilePos[1]] = false;
        }

        return result;
    }

    private boolean validateGrid() {
        boolean finished = true;
        //System.out.println("-----------------------");
        for (int i = 0; i < this.validatedTiles.length; i++) {
            boolean[] validatedTile = this.validatedTiles[i];
            //StringBuilder line = new StringBuilder("| ");
            for (int j = 0; j < validatedTile.length; j++) {
                boolean b = validatedTile[j];
                if (!b) {
                    if(i != this.nbl-1 || j != this.nbc-1) {
                        finished = false;
                    }
                }
                //line.append(b).append(" |");
            }
            /*System.out.println(line);
            System.out.println("-----------------------");*/
        }

        if(finished){
            this.frame.removeKeyListener(this.gridKeyListener);
            JLabel fSquare = this.cases[this.nbl-1][this.nbc-1];
            fSquare.setBackground(this.validatedTileColor);
            fSquare.setText(String.valueOf(this.nbl*this.nbc));
        }

        return finished;
    }

    private void swapNumbers(){
        JLabel c1=null, c2=null;
        for(int i=0;i<this.nbl;i++){
            for(int j=0;j<this.nbc;j++){
                if(cases[i][j].getText().equals("1")){
                    c1 = cases[i][j];
                }
                else if(cases[i][j].getText().equals("2")){
                    c2 = cases[i][j];
                }
            }
        }

        c1.setText("2");
        c2.setText("1");
    }
}
