package minefield.game;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

    private ArrayList<GameObject>[][] gameboard = new ArrayList[13][20];

    // Track squares player has visited
    private boolean[][] visitedSquares = new boolean[gameboard.length][gameboard[0].length];

    // Store number of mines in surrounding squares
    private int[][] surroundingMines = new int[13][20];

    public GameLogic() {

  //      gameboard = new int[13][20];

//        gameboard = new ArrayList<GameObject>[13][20];

        // Initialize gameboard and visited squares
        for(int i = 0; i < gameboard.length; i++) {
            for(int j = 0; j < gameboard[0].length; j++) {
                gameboard[i][j] = new ArrayList<GameObject>();
                visitedSquares[i][j] = false;
            }
        }





        System.out.println("Pelilaudan koko: "+gameboard.length+" x "+gameboard[0].length);

        //populateMineField();
        populateMineFieldByRandom();

        /*
        gameboard[6][0]=16;
        gameboard[6][19]=32;
*/

    }

    public ArrayList<GameObject>[][] getGameboard() {
        return this.gameboard;
    }

    // Returns number of visited squares
    public int getNumberOfVisitedSquares()
    {
        // Start with zero squares
        int returnValue=0;

        // Iterate over each row
        for(boolean[] row : this.visitedSquares)
        {
            // Iterate over each square
            for(boolean square : row)
            {
                // If square contains a mine (square is true) accumulate return value by one
                if(square==true)
                {
                    returnValue++;
                }
            }
        }

        return returnValue;
    }

    // returns width of the gameboard
    public int getSizeX()
    {
        return this.gameboard.length;
    }

    // Returns height of the gameboard
    public int getSizeY()
    {
        return this.gameboard[0].length;
    }

    // Calculates number of mines placed
    public int getNumberOfMines()
    {
        // Start with zero mines
        int returnValue=0;

        // Iterate over each row
        for(ArrayList<GameObject> row[] : this.gameboard)
        {
            // Iterate over each square
            for(ArrayList<GameObject> square : row)
            {
                // If there's a mine in the square accumulate return value
                if(square.contains(new GameObjectMine()))
                {
                    returnValue++;
                }
            }
        }

        return returnValue;
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
//                if(this.gameboard[i][j].contains(new GameObjectPlayer()))
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
            this.gameboard[horizontal][vertical].remove(new GameObjectPlayer());
            this.visitedSquares[horizontal][vertical+1]=true;
        }
        else if(direction.matches("Down"))
        {
            if(isMoveLegal(horizontal,vertical, direction)==false)
                return;
            this.gameboard[horizontal+1][vertical].add(new GameObjectPlayer());
            this.gameboard[horizontal][vertical].remove(new GameObjectPlayer());
            this.visitedSquares[horizontal+1][vertical]=true;
        }
        else if(direction.matches("Left"))
        {
            if(isMoveLegal(horizontal,vertical, direction)==false)
                return;
            this.gameboard[horizontal][vertical-1].add(new GameObjectPlayer());
            this.gameboard[horizontal][vertical].remove(new GameObjectPlayer());
            this.visitedSquares[horizontal][vertical-1]=true;
        }
        else if(direction.matches("Up"))
        {
            if(isMoveLegal(horizontal,vertical, direction)==false)
                return;
            this.gameboard[horizontal-1][vertical].add(new GameObjectPlayer());
            this.gameboard[horizontal][vertical].remove(new GameObjectPlayer());
            this.visitedSquares[horizontal-1][vertical]=true;
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

    public GameObject testForGameOver(){

        for(int i=0; i<this.gameboard.length; i++)
        {
            for(int j=0; j<this.gameboard[0].length; j++)
            {
                if(this.gameboard[i][j].contains(new GameObjectPlayer()) && this.gameboard[i][j].contains(new GameObjectGoal()))
                    return new GameObjectGoal();
                if(this.gameboard[i][j].contains(new GameObjectPlayer()) && this.gameboard[i][j].contains(new GameObjectMine()))
                    return new GameObjectMine();
            }
        }

        return new GameObjectPlayer();
    }

    public int getNumberOfSurroundingMines(int i, int j)
    {
        return this.surroundingMines[i][j];
    }

    private int countNumberOfSurroundingMines(int i, int j)
    {
        int returnValue=0;

//        ArrayList<int[][]> surroudingSquare = new ArrayList<int[][]>();

        for(int ii=i-1; ii<=i+1; ii++)
        {
            for(int jj=j-1; jj<=j+1; jj++)
            {
                if(ii==i && jj==j)
                    continue;
                try{
                    if(this.gameboard[ii][jj].contains(new GameObjectMine()))
                    {
                        returnValue++;
                    }
                }
                catch (Exception ex) {
                    // No need to handle this exception
                }
            }
        }

        return returnValue;

    }

    public boolean isSquareVisited(int i, int j) {
        return this.visitedSquares[i][j];
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
        this.visitedSquares[6][0]=true;
        this.gameboard[6][19].add(new GameObjectGoal());

    }

    private void populateMineFieldByRandom() {

        for(int i=0; i<50; i++)
        {
            Random r = new Random();
            int x = r.nextInt(this.gameboard.length);
            int y = r.nextInt(this.gameboard[0].length);
            this.gameboard[x][y].add(new GameObjectMine());
        }

        this.gameboard[5][0].clear();
        this.gameboard[5][1].clear();
        this.gameboard[6][0].clear();
        this.gameboard[6][1].clear();
        this.gameboard[7][0].clear();
        this.gameboard[7][1].clear();

        this.gameboard[6][0].add(new GameObjectPlayer());
        this.visitedSquares[6][0]=true;

        this.gameboard[5][18].clear();
        this.gameboard[5][19].clear();
        this.gameboard[6][18].clear();
        this.gameboard[6][19].clear();
        this.gameboard[7][18].clear();
        this.gameboard[7][19].clear();


        this.gameboard[6][19].add(new GameObjectGoal());

        for(int i=0; i<this.gameboard.length; i++)
        {
            for(int j=0; j<this.gameboard[0].length; j++)
            {
                this.surroundingMines[i][j]=this.countNumberOfSurroundingMines(i, j);
            }
        }
    }

}
