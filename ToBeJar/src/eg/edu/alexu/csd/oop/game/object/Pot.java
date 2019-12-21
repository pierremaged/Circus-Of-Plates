package eg.edu.alexu.csd.oop.game.object;

public class Pot extends Shape{

	private final String color;

	public Pot(String color)
	{
		super(shapeFactory.CreatePot(color));
		this.color=color;
	}
	public String getColor()
	{
		return this.color;
	}

}
