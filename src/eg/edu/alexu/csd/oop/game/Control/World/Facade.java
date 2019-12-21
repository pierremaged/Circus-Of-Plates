package eg.edu.alexu.csd.oop.game.Control.World;

import eg.edu.alexu.csd.oop.game.Control.pool.Pool;
import eg.edu.alexu.csd.oop.game.Model.state.*;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.Control.iterator.Iterator;
import eg.edu.alexu.csd.oop.game.Control.iterator.MyIterator;
import eg.edu.alexu.csd.oop.game.Control.Snapshot.*;
import eg.edu.alexu.csd.oop.game.Control.observer.*;
import eg.edu.alexu.csd.oop.game.object.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class Facade implements IFacade {
    private final Circus game;
    private static CareTaker caretaker;
    private static Originator originator;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private boolean flag = false;
    private final LogManager log ;

    Facade(Circus game) {
        new TimeCalculator(game);
        new Sound(game);
        new ScoreCalculator(game);
        new PlatesControler(game);
        log = new LogManager(game);
        this.game = game;
        caretaker = Circus.getCaretaker();
        originator = Circus.getOriginator();
        saveStateL();
        saveStateR();
    }

    public boolean refreshLogic() {
        GameObject lastplateL = game.getControlL().get(game.getControlL().size() - 1);
        GameObject lastplateR = game.getControlR().get(game.getControlR().size() - 1);
        MyIterator itr=new MyIterator(game.getMoving());// handle moving plates here
        long timePassed = System.currentTimeMillis() - game.getStartTime();

        if (timePassed >= Circus.getMaxTime() - 10*1000)
            eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().almostDone();

        boolean timeout = System.currentTimeMillis() - game.getStartTime() > Circus.getMaxTime(); // time end and game over
        for (Iterator iter = itr.CreateIterator(); iter.hasNext();) {
            ImageObject o = (ImageObject) iter.next();
            o.setY((o.getY() + 1));
            if (o.getY() + 100 >= game.getHeight()) {
                // reuse the plate in another position
                o.setY(-1 * (int) (Math.random() * game.getHeight()));
                o.setX((int) (Math.random() * game.getWidth()));
                log.log(Level.FINE, "Plate reused in another position");
                
            }
            if (!timeout & o.isVisible() && (intersect(o, lastplateL))) {
                int midx = (o.getX() + o.getX() + o.getWidth()) / 2;
                if (midx <= game.getDummyL().getX() + game.getDummyL().getWidth() && midx >= game.getDummyL().getX()) {
                    game.getMoving().remove(o);
                    State state = new ControlableObject(o.getX(), o.getY() + 5);
                    o.setState(state);
                    game.getControl().add(o);
                    game.getControlL().add(o);
                    saveStateL();

                    String color1 = o.getColor();
                   if(color1.equals("0"))
                    {
                        BombExplosion(o, game.getControlL());
                        continue;
                    }
                    eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().caught();
                    String color2 = ((ImageObject) lastplateL).getColor();
                    if (game.getControlL().size() > 3) {
                        String color3 = ((ImageObject) game.getControlL().get(game.getControlL().size() - 3))
                                .getColor();

                        if (color1.equals(color2) && color2.equals(color3)) {
                            notifyAllObserver(1);
                        }
                        else
                        	log.log(Level.FINE, "Plate collected on the Left-Side");
                    }

                } else {
                    FallingPlates(lastplateL, game.getControlL());
                    caretaker.removeLeft();
                    game.setCurrentMementoL(game.getCurrentMementoL() - 1);
                    int scr = game.getScore();
                    scr--;
                    game.setScore(scr);
                    
                    log.log(Level.FINE, "Plate Removed from the Left-Side and Score decreased to "+scr);
                }
            }
            if (!timeout & o.isVisible() && (intersect(o, lastplateR))) {
                int midx = (o.getX() + o.getX() + o.getWidth()) / 2;
                if (midx <= game.getDummyR().getX() + game.getDummyR().getWidth() && midx >= game.getDummyR().getX()) {
                    game.getMoving().remove(o);
                    State state = new ControlableObject(o.getX(), o.getY() + 5);
                    o.setState(state);
                    game.getControl().add(o);
                    game.getControlR().add(o);
                    saveStateR();
                    String color1 = o.getColor();
                    if(color1.equals("0"))
                    {
                        BombExplosion(o, game.getControlR());
                        break;
                    }
                    eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().caught();
                    String color2 = ((ImageObject) lastplateR).getColor();
                    if (game.getControlR().size() > 3) {
                        String color3 = ((ImageObject) game.getControlR().get(game.getControlR().size() - 3))
                                .getColor();
                        if (color1.equals(color2) && color2.equals(color3)) {
                            notifyAllObserver(2);
                        }
                        else
                        	log.log(Level.FINE, "Plate collected on the Right-Side");
                    }
                } else {
                    FallingPlates(lastplateR, game.getControlR());
                    caretaker.removeRight();
                    game.setCurrentMementoR(game.getCurrentMementoR() - 1);
                    int scr = game.getScore();
                    scr--;
                    game.setScore(scr);
                    
                    log.log(Level.FINE, "Plate removed from the Right-Side and Score decreased to "+scr);
                }

            }

        }

        if (game.getControlL().size()==game.getMaxY()+1||game.getControlR().size()==game.getMaxY()+1) {
            if (!flag) {
                flag = true;
                eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().gameOver();
                eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().stopTheme();
                
                log.log(Level.INFO, "Game Over with maximum plates");
            }
            this.game.setTime(0);
        }
        if (game.getControlL().size()+game.getControlR().size()==game.getCreatedPlates()+2) {
            if (!flag) {
                flag = true;
                eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().gameOver();
                eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().stopTheme();

                log.log(Level.INFO, "Game Over with no other plates to be collected");
            }
            this.game.setTime(0);
        }

        if(game.getLive()==0)
        {
            if (!flag) {
                flag = true;
                eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().gameOver();
                eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().stopTheme();
                
                log.log(Level.INFO, "Game over by Finishing Lives");
            }
            this.game.setTime(0);
        }
        if (timeout) {
            if (!flag) {
                flag = true;
                eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().gameOver();
                eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().stopTheme();
                this.game.setTime(0);
            }
        }
        return !timeout;
    }

    private void BombExplosion(ImageObject o, ArrayList<GameObject> controlR) {
        game.setLive(game.getLive()-1);
        game.getControl().remove(o);
        controlR.remove(o);
        eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().boom();
        log.log(Level.INFO, "Bomb Collected and Lives decreased to "+game.getLive());
    }

    private void FallingPlates(GameObject lastplateR, ArrayList<GameObject> controlR) {
        eg.edu.alexu.csd.oop.game.sound.Sound.getInstance().two();
        game.getControl().remove(lastplateR);
        controlR.remove(lastplateR);
        State state = new MovableObject();
        ((ImageObject) lastplateR).setState(state);
        game.getMoving().add(lastplateR);
    }

    public void iterate() {

        MyIterator itr = new MyIterator(game.getControlL());
        for (Iterator iter = itr.CreateIterator(); iter.hasNext();) {
            GameObject o = (GameObject) iter.next();
            o.setX((int) Math.min(o.getX(), screenSize.getWidth() - 225));
        }
        MyIterator itr2 = new MyIterator(game.getControlR());
        for (Iterator iter = itr2.CreateIterator(); iter.hasNext();) {
            GameObject o = (GameObject) iter.next();
            o.setX(Math.max(o.getX(), 157));
        }
    }

    public void pooling() {
        Pool mpl = Pool.getInstance(this.game.getDifficulty());
        if (mpl.hasElement()) {
            State state = new MovableObject();
            GameObject obj = mpl.useObj();
            ((ImageObject) obj).setState(state);
            obj.setX((int) (Math.random() * screenSize.width));
            obj.setY(-1*(int) (Math.random() * screenSize.height));
            game.getMoving().add(obj);
        }
    }

    private boolean intersect(GameObject o1, GameObject o2) {
        int midx = (o1.getX() + o1.getX() + o1.getWidth()) / 2;
        return o1.getY() + o1.getHeight() == o2.getY() && (midx <= o2.getX() + o2.getWidth() && midx >= o2.getX());
    }

    private void saveStateL() {
        originator.setState((ArrayList<GameObject>) game.getControlL().clone());
        caretaker.addLeft(originator.saveStateToMemento());
        game.setCurrentMementoL(game.getCurrentMementoL() + 1);
    }

    private void saveStateR() {
        originator.setState((ArrayList<GameObject>) game.getControlR().clone());
        caretaker.addRight(originator.saveStateToMemento());
        game.setCurrentMementoR(game.getCurrentMementoR() + 1);
    }

    private void notifyAllObserver(int num) {
        for (Observer observer : game.getObservers()) {
            observer.update(num);
        }
    }

}
