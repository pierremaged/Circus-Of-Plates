package eg.edu.alexu.csd.oop.game;

import eg.edu.alexu.csd.oop.game.Control.World.Circus;
import eg.edu.alexu.csd.oop.game.Model.strategy.Easy;
import eg.edu.alexu.csd.oop.game.Model.strategy.Hard;
import eg.edu.alexu.csd.oop.game.Model.strategy.Medium;
import eg.edu.alexu.csd.oop.game.Model.strategy.Strategy;

import javax.swing.*;
import java.awt.*;

public class Buttongenerator extends Command {

    private Strategy strategy;
    private Button e,m,h;
    private JFrame frame;

    Buttongenerator(ScreenResolution resolution) {
        super(resolution, null);
    }

    void startMenu(){
        menuBar = new JMenuBar();
        JMenuItem newgame = new JMenuItem("New");
        newgame.addActionListener(e -> gameController.changeWorld( new Circus(resolution.getWidth(),resolution.getHeight(), strategy)));
        JMenu menu = new JMenu("File");
        JMenuItem  exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        menuBar.add(menu);
        pauseMenuItem.addActionListener(e -> gameController.pause());
        resumeMenuItem.addActionListener(e -> gameController.resume());
        menu.add(newgame);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menu.addSeparator();
        menu.add(exitMenuItem);
        menuBar.add(menu);
    }

    void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void generate(){
        //easy Button
        e = new Button();
        e.setSize(100,100);
        e.setLabel("EASY");
        e.addActionListener(e -> {
            setStrategy(1);
            frame.setVisible(false);
            gameController = GameEngine.start("Circus of plates", new Circus(resolution.getWidth(),resolution.getHeight(),strategy), menuBar, Color.BLACK);
        });
        //normal Button
        m = new Button();
        m.setSize(100,100);
        m.setLabel("MEDIUM");
        m.addActionListener(e -> {
            setStrategy(2);
            frame.setVisible(false);
            gameController = GameEngine.start("Circus of plates", new Circus(resolution.getWidth(),resolution.getHeight(), strategy), menuBar, Color.BLACK);
        });
        //hard Button
        h = new Button();
        h.setSize(100,100);
        h.setLabel("HARD");
        h.addActionListener(e -> {
            setStrategy(3);
            frame.setVisible(false);
            gameController = GameEngine.start("Circus of plates"
                    , new Circus(resolution.getWidth(),resolution.getHeight(), strategy)
                    , menuBar, Color.BLACK);
        });
    }

    private void setStrategy(int x){
        if(x==1){
            strategy=new Easy();
        }else if(x==2){
            strategy = new Medium();
        }else if(x==3){
            strategy = new Hard();
        }
    }


    Button getE() {
        return e;
    }

    Button getM() {
        return m;
    }

    Button getH() {
        return h;
    }

}
