package minefield.userinterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlsNewGame implements ActionListener {

    private UserInterface userInterface;

    public AlsNewGame(UserInterface userInterface){
        this.userInterface=userInterface;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Aloitettaisiin uusi peli");
        this.userInterface.setComponents();
    }
}