package minefield.userinterface;

import javax.swing.*;
import java.awt.*;
import minefield.game.GameLogic;


public class UserInterface extends JFrame {

    private JPanel playfield = new JPanel();
    private GameLogic gameLogic = new GameLogic();

    public UserInterface() {

        // Set window title
        setTitle("MineField");

        // Set window size
        setSize(800, 500);

        // Set window location
        setLocation(100, 100);

        gameLogic.getGameboard();

        setComponents();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void setComponents() {

        JPanel panel = new JPanel(new BorderLayout());

        Container container = this.getContentPane();

        container.setLayout(new BorderLayout());

        JButton btNewGame = new JButton("New game");

        container.add(panel);

        panel.add(btNewGame, BorderLayout.NORTH);

        panel.add(playfield, BorderLayout.CENTER);

        //playfield.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));

        playfield.setLayout(new GridLayout(13, 20));

        updatePlayField();
    }

    private void updatePlayField() {

        int[][] gameboard = gameLogic.getGameboard();

        playfield.removeAll();

        for(int i=0; i<gameboard.length; i++)
        {
            for(int j=0; j<gameboard[0].length; j++)
            {
                JButton btButton = new JButton(""+gameboard[i][j]);
                btButton.setSize(20,20);
                btButton.setMargin(new Insets(0,6, 0,6));
                playfield.add(btButton);
            }
        }
    }

    public static void main(String[] args) {

        UserInterface ui = new UserInterface();
        ui.setVisible(true);

    }
}
