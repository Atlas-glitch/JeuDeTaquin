import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuJeu extends JFrame implements ActionListener {

    private Container c;
    private JLabel nblt;
    private JLabel nbct;
    private JComboBox nbl;
    private JComboBox nbc;
    private JButton sub;
    private int nbMaxLC=7;
    private String[] choiceLC = new String[this.nbMaxLC];

    public MenuJeu()
    {
        for(int i = 0;i<this.nbMaxLC;i++){
            choiceLC[i] = String.valueOf(i+1);
        }

        setTitle("Jeu de Taquin");
        setSize(320,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        this.c = getContentPane();
        this.c.setLayout(null);

        this.nblt = new JLabel("NbLignes", JLabel.CENTER);
        this.nblt.setFont(new Font("Arial", Font.PLAIN, 20));
        this.nblt.setSize(120, 20);
        this.nblt.setLocation(20, 20);
        this.c.add(this.nblt);

        this.nbct = new JLabel("NbColonnes", JLabel.CENTER);
        this.nbct.setFont(new Font("Arial", Font.PLAIN, 20));
        this.nbct.setSize(120, 20);
        this.nbct.setLocation(160, 20);
        this.c.add(this.nbct);

        this.nbl = new JComboBox(choiceLC);
        this.nbl.setFont(new Font("Arial", Font.PLAIN, 15));
        this.nbl.setSize(60, 40);
        this.nbl.setLocation(60, 60);
        this.c.add(this.nbl);

        this.nbc = new JComboBox(choiceLC);
        this.nbc.setFont(new Font("Arial", Font.PLAIN, 15));
        this.nbc.setSize(60, 40);
        this.nbc.setLocation(180, 60);
        this.c.add(this.nbc);

        this.sub = new JButton("Démarrer");
        this.sub.setFont(new Font("Arial", Font.PLAIN, 15));
        this.sub.setSize(120, 50);
        this.sub.setLocation(90, 120);
        this.sub.addActionListener(this);
        this.c.add(this.sub);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == sub) {

            GrilleInterface[] gi = {new GrilleInterface()};
            JeuTaquin jeu = new JeuTaquin(Integer.parseInt(nbl.getSelectedItem().toString()), Integer.parseInt(nbc.getSelectedItem().toString()), gi);

            this.dispose();
        }
    }
}