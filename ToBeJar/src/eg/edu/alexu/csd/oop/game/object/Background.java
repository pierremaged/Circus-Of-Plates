package eg.edu.alexu.csd.oop.game.object;

public class Background extends Shape {
    private final String color;

    public Background(String color) {
        super(shapeFactory.CreateBackground(color));
        this.color=color;
    }

    public String getColor()
    {
        return this.color;
    }
}
