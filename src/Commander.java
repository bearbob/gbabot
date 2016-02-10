import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * @author Bjoern Gross
 * @version 1.6
 * @brief Java AWT robot to controll the emulator with
 * @date 8.February 2016
 */

public class Commander {
	private Robot robot;
	private Keyboard kebo;
	
	/**
	 * Creates a new commander with the given settings
	 */
	public Commander(Keyboard keyboard) throws AWTException {
		robot = new Robot();
		robot.setAutoDelay(50);
		robot.setAutoWaitForIdle(true);
		kebo = keyboard;
	}
	
	public String input(int button, int player) {
		return input(button, 1, player);
	}
	
	public String input(int button, int times, int player) {
		int t = times;
		int keycode = kebo.getKey(button, player);
		if(times < 1 || times > 20){
			t = 1;
		}
		for(int i=0; i<t; i++){
			robot.keyPress(keycode);
			robot.delay(10);
			robot.keyRelease(keycode);
			robot.delay(BotConfig.getKeyDelay());
		}
		robot.delay(BotConfig.getDelayBeforeScreenshot());
		
		return getScreenshot();
	}
	
	public boolean saveGame(){
		for(int i : kebo.getSaveKeys()){
			robot.keyPress(i);
		}
		robot.delay(10);
		for(int i : kebo.getSaveKeys()){
			robot.keyRelease(i);
		}
		
		return true;
	}
	
	/**
	 * This key combination is designed to be used only with a
	 * pokemon game where you open the menu, press down once
	 * and then a once
	 * @return
	 */
	public String showPokemon(){
		robot.keyPress(kebo.getKey(GBAKeyboard.START, 1));
		robot.delay(10);
		robot.keyRelease(kebo.getKey(GBAKeyboard.START, 1));
		robot.delay(BotConfig.getKeyDelay());
		robot.keyPress(kebo.getKey(GBAKeyboard.DOWN, 1));
		robot.delay(10);
		robot.keyRelease(kebo.getKey(GBAKeyboard.DOWN, 1));
		robot.delay(BotConfig.getKeyDelay());
		robot.keyPress(kebo.getKey(GBAKeyboard.BUTTONA, 1));
		robot.delay(10);
		robot.keyRelease(kebo.getKey(GBAKeyboard.BUTTONA, 1));
		robot.delay(BotConfig.getKeyDelay());
		robot.delay(BotConfig.getDelayBeforeScreenshot());
		
		return getScreenshot();
	}
	
	public String getScreenshot(){
		File file = new File("screenshot.jpg");
		Rectangle screenRect = new Rectangle(BotConfig.getScreenStartX(), BotConfig.getScreenStartY(), BotConfig.getScreenWidth(), BotConfig.getScreenHeight());
		try{
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			ImageIO.write(capture, "jpg", file);
		}catch(Exception ex){
			
		}
		System.out.println("Screenshot taken: "+file.getPath());
		
		return (file.getPath());
	}

}
