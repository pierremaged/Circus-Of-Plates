package eg.edu.alexu.csd.oop.game.object;

import java.awt.image.BufferedImage;

public abstract class Shape implements IShape {
	private final BufferedImage bufferedImage;
	static final ShapeFactory shapeFactory=ShapeFactory.getShapeFactoryInstance();

	Shape(BufferedImage image)
	{
		this.bufferedImage=image;
	}
	public BufferedImage getImage()
	{
		return this.bufferedImage;
	}
	public String getColor()
	{
		return this.getColor();
	}
}
