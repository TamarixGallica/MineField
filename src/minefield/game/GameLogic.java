package minefield.game;

import java.util.ArrayList;

public class GameLogic {

    private ArrayList<GameObject>[][] gameboard = new ArrayList[13][20];

//    private int gameboard[][];

    public GameLogic() {

  //      gameboard = new int[13][20];

//        gameboard = new ArrayList<GameObject>[13][20];

        // Initialize gameboard
        for(int i = 0; i < gameboard.length; i++) {
            for(int j = 0; j < gameboard[0].length; j++) {
                gameboard[i][j] = new ArrayList<GameObject>();
            }
        }





        System.out.println("Pelilaudan koko: "+gameboard.length+" x "+gameboard[0].length);

        populateMineField();

        /*
        gameboard[6][0]=16;
        gameboard[6][19]=32;
*/

    }

    public ArrayList<GameObject>[][] getGameboard() {
        return this.gameboard;
    }

    public void movePlayer(String direction)
    {
        int horizontal = -1;
        int vertical = -1;
        for(int i = 0; i < this.gameboard.length; i++)
        {
            for(int j = 0; j < this.gameboard[0].length; j++)
            {
                if(containsObject(this.gameboard[i][j], new GameObjectPlayer()))
                {
                    horizontal = i;
                    vertical = j;
                    System.out.println(": "+horizontal+" "+vertical);
                    break;
                }
            }
        }

        if(direction.matches("Right"))
        {
            if(isMoveLegal(horizontal,vertical, direction)==false)
                return;
            this.gameboard[horizontal][vertical+1].add(new GameObjectPlayer());
            this.gameboard[horizontal][vertical].remove(0);
        }
        else if(direction.matches("Down"))
        {
            if(isMoveLegal(horizontal,vertical, direction)==false)
                return;
            this.gameboard[horizontal+1][vertical].add(new GameObjectPlayer());
            this.gameboard[horizontal][vertical].remove(0);
        }
        else if(direction.matches("Left"))
        {
            if(isMoveLegal(horizontal,vertical, direction)==false)
                return;
            this.gameboard[horizontal][vertical-1].add(new GameObjectPlayer());
            this.gameboard[horizontal][vertical].remove(0);
        }
        else if(direction.matches("Up"))
        {
            if(isMoveLegal(horizontal,vertical, direction)==false)
                return;
            this.gameboard[horizontal-1][vertical].add(new GameObjectPlayer());
            this.gameboard[horizontal][vertical].remove(0);
        }
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

    private boolean isMoveLegal(int horizontal, int vertical, String direction)
    {
        if(direction.matches("Right"))
        {
            if((vertical+1)>=(gameboard[0].length))
                return false;
        }
        else if(direction.matches("Down"))
        {
            if((horizontal+1)>=(gameboard.length))
                return false;
        }
        else if(direction.matches("Left"))
        {
            if(vertical<=0)
                return false;
        }
        else if(direction.matches("Up"))
        {
            if(horizontal<=0)
                return false;
        }

        return true;
    }

    private void populateMineField() {


        for(int i=0; i < 13; i++)
        {
            this.gameboard[i][i].add(new GameObjectMine());
            this.gameboard[12-i][i].add(new GameObjectMine());
        }

        this.gameboard[4][4].remove(this.gameboard[4][4].get(0));
        this.gameboard[5][7].remove(this.gameboard[5][7].get(0));

        this.gameboard[6][0].add(new GameObjectPlayer());
        this.gameboard[6][19].add(new GameObjectGoal());
        
    }

}
