package minefield.game;

public class GameObjectGoal extends  GameObject{

    private int type=1;

    public GameObjectGoal() {

    }

    @Override public boolean equals(Object o) {
        return (o instanceof GameObjectGoal) && (this.type == ((GameObjectGoal) o).type);
    }

}
