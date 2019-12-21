package eg.edu.alexu.csd.oop.game;

import javax.swing.*;

abstract class Command {
    GameEngine.GameController gameController;
    final ScreenResolution resolution;
    JMenuBar menuBar;

    Command(ScreenResolution resolution, JMenuBar menuBar) {
        this.resolution = resolution;
        this.menuBar = menuBar;
    }
    public abstract void generate();
}
