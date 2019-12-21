package eg.edu.alexu.csd.oop.game.object;

public class Clown extends Shape {
    private final String color;

    public Clown(String color) {
        super(shapeFactory.CreateClown(color));
        this.color=color;
    }

    public String getColor()
    {
        return this.color;
    }
}
