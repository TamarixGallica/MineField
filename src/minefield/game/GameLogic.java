package minefield.game;

public class GameLogic {

    private int gameboard[][];

    public GameLogic() {

        gameboard = new int[13][20];

        // Initialize gameboard
        for(int i = 0; i < gameboard.length; i++) {
            for(int j = 0; j < gameboard[0].length; j++) {
                gameboard[i][j]=0;
            }
        }

        gameboard[6][0]=20;
        gameboard[6][19]=30;
    }

    public int[][] getGameboard() {
        return this.gameboard;
    }
}
