package eg.edu.alexu.csd.oop.game.Control.Snapshot;

import java.util.ArrayList;

public class Memento {
    private final ArrayList<?> state;

    Memento(ArrayList<?>state)
    {
        this.state=(ArrayList<?>)state.clone();
    }

    public ArrayList<?> getState()
    {
        return (ArrayList<?>) state.clone();
    }
}
