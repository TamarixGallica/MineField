package minefield.userinterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import minefield.game.*;



public class UserInterface extends JFrame {

    private JPanel playfield = new JPanel();
    private GameLogic gameLogic;
    private KeyBindings panel;
    private JLabel lbVisitedSquares = new JLabel();

    public UserInterface() {

        // Set window title
        setTitle("MineField");

        // Set window size
        setSize(800, 500);

        // Set window location
        setLocation(100, 100);

        // Layout the components
        setComponents();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    public void setComponents() {


        this.gameLogic = new GameLogic();

        panel = new KeyBindings(new BorderLayout(), this);

        Container container = this.getContentPane();

        container.setLayout(new BorderLayout());

        JButton btNewGame = new JButton("New game");

        btNewGame.addActionListener(new AlsNewGame(this));

        container.add(panel);

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.add(btNewGame, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        JLabel lbNumberOfMines = new JLabel("Mines: ");

        lbNumberOfMines.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

        topPanel.add(lbNumberOfMines, BorderLayout.WEST);

        topPanel.add(this.lbVisitedSquares, BorderLayout.CENTER);

        panel.add(playfield, BorderLayout.CENTER);

        playfield.setLayout(new GridLayout(13, 20));

        panel.activateInput();

        this.lbVisitedSquares.setHorizontalAlignment(SwingConstants.CENTER);

        updatePlayField();

        lbNumberOfMines.setText("Mines: "+this.gameLogic.getNumberOfMines());
    }

    private void updatePlayField() {

        ArrayList<GameObject>[][] gameboard = gameLogic.getGameboard();

        playfield.removeAll();

        for(int i=0; i<gameboard.length; i++)
        {
            for(int j=0; j<gameboard[0].length; j++)
            {

                JButton btButton = new JButton();

                String text="";
                if(gameboard[i][j].size()==0)
                {
                    if(this.gameLogic.isSquareVisited(i, j))
                    {
                        text=Integer.toString(this.gameLogic.getNumberOfSurroundingMines(i, j));
                    }
                    else
                        text="";
                }

                else if(containsObject(gameboard[i][j], new GameObjectMine()) && containsObject(gameboard[i][j], new GameObjectPlayer()))
                {
                    text="X";
                    btButton.setBackground(Color.RED);
                }
                else if(containsObject(gameboard[i][j], new GameObjectPlayer())) {
                    text=Integer.toString(this.gameLogic.getNumberOfSurroundingMines(i, j));
                    btButton.setBackground(Color.PINK);
                }
                else if(containsObject(gameboard[i][j], new GameObjectMine()))
                    text = " ";
//                    text="X";
                else if(containsObject(gameboard[i][j], new GameObjectGoal())) {
                    text = " ";
                    btButton.setBackground(Color.GREEN);
                }

                containsObject(gameboard[i][j], new GameObjectPlayer());

                btButton.setEnabled(false);
                btButton.setText(text);
                btButton.setSize(20,20);
                btButton.setMargin(new Insets(0,6, 0,6));
                playfield.add(btButton);
            }
        }

        this.lbVisitedSquares.setText("Visited squares: "+this.gameLogic.getNumberOfVisitedSquares() +"/"+ this.gameLogic.getSizeX()*this.gameLogic.getSizeY() );

        playfield.revalidate();
        playfield.repaint();



    }

    private boolean containsObject(ArrayList<GameObject> objectList, GameObject object) {

        for(int i=0; i<objectList.size(); i++)
        {
            if(objectList.get(i).getClass().getSimpleName().matches(object.getClass().getSimpleName()))
            {
                return true;
            }
        }

        return false;
    }

    public void movePlayer(String direction)
    {
        gameLogic.movePlayer(direction);

        GameObject gameOver = gameLogic.testForGameOver();

        panel.deactivateInput();

        if(gameOver.equals(new GameObjectMine())) {
            showGameEnd(false);

        }
        else if(gameOver.equals(new GameObjectGoal())) {
            showGameEnd(true);
        }
        else {
            panel.activateInput();
        }

        updatePlayField();
    }

    private void showGameEnd(boolean gameWon)
    {
        if(gameWon == false) {
            JOptionPane.showMessageDialog(null, "Boom!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Congratulations!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public static void main(String[] args) {

        UserInterface ui = new UserInterface();
        ui.setVisible(true);

    }
}
