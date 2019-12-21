package eg.edu.alexu.csd.oop.game.object;

import eg.edu.alexu.csd.oop.game.Control.DynamicLinkage.DynamicLoading;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.List;

public class ShapeFactory {
	
	private static ShapeFactory  fact = null;
	private final List<Class<?>>classList;

	private ShapeFactory()
	{
		DynamicLoading loading=new DynamicLoading();
		classList=loading.load();
	}
	
	public static ShapeFactory getShapeFactoryInstance()
	{
		if(fact==null)
		{
			fact = new ShapeFactory();
		}
		
		return fact;
	}

	public BufferedImage CreateClown(String color) {
		try {
			return ImageIO.read(classList.get(1).getResourceAsStream("/"+ color + "clown.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BufferedImage CreatePlate(String color) {
		try {
			return ImageIO.read(classList.get(1).getResourceAsStream("/" + color + "plate.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BufferedImage CreateBomb(String color) {
		try {
			return ImageIO.read(classList.get(1).getResourceAsStream("/" + color + "bomb.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BufferedImage CreatePot(String color) {
		try {
			return ImageIO.read(classList.get(1).getResourceAsStream("/" + color + "pot.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


	public BufferedImage CreateBackground(String color) {
		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int height = (int)(screenSize.getHeight() - 120.0D);
			int width = (int)screenSize.getWidth();
			BufferedImage img = ImageIO.read(classList.get(0).getResourceAsStream("/"+ color+ "background.png"));
			Image tmp = img.getScaledInstance(width, height, 4);
			BufferedImage ScaledImage = new BufferedImage(width, height, 2);
			Graphics2D g2d = ScaledImage.createGraphics();
			g2d.drawImage(tmp, 0, 0, null);
			g2d.dispose();
			return ScaledImage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
