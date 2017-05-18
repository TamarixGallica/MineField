package minefield.game;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

    private ArrayList<GameObject>[][] gameboard = new ArrayList[13][20];

    // Track squares player has visited, true if player has visited and false if player has not visited
    private boolean[][] visitedSquares = new boolean[gameboard.length][gameboard[0].length];

    // Store number of mines in surrounding squares
    private int[][] surroundingMines = new int[gameboard.length][gameboard[0].length];

    public GameLogic() {

        // Initialize gameboard and visited squares
        for(int i = 0; i < gameboard.length; i++) {
            for(int j = 0; j < gameboard[0].length; j++) {
                gameboard[i][j] = new ArrayList<GameObject>();
                visitedSquares[i][j] = false;
            }
        }

        // Fill game board with mines
        populateMineFieldByRandom();


    }

    // returns copy of current game board
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

    // Moves player to given direction
    public void movePlayer(String direction)
    {
        // Initialize variables used to store player's location
        int horizontal = -1;
        int vertical = -1;

        // Iterate over each row
        for(int i = 0; i < this.gameboard.length; i++)
        {
            // Iterate over each square
            for(int j = 0; j < this.gameboard[0].length; j++)
            {
                // If square contains player object store the location and break the loop
                if(containsObject(this.gameboard[i][j], new GameObjectPlayer()))
                {
                    horizontal = i;
                    vertical = j;
                    break;
                }
            }
        }

        // Go through each possible direction player can be moved to
        if(direction.matches("Right"))
        {
            // Check if the move is legal and exit method if move is not legal
            if(isMoveLegal(horizontal,vertical, direction)==false)
                return;

            // If move is legal add player object to square on the right side of player's current square
            this.gameboard[horizontal][vertical+1].add(new GameObjectPlayer());
            // Remove player object from current square
            this.gameboard[horizontal][vertical].remove(new GameObjectPlayer());
            // Mark the new square as visited
            this.visitedSquares[horizontal][vertical+1]=true;
        }
        else if(direction.matches("Down"))
        {
            // Check if the move is legal and exit method if move is not legal
            if(isMoveLegal(horizontal,vertical, direction)==false)
                return;

            // If move is legal add player object to square on top of player's current square
            this.gameboard[horizontal+1][vertical].add(new GameObjectPlayer());
            // Remove player object from current square
            this.gameboard[horizontal][vertical].remove(new GameObjectPlayer());
            // Mark the new square as visited
            this.visitedSquares[horizontal+1][vertical]=true;
        }
        else if(direction.matches("Left"))
        {
            // Check if the move is legal and exit method if move is not legal
            if(isMoveLegal(horizontal,vertical, direction)==false)
                return;

            // If move is legal add player object to square on the left side of player's current square
            this.gameboard[horizontal][vertical-1].add(new GameObjectPlayer());
            // Remove player object from current square
            this.gameboard[horizontal][vertical].remove(new GameObjectPlayer());
            // Mark the new square as visited
            this.visitedSquares[horizontal][vertical-1]=true;
        }
        else if(direction.matches("Up"))
        {
            // Check if the move is legal and exit method if move is not legal
            if(isMoveLegal(horizontal,vertical, direction)==false)
                return;
            // If move is legal add player object to square on below player's current square
            this.gameboard[horizontal-1][vertical].add(new GameObjectPlayer());
            // Remove player object from current square
            this.gameboard[horizontal][vertical].remove(new GameObjectPlayer());
            // Mark the new square as visited
            this.visitedSquares[horizontal-1][vertical]=true;
        }
    }

    // Check if square contains an object which shares a class with given object
    // Returns true if contains, false if not
    private boolean containsObject(ArrayList<GameObject> objectList, GameObject object) {

        // Iterate over contents of square
        for(int i=0; i<objectList.size(); i++)
        {
            // Check if given element's class in array list is of same class as object
            if(objectList.get(i).getClass().getSimpleName().matches(object.getClass().getSimpleName()))
            {
                return true;
            }
        }

        // If all squares have been searched without a match, return false
        return false;
    }

    // Check if move is legal
    // Takes horizontal and vertical coordinates and direction where player is trying to go to
    // Returns true if move is legal, false if not
    private boolean isMoveLegal(int horizontal, int vertical, String direction)
    {
        // Go through each direction and return false if either proposed vertical or horizontal coordinate would fall of the board
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

    // Check if the game has ended
    // Returns GameObjectPlayer if game hasn't ended
    // Returns GameObjectGoal if player has won the game by getting to same square with the goal
    // Returns GameObjectMine if player has lost the game by getting to same square with a mine
    public GameObject testForGameOver()
    {
        // Iterate over each row
        for(int i=0; i<this.gameboard.length; i++)
        {
            // Iterate over each square
            for(int j=0; j<this.gameboard[0].length; j++)
            {
                // If same square contains player object and goal object, return GameObjectGoal to indicate that player has won
                if(this.gameboard[i][j].contains(new GameObjectPlayer()) && this.gameboard[i][j].contains(new GameObjectGoal()))
                    return new GameObjectGoal();
                // If same square contains player object and goal object, return GameObjectMine to indicate that player has lost
                if(this.gameboard[i][j].contains(new GameObjectPlayer()) && this.gameboard[i][j].contains(new GameObjectMine()))
                    return new GameObjectMine();
            }
        }

        // If all check are done already, return GameObjectPlayer to indicate that the game continues
        return new GameObjectPlayer();
    }

    // Return number of mines surrounding square at i,j
    // Used only by GameLogic
    public int getNumberOfSurroundingMines(int i, int j)
    {
        return this.surroundingMines[i][j];
    }

    // Return number of mines surrounding square at i,j
    // Used by PopulateMineFieldByRandom
    private int countNumberOfSurroundingMines(int i, int j)
    {
        // Initialize return value as zero
        int returnValue = 0;

        // Go through i-1, i and i+1
        for (int horizontal = i - 1; horizontal <= i + 1; horizontal++) {
            // Go through j-1, i and i+1
            for (int vertical = j - 1; vertical <= j + 1; vertical++) {
                // Though shalt not count the square the information was requested for
                if (horizontal == i && vertical == j)
                    continue;
                // If no ArrayOutOfBounds exception was raised increment return value by one
                try {
                    if (this.gameboard[horizontal][vertical].contains(new GameObjectMine())) {
                        returnValue++;
                    }
                } catch (Exception ex) {
                    // No need to handle this exception
                }
            }
        }

        return returnValue;
    }

    // Return true if square has been visited, false if not
    public boolean isSquareVisited(int i, int j) {
        return this.visitedSquares[i][j];
    }

    // Fill squares with mines at random
    private void populateMineFieldByRandom() {

        // Initialize random number generator
        Random r = new Random();

        // Place 50 mines
        for(int i=0; i<50; i++)
        {
            // Take x and y coordinates where to place a mine
            int x = r.nextInt(this.gameboard.length);
            int y = r.nextInt(this.gameboard[0].length);

            // Place a mine in the square (doesn't matter if there already is one)
            this.gameboard[x][y].add(new GameObjectMine());
        }

        // Clear squares surrounding the player of mines
        this.gameboard[5][0].clear();
        this.gameboard[5][1].clear();
        this.gameboard[6][0].clear();
        this.gameboard[6][1].clear();
        this.gameboard[7][0].clear();
        this.gameboard[7][1].clear();

        // Place player object in starting position and mark the square as visited
        this.gameboard[6][0].add(new GameObjectPlayer());
        this.visitedSquares[6][0]=true;

        // Cleare squares surrounding the goal
        this.gameboard[5][18].clear();
        this.gameboard[5][19].clear();
        this.gameboard[6][18].clear();
        this.gameboard[6][19].clear();
        this.gameboard[7][18].clear();
        this.gameboard[7][19].clear();

        // Place goal on board
        this.gameboard[6][19].add(new GameObjectGoal());

        // Iterate over each square and store number of mines surrounding each square in an array
        for(int i=0; i<this.gameboard.length; i++)
        {
            for(int j=0; j<this.gameboard[0].length; j++)
            {
                this.surroundingMines[i][j]=this.countNumberOfSurroundingMines(i, j);
            }
        }
    }

}
