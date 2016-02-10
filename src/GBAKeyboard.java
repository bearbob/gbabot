import java.awt.event.KeyEvent;
import java.util.HashMap;

public class GBAKeyboard extends Keyboard {
	public static final int UP = 0;
	public static final int RIGHT =1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int BUTTONA = 4;
	public static final int BUTTONB = 5;
	public static final int START = 6;
	public static final int SELECT = 7;
	public static final int SAVE = 99;
	public static final int STATUS = 100;
	
	private HashMap<Integer, Integer> keyMap;
	
	public GBAKeyboard(){
		setKeyMap();		
	}
	
	/**
	 * This determines the key settings for the emulator.
	 * Edit this, if you want to use another configuration
	 */
	private void setKeyMap(){
		keyMap = new HashMap<Integer, Integer>();
		keyMap.put(UP, KeyEvent.VK_UP);
		keyMap.put(RIGHT, KeyEvent.VK_RIGHT);
		keyMap.put(LEFT, KeyEvent.VK_LEFT);
		keyMap.put(DOWN, KeyEvent.VK_DOWN);
		keyMap.put(BUTTONA, KeyEvent.VK_H);
		keyMap.put(BUTTONB, KeyEvent.VK_J);
		keyMap.put(START, KeyEvent.VK_M);
		keyMap.put(SELECT, KeyEvent.VK_N);
	}
	
	/**
	 * Returns the KeyEvent matching the button. 
	 * Default is 'A'
	 * @param button
	 * @return
	 */
	public int getKey(int button, int player){
		return getKey(button);
	}
	
	public int getKey(int button){
		if(keyMap.containsKey(button)){
			return keyMap.get(button);
		}
		return keyMap.get(BUTTONA);
	}
	
	public String nameKey(int button){
		switch(button){
		case UP: return "Up";
		case DOWN: return "Down";
		case LEFT: return "Left";
		case RIGHT: return "Right";
		case BUTTONA: return "A";
		case BUTTONB: return "B";
		case SELECT: return "Select";
		case START: return "Start";
		default: return "DefaultButton";
		}
	}
	
	/**
	 * Returns the buttons to simultaneously press and release to save the game.
	 * For a single button, return an array with a single element, for more keys just add the 
	 * keys to the array. The default scheme is "F1+SHIFT"
	 */
	public int[] getSaveKeys(){
		return new int[]{
				KeyEvent.VK_SHIFT, 
				KeyEvent.VK_F1
				};
	}
}
