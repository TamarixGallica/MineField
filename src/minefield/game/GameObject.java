package minefield.game;

public abstract class GameObject {

    private int type=0;

    public GameObject () {

    }

//    @Override public boolean equals(Object o) {
//        boolean same = false;
//
//        if(o != null && o instanceof  GameObject)
//        {
//            same = this.type == ((GameObject) o).type;
//        }
//
//        return same;
//    }

    @Override
    public int hashCode() {
        return type;
    }
}
