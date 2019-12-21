package eg.edu.alexu.csd.oop.game.Control.Snapshot;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private final List<Memento> mementoListRight = new ArrayList<>();
    private final List<Memento> mementoListLeft = new ArrayList<>();

    public void addRight(Memento state){
        mementoListRight.add(state);
    }

    public Memento getRight(int index){
        return mementoListRight.get(index);
    }

    public void removeRight()
    {
        mementoListRight.remove(mementoListRight.size() - 1);
    }

    public void addLeft(Memento state){
        mementoListLeft.add(state);
    }

    public Memento getLeft(int index){
        return mementoListLeft.get(index);
    }

    public void removeLeft()
    {
        mementoListLeft.remove(mementoListLeft.size() - 1);
    }
}
