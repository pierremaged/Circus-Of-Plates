package eg.edu.alexu.csd.oop.game.Control.World;

import eg.edu.alexu.csd.oop.game.Control.pool.Pool;
import eg.edu.alexu.csd.oop.game.Model.state.*;
import eg.edu.alexu.csd.oop.game.Control.Snapshot.*;
import eg.edu.alexu.csd.oop.game.sound.*;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.Model.strategy.Strategy;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.Control.observer.Observer;
import eg.edu.alexu.csd.oop.game.Control.DynamicLinkage.*;
import eg.edu.alexu.csd.oop.game.object.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Circus implements World {

    private static int MAX_TIME;
    private int score = 0;
    private int live;
    private final long startTime = System.currentTimeMillis();
    private final int width;
    private final int height;
    private final Strategy difficulty;
    private final ArrayList<GameObject> constant = new ArrayList<>();
    private final ArrayList<GameObject> moving ;
    private final int maxNum;
    private final ArrayList<GameObject> control = new ArrayList<>();
    private ArrayList<GameObject> controlL = new ArrayList<>();
    private ArrayList<GameObject> controlR = new ArrayList<>();
    private final List<Observer> observers = new ArrayList<>();
    private static final CareTaker caretaker = new CareTaker();
    private static final Originator originator = new Originator();
    private int currentMementoL = 0;
    private int currentMementoR = 0;
    private final ImageObject dummyL;
    private final ImageObject dummyR;
    private final IFacade logic;
    private final int limitY;

    public Circus(int width, int height, Strategy difficulty) {

        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        Circus.MAX_TIME = this.difficulty.GameTime();
        this.limitY = this.difficulty.MaxHeightOfPlate();
        this.maxNum= this.difficulty.NoOfFallingShapes();
        this.live=this.difficulty.live();
        State state=new ConstantObject();
        List<Class<?>> listofClasses = new DynamicLoading().load();
        IShape shape = null;
        try {
            shape = (IShape) listofClasses.get(0).getConstructor(String.class).newInstance("0");
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        ImageObject bg = new ImageObject(0, 0, Objects.requireNonNull(shape),  state);
        constant.add(bg);
        try {
            shape = (IShape) listofClasses.get(2).getConstructor(String.class).newInstance("");
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        state = new ControlableObject(width / 3, (int) (height * 0.6));
        ImageObject clown = new ImageObject(width / 3, (int) (height * 0.6), shape, state);
        try {
            shape = (IShape) listofClasses.get(4).getConstructor(String.class).newInstance("");
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        state = new ControlableObject(clown.getX() - 25, clown.getY() - 10);
        dummyL = new ImageObject(clown.getX() - 25, clown.getY() - 10, shape, state);
        state = new ControlableObject(clown.getX() + clown.getWidth() - 45, clown.getY() - 10);
        dummyR = new ImageObject(clown.getX() + clown.getWidth() - 45, clown.getY() - 10, shape,  state);
        control.add(clown);
        control.add(dummyL);
        control.add(dummyR);
        controlR.add(dummyR);
        controlL.add(dummyL);
        Sound.getInstance().stopTheme();
        Sound.getInstance().startTheme();
        Pool mpl = Pool.getInstance(this.difficulty);
        mpl.setNum(maxNum);
        mpl.setPool(width, height);
        moving = mpl.usePool();
        logic = new Facade(this);
    }

    List<eg.edu.alexu.csd.oop.game.Control.observer.Observer> getObservers() {
        return observers;
    }

    ArrayList<GameObject> getMoving() {
        return moving;
    }

    public ArrayList<GameObject> getControl() {
        return control;
    }

    static int getMaxTime() {
        return MAX_TIME;
    }

    long getStartTime() {
        return startTime;
    }

    public void setControlL(ArrayList<GameObject> controlL) {
        this.controlL = controlL;
    }

    public void setControlR(ArrayList<GameObject> controlR) {
        this.controlR = controlR;
    }

    public ArrayList<GameObject> getControlL() {
        return controlL;
    }

    public ArrayList<GameObject> getControlR() {
        return controlR;
    }

    public Strategy getDifficulty() {
        return difficulty;
    }

    public int getCurrentMementoL() {
        return currentMementoL;
    }

    public int getCurrentMementoR() {
        return currentMementoR;
    }

    public static CareTaker getCaretaker() {
        return caretaker;
    }

    public void setCurrentMementoL(int currentMementoL) {
        this.currentMementoL = currentMementoL;
    }

    public void setCurrentMementoR(int currentMementoR) {
        this.currentMementoR = currentMementoR;
    }

    public static Originator getOriginator() {
        return originator;
    }

    @Override
    public List<GameObject> getConstantObjects() {
        return constant;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        return moving;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        return control;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    ImageObject getDummyL() {
        return dummyL;
    }

    ImageObject getDummyR() {
        return dummyR;
    }

    @Override
    public boolean refresh() {
        logic.pooling();
        logic.iterate();
        return logic.refreshLogic();
    }

    @Override
    public String getStatus() {
        return "Score=" + score + "   |   Time="
                + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000)+"   |   Live="+this.getLive();
         // update status
    }

    @Override
    public int getSpeed() {
        return difficulty.Speed();
    }

    @Override
    public int getControlSpeed() {
        return 20;
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public int getTime() {
        return Circus.MAX_TIME;
    }

    public int getScore() {
        return this.score;
    }

    public void setTime(int sTime) {
        MAX_TIME = sTime;
    }

    public void setScore(int score) {
        score = Math.max(score, 0);
        this.score = score;
    }

    int getLive()
    {
        return this.live;
    }

    void setLive(int live)
    {
        this.live=live;
    }

    int getMaxY() {
        return this.limitY;
    }

    int getCreatedPlates()
    {
        return this.maxNum;
    }

}
