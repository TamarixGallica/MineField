package minefield.game;

public class GameObjectMine extends GameObject {

    private int type=2;

    public GameObjectMine() {

    }

    @Override public boolean equals(Object o) {
        return (o instanceof GameObjectMine) && (this.type == ((GameObjectMine) o).type);
    }

}
