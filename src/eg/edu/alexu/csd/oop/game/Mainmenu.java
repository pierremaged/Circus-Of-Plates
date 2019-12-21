package eg.edu.alexu.csd.oop.game;

public class Mainmenu {
    private final ScreenResolution resolution;

    public Mainmenu( ScreenResolution screenResolution) {
        resolution = screenResolution;
    }

    public void start() {
        Buttongenerator buttonGenerator = new Buttongenerator( resolution);
        buttonGenerator.startMenu();
        buttonGenerator.generate();
        Difficulty difficultyBox = new Difficulty(resolution, buttonGenerator.menuBar);
        difficultyBox.setFrame();
        buttonGenerator.setFrame(difficultyBox.getFrame());
        difficultyBox.setButtonGenerator(buttonGenerator);
        difficultyBox.generate();
    }

}
