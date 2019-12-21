package eg.edu.alexu.csd.oop.game.object;

public class Plate extends Shape{
	private final String color;

	public Plate(String color)
	{
		super(shapeFactory.CreatePlate(color));
		this.color=color;
	}
	public String getColor()
	{
		return this.color;
	}
}
