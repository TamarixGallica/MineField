package minefield.userinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.Key;
import java.util.ArrayList;

import minefield.game.*;

import javax.swing.*;


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

        // Layout the components
        setComponents();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//        KeyBindings keyBindings = new KeyBindings();

    }

    private void setComponents() {

        //JPanel panel = new JPanel(new BorderLayout());

        KeyBindings panel = new KeyBindings(new BorderLayout(), this);

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

        ArrayList<GameObject>[][] gameboard = gameLogic.getGameboard();

        playfield.removeAll();

        for(int i=0; i<gameboard.length; i++)
        {
            for(int j=0; j<gameboard[0].length; j++)
            {
                String text="";
                if(gameboard[i][j].size()==0)
                    text = " ";

                else if(containsObject(gameboard[i][j], new GameObjectPlayer()))
                    text = "P";
                else if(containsObject(gameboard[i][j], new GameObjectMine()))
                    text = "X";
                else if(containsObject(gameboard[i][j], new GameObjectGoal()))
                    text = "G";

                containsObject(gameboard[i][j], new GameObjectPlayer());

                JButton btButton = new JButton(text);
                btButton.setSize(20,20);
                btButton.setMargin(new Insets(0,6, 0,6));
                playfield.add(btButton);
            }
        }

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
        System.out.println(direction);

        gameLogic.movePlayer(direction);

        GameObject gameOver = gameLogic.testForGameOver();

        if(gameOver.equals(new GameObjectMine()))
            System.out.println("Miina koitui kohtaloksi");
        else if(gameOver.equals(new GameObjectGoal()))
            System.out.println("Peli voitettiin");
        else
            System.out.println("Peli jatkuu");

        updatePlayField();
    }

    public static void main(String[] args) {

        UserInterface ui = new UserInterface();
        ui.setVisible(true);

    }
}
