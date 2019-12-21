package eg.edu.alexu.csd.oop.game.Control.observer;

import java.util.logging.Level;

import eg.edu.alexu.csd.oop.game.Control.World.Circus;
import eg.edu.alexu.csd.oop.game.Model.Logging.Log;

public class LogManager extends Observer{

	private final Log logger = Log.getInstance();
	
	public LogManager(Circus game)
	{
		game.attach(this);
        this.logger.LogWithLevel(Level.INFO, "("+game.getDifficulty().getClass().getSimpleName()+") Game Started");
        
	}
	
	@Override
	public void update(int num) {
		
		switch(num)
		{
		case 1:
			logger.LogWithLevel(Level.INFO, "Three Plates Collected on the Left-Side and Score Increased");
			break;
		
		case 2:
			logger.LogWithLevel(Level.INFO, "Three Plated Collected on the Right-Side and Score Increased");
			break;
		}
	}
	
	public void log(Level level, String s)
	{
		logger.LogWithLevel(level, s);
	}
}
