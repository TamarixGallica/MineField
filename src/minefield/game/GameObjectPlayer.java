package minefield.game;

public class GameObjectPlayer extends GameObject {

    private int type=3;

    public GameObjectPlayer() {

    }

    @Override public boolean equals(Object o) {
        return (o instanceof GameObjectPlayer) && (this.type == ((GameObjectPlayer) o).type);
    }

}
