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

//  Set the window layout
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

//  Update game area
    private void updatePlayField() {

//      Get array list containing information about the game board
        ArrayList<GameObject>[][] gameboard = gameLogic.getGameboard();

//      Remove all object from game board
        playfield.removeAll();

//      Iterate over each column
        for(int i=0; i<gameboard.length; i++)
        {
//          Iterate over each row
            for(int j=0; j<gameboard[0].length; j++)
            {

//              Create a new button and set its text to blank
                JButton btButton = new JButton();
                String text="";

//              Check if the square doesn't contain any objects
                if(gameboard[i][j].size()==0)
                {
//                  Check if square has been visited
                    if(this.gameLogic.isSquareVisited(i, j))
                    {
//                      If square has been visited, set number of surrounding mines as text for the button
                        text=Integer.toString(this.gameLogic.getNumberOfSurroundingMines(i, j));
                    }
                    else
//                      If square doesn't contain any objects and hasn't been visited, set text to blank
                        text="";
                }

//              Check if square contains mine and player objects
                else if(containsObject(gameboard[i][j], new GameObjectMine()) && containsObject(gameboard[i][j], new GameObjectPlayer()))
                {
//                  If square contains both a mine and the player mark the square containing a mine with X and set background as bright red to indicate game over
                    text="X";
                    btButton.setBackground(Color.RED);
                }
//                Check if square contains player object
                else if(containsObject(gameboard[i][j], new GameObjectPlayer())) {
//                If square contains player object show number of surrounding mines and set background to pink
                    text=Integer.toString(this.gameLogic.getNumberOfSurroundingMines(i, j));
                    btButton.setBackground(Color.PINK);
                }
//                Check if square contains a mine object
                else if(containsObject(gameboard[i][j], new GameObjectMine())) {
//                    If it does, set square text to blank
                    text = "";
                }
//                Check if square contains a the goal object
                else if(containsObject(gameboard[i][j], new GameObjectGoal())) {
//                    If it doesn't set text to blank and set background to green
                    text = "";
                    btButton.setBackground(Color.GREEN);
                }

//                Disable the button so player can't push it
                btButton.setEnabled(false);

//                Set text for the button
                btButton.setText(text);

//                Set size for the button (square)
                btButton.setSize(20,20);

//                Set margins for the button (square)
                btButton.setMargin(new Insets(0,6, 0,6));

//                Add button (square) to the game board
                playfield.add(btButton);
            }
        }

//        Update number of squares visited and total number of squares
        this.lbVisitedSquares.setText("Visited squares: "+this.gameLogic.getNumberOfVisitedSquares() +"/"+ this.gameLogic.getSizeX()*this.gameLogic.getSizeY() );

//        Update the window
        playfield.revalidate();
        playfield.repaint();



    }

//    Check if array list contains object that shares a class with object
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

//    Move the player
    public void movePlayer(String direction)
    {
//        Pass player's movement to game logic
        gameLogic.movePlayer(direction);

//        Test if game has ended
        GameObject gameOver = gameLogic.testForGameOver();

//        Deactivate keyboard input
        panel.deactivateInput();

//        Check if player has lost the game
        if(gameOver.equals(new GameObjectMine())) {
            showGameEnd(false);

        }
//        Check if player has won the game
        else if(gameOver.equals(new GameObjectGoal())) {
            showGameEnd(true);
        }
        else {
//            If player has neither won or lost the game reactivate keyboard input
            panel.activateInput();
        }

//        Update the window
        updatePlayField();
    }

//    Show different game over message depending on if player won or lost
    private void showGameEnd(boolean gameWon)
    {
        if(gameWon == false) {
            JOptionPane.showMessageDialog(null, "Boom! You have lost the game.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Congratulations! You have won the game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }

    }

//    Main method
    public static void main(String[] args) {

        UserInterface ui = new UserInterface();
        ui.setVisible(true);

    }
}
