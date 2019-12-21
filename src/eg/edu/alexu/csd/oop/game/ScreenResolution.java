package eg.edu.alexu.csd.oop.game;

import java.awt.*;

public class ScreenResolution {

    private int height;
    private int width;

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    private void getScreenResolution(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        height = screenSize.height-120 ;
        width = screenSize.width ;
    }

    public ScreenResolution() {
        getScreenResolution();
    }
}
