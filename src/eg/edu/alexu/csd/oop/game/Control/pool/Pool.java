package eg.edu.alexu.csd.oop.game.Control.pool;

import eg.edu.alexu.csd.oop.game.Control.iterator.Iterator;
import eg.edu.alexu.csd.oop.game.Control.iterator.MyIterator;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.Model.flyweight.FlyWeight;
import eg.edu.alexu.csd.oop.game.Model.strategy.Strategy;

import java.util.ArrayList;

public class Pool {

    private int num =0;
    private static Pool objectPool = null;
    private ArrayList<GameObject> thePool = new ArrayList<>();
    private static Strategy strategy;

    private Pool(Strategy strategy) {
    	this.strategy = strategy;
    }

    public static Pool getInstance(Strategy strategy) {
        if (objectPool == null) {
            objectPool = new Pool(strategy);
        }
        return objectPool;

    }

    public void setPool(int width,int height) {
        FlyWeight fw = FlyWeight.getInstance(this.strategy,num);
        thePool=fw.createPlates(width, height);
    }

    public ArrayList<GameObject> usePool(){
        MyIterator itr=new MyIterator(thePool);
        ArrayList<GameObject> thePoolused = new ArrayList<>();
        for (Iterator iter = itr.CreateIterator(); iter.hasNext();) {
            GameObject o = (GameObject) iter.next();
            thePoolused.add(o);
        }
        thePool.clear();
        return thePoolused;

    }

    public void releaseObj(GameObject obj) {
        thePool.add(obj);
    }

    public boolean hasElement() {
        return thePool.size()>0;
    }

    public GameObject useObj() {

        if(!hasElement()) {
            return null;
        }
        return thePool.remove(0);
    }
    public void setNum(int num)
    {
        this.num=num;
    }

}
