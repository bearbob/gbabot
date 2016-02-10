import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * @author Bjoern Gross
 * @version 1.1
 * @brief Bot configurations
 * @date 10.February 2016
 */

public class BotConfig {
    public static final String TOKEN = "155730106:AAGY1x2ow1mUIIG2Xp1AOfX4GfTnp04BXNk";
    public static final String USERNAME = "PocketDewdes";
    /**
     * The nickname to add the bot with, e.g. @botfather
     */
    public static final String ADDRESS = "dewdebot";
    /**
     * If the bot receives this command, it will shut down
     */
    private static final String KILLCOMMAND = "bjisawesome";
    
    /**
     * coordinates of the screen, starting at the upper left point
     * in a rectangle to the lower right point
     */
    private static final int SCREENSTARTX = 210;
    private static final int SCREENSTARTY = 173;
    private static final int SCREENENDX = 689;
    private static final int SCREENENDY = 603;
    
    /**
     * Determines the delay between two key strokes
     */
    private static final int KEYDELAY = 300;
    /**
     * Determines the delay after the last key stroke and the screenshot
     */
    private static final int SCREENDELAY = 600;
    /**
     * Timeframe within which messages are accepted in seconds. Messages older than the threshold will be ignored
     */
    private static final int TRASHTHRESHOLD = 5*60;
    
    /**
     * The names of the text files for history and goals.
     *@TODO Should be included in the database  
     */
    private static final String HISTORYFILE = "history.txt";
	private static final String GOALFILE = "goal.txt";
    
    
    public static String getKillCommand(){
    	return KILLCOMMAND;
    }
    
    public static int getScreenStartX(){
    	return SCREENSTARTX;
    }
    
    public static int getScreenStartY(){
    	return SCREENSTARTY;
    }
    
    public static int getScreenWidth(){
    	return (SCREENENDX - SCREENSTARTX);
    }
    
    public static int getScreenHeight(){
    	return (SCREENENDY - SCREENSTARTY);
    }
    
    public static int getKeyDelay(){
    	return KEYDELAY;
    }
    
    public static int getDelayBeforeScreenshot(){
    	return SCREENDELAY;
    }
    
    public static String getHistoryName(){
    	return HISTORYFILE;
    }
    
    public static String getGoalName(){
    	return GOALFILE;
    }
    
    /**
     * Returns the time in seconds after which the bot ignores messages.
     * This means if a message is received which was send longer ago 
     * than the threshold, it will be ignored
     * @return
     */
    public static int getTrashThreshold(){
    	return TRASHTHRESHOLD;
    }

    
}
