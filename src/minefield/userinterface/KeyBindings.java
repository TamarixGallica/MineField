package minefield.userinterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class KeyBindings extends JPanel {

    private UserInterface userInterface;

    private boolean inputActive=false;

    public KeyBindings(BorderLayout layout, UserInterface userInterface) {

        super(layout);

        this.userInterface = userInterface;

        ActionMap actionMap = getActionMap();
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);

        for(Direction direction : Direction.values())
        {
            inputMap.put(direction.getKeyStroke(), direction.getText());
            actionMap.put(direction.getText(), new MyArrowBinding(direction.getText()));
        }
    }

    public void activateInput(){
        this.inputActive=true;
    }

    public void deactivateInput(){
        this.inputActive=false;
    }

    private class MyArrowBinding extends AbstractAction {
        public MyArrowBinding(String text) {
            super(text);
            putValue(ACTION_COMMAND_KEY, text);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(inputActive==true)
            {
                String actionCommand = e.getActionCommand();
                System.out.println("Key Binding: " + actionCommand);

                userInterface.movePlayer(actionCommand);
            }
        }
    }
}

enum Direction {
    UP("Up", KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0)),
    DOWN("Down", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0)),
    LEFT("Left", KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0)),
    RIGHT("Right", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));

    Direction(String text, KeyStroke keyStroke) {
        this.text = text;
        this.keyStroke = keyStroke;
    }
    private String text;
    private KeyStroke keyStroke;

    public String getText() {
        return text;
    }

    public KeyStroke getKeyStroke() {
        return keyStroke;
    }

    @Override
    public String toString() {
        return text;
    }
}