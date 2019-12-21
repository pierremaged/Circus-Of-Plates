package eg.edu.alexu.csd.oop.game.object;

public class Bomb extends Shape {
    private final String color;

    public Bomb(String color)
    {
        super(shapeFactory.CreateBomb(color));
        this.color=color;
    }
    public String getColor()
    {
        return this.color;
    }
}
