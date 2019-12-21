package eg.edu.alexu.csd.oop.game.Model.flyweight;

import eg.edu.alexu.csd.oop.game.Control.DynamicLinkage.DynamicLoading;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.Model.state.MovableObject;
import eg.edu.alexu.csd.oop.game.Model.state.State;
import eg.edu.alexu.csd.oop.game.Model.strategy.Strategy;
import eg.edu.alexu.csd.oop.game.object.IShape;
import eg.edu.alexu.csd.oop.game.object.ImageObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FlyWeight {
    private final ArrayList<GameObject> shapes = new ArrayList<>();
    private final List<Class<?>> listofClasses;
    private static final HashMap<String,Object> classMap = new HashMap<>();
    private final int max;
    private final int bombs;
    private static FlyWeight flyWeight;
    private static Strategy difficulty;
    

    private FlyWeight(Strategy strategy , int num) {
        this.listofClasses = new DynamicLoading().load();
        max = num;
        difficulty = strategy;
        bombs = FlyWeight.difficulty.maxBombs();
    }
    public static FlyWeight getInstance(Strategy strategy,int num)
    {
        if(flyWeight==null)
            flyWeight=new FlyWeight(strategy,num);
        return flyWeight;
    }

    public ArrayList<GameObject> createPlates(int width, int height) {

    	//Creating bombs
    	for(int i=0; i<bombs ; ++i)
    	{
    		IShape shape = null;
    		if (classMap.get("Bomb") == null) {
                try {
                    classMap.put("Bomb", listofClasses.get(1).getConstructor(String.class).newInstance("0"));
                    shape = (IShape) classMap.get("Bomb");
                    
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                shape = (IShape)classMap.get("Bomb");
            }
    		State state = new MovableObject();
    		shapes.add(new ImageObject((int) (Math.random() * width), (int) (Math.random() * -1 * height),
    				shape,state));
    	}
    	
    	//Creating Shapes
        for (int i = 0; i < (max-bombs); ++i) {
            State state = new MovableObject();
            shapes.add(new ImageObject((int) (Math.random() * width), (int) (Math.random() * -1 * height),
                    getRandomshape(), state));
        }
        
        Collections.shuffle(shapes);
        
        return shapes;
    }

    private IShape getRandomshape() {
        IShape shape = null;
        String color;
        int num1,num2;
        do{
        	num1 = (1 + (int) (Math.random() * 3));
        }
        //Unwanted Shape or 
        while(num1>difficulty.maxShapes());
        
        do {
        	num2 = (1 + (int) (Math.random() * 5));
        }
        while(num2>difficulty.maxShapesColors());
        
        if (num2 == 1) {
            color = "red";
        } else if (num2 == 2) {
            color = "blue";
        } else if(num2==3) {
            color = "green";
        }else if(num2==4) {
            color = "yellow";
        }else {
            color = "black";
        }

        if (num1 == 1) {
            if (classMap.get(color + "Plate") == null) {
                try {
                    classMap.put(color + "Plate", listofClasses.get(4).getConstructor(String.class).newInstance(color));
                    shape = (IShape) classMap.get(color + "Plate");
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                shape = (IShape)classMap.get(color + "Plate");
            }
        }
        else {
            if (classMap.get(color + "Pot") == null) {
                try {
                    classMap.put(color + "Pot", listofClasses.get(5).getConstructor(String.class).newInstance(color));
                    shape = (IShape) classMap.get(color + "Pot");
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                shape = (IShape)classMap.get(color + "Pot");
            }
        }
        return shape;
    }
}
