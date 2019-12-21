package eg.edu.alexu.csd.oop.game.Model.Logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
    private static Log logger;
    private static Logger log;
    private Log()
    {
        log=Logger.getLogger("MyLog");
    
    }
    public static Log getInstance() {

        if (Log.logger == null)
             Log.logger=new Log();

        return Log.logger;
    }
    
    public void LogWithLevel(Level level, String s)
    {
    	log.log(level,s);
    }

}
