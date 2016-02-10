import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.methods.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

/**
 * @author Bjoern Gross
 * @version 1.4
 * @brief The core of the bot
 * @date 10.February 2016
 */

public class DewdeHandler extends TelegramLongPollingBot {
	private Commander robot;
	/**
	 * Database for statistical purposes
	 */
	private StatisticDB statistics;
	/**
	 * A list of chat ids of all chats the bot has received messages from in this session
	 */
	private List<Long> chatSessions;
	private File history;
	private File goal;
	
	public DewdeHandler() {
		super();
		chatSessions = new ArrayList<Long>();
		try{
			GBAKeyboard gba = new GBAKeyboard();
			robot = new Commander(gba);
		}catch(AWTException awte){
			
		}
		
		statistics = new StatisticDB();
		
		try{
			history = new File(BotConfig.getHistoryName());
			history.createNewFile();
			goal = new File(BotConfig.getGoalName());
			goal.createNewFile();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	@Override
	public String getBotUsername() {
		return BotConfig.USERNAME;
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() || message.hasLocation()) {
                handleIncomingMessage(message);
            }
        }
		
	}

	@Override
	public String getBotToken() {
		return BotConfig.TOKEN;
	}

	
	private void handleIncomingMessage(Message message) {
        System.out.println("Received a message from "+message.getFrom().getUserName()+": "+message.getText());
		
        //check if the message was send more than five minutes ago and if so, ignore it
        if(message.getDate() < (System.currentTimeMillis()/1000)- (BotConfig.getTrashThreshold())){
        	System.out.println("Ignored the message because it arrived more than "+(BotConfig.getTrashThreshold()/60)+" minutes ago.");
        	return;
        }
        
        logMessage(message);
        //initialize the sendMessageRequest with a default value that will be overwritten
        SendMessage sendMessageRequest = messageOnMainMenu(message);
        String[] m = message.getText().split(" ");
        int command = getCommand(m[0]);
        SendPhoto sendPhoto = new SendPhoto();
        switch(command){
        case GBAKeyboard.BUTTONA: case GBAKeyboard.BUTTONB:
        case GBAKeyboard.UP: case GBAKeyboard.DOWN:
        case GBAKeyboard.LEFT: case GBAKeyboard.RIGHT:
        case GBAKeyboard.SELECT: case GBAKeyboard.START:
        	int times = 1;
        	try{
        		times = Integer.parseInt(m[1]);
        		System.out.println("Times="+times);
        	}catch(ArrayIndexOutOfBoundsException ae){
        		//simply no second element in the array, so no times value given. Ignore this.
        	}catch(Exception e){
        		System.out.println("Problem parsing the times value for "+message.getText());
        	}
        	String photo = robot.input(command, times, 1); 	
        	sendPhoto.setChatId(message.getChatId().toString());
        	sendPhoto.setNewPhoto(photo, "screenshot.jpg");
        	try {
        		sendPhoto(sendPhoto);
        	} catch (TelegramApiException e) {
                e.printStackTrace();
                try{
                	//most likely the image was not send, send an excuse
                	sendMessageRequest = createMessage(message, BotReplies.getPictureErrorMessage());
                	sendMessageRequest.setReplayToMessageId(message.getMessageId());
                	sendMessage(sendMessageRequest);
            	}catch( Exception ex){
            		ex.printStackTrace();
            	}
        	} catch (Exception e) {
            	e.printStackTrace();
            }
        	//these commands cannot be packed together as the sendPhoto produces an exception most of the time but is still send
        	try{
        		//with a low chance add a comment to the picture
        		int rnd = new Random().nextInt(100);
        		if(rnd < 16){
        			sendMessageRequest = createMessage(message, BotReplies.getCommentMessage());
                	sendMessage(sendMessageRequest);	
        		}
        	}catch( Exception e){
        		e.printStackTrace();
        	}
        	return;
        case GBAKeyboard.STATUS:
        	sendPhoto.setChatId(message.getChatId().toString());
        	sendPhoto.setNewPhoto(robot.getScreenshot(), "screenshot.jpg");
        	try {
                sendPhoto(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        	return;
        case GBAKeyboard.SAVE:
        	robot.saveGame();
        	System.out.println("Saved the game");
        	sendMessageRequest = createMessage(message, BotReplies.getSaveMessage());
        	try {
                sendMessage(sendMessageRequest);
            } catch (TelegramApiException e) {
            	e.printStackTrace();
            }
        	return;
        }
        
        //@TODO: Not working at the moment, the key input is to fast
        if(isEqual(m[0], new String[]{"/pokemon", "pokemon", "/team", "/poke"})){
        	sendPhoto.setChatId(message.getChatId().toString());
        	sendPhoto.setNewPhoto(robot.showPokemon(), "screenshot.jpg");
        	try {
                sendPhoto(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        	return; 
		}else if(isEqual(m[0], new String[]{"/write", "write"})){
    		writeHistory(m);
    		sendMessageRequest = createMessage(message, BotReplies.getWriteMessage()); 
		}else if(isEqual(m[0], new String[]{"/history", "history", "/log", "log"})){
    		sendMessageRequest = createMessage(message, readHistory()); 
		}else if(isEqual(m[0], new String[]{"/goal"})){
    		if(m.length > 1){
    			writeGoal(m);
    			sendMessageRequest = createMessage(message, BotReplies.getWriteGoalMessage());
    		}else{
    			sendMessageRequest = createMessage(message, readGoal());
    		} 
		}else if(isEqual(m[0], new String[]{"/"+BotConfig.getKillCommand(), BotConfig.getKillCommand()})){
        	for(long cid : chatSessions){
        		sendMessageRequest = messageOnKill(cid);
            	try {
            		sendMessage(sendMessageRequest);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
        	}
        	this.exitGame();
        }else if(isEqual(m[0], new String[]{"/help", "help"})){
        	StringBuilder helptext = new StringBuilder("Hey, looks like your looking for help! Here's what you can do:\n");
        	helptext.append("/up, /down, /left, /right trigger the move buttons, or use the shortcuts /u, /d, ...\n");
        	helptext.append("/a, /b, /start, /select trigger the respective buttons\n");
        	helptext.append("To press a button multiple times, send the button code and a number between 1 and 20 after it, e.g. '/a 17'.");
        	helptext.append("/status to request a new screenshot\n");
        	helptext.append("/save to save the game.\n");
        	helptext.append("/poke to show your team (only works outside combat and dialogue), only works with pokemon games.\n");
        	helptext.append("With /write + text you can save a line to the log file, which can be read with /log\n");
        	helptext.append("Use /goal + text to set a new goal or /goal to read the current goal.\n");
        	sendMessageRequest = createMessage(message, helptext.toString());
        }
        	
        ReplyKeyboardMarkup reply = new ReplyKeyboardMarkup();
        reply.setKeyboard(getKeyboard());
        sendMessageRequest.setReplayMarkup(reply);
        try {
        	sendMessage(sendMessageRequest);
        } catch (TelegramApiException e) {
        	e.printStackTrace();
        }
    }
	
	private static List<List<String>> getKeyboard(){
		List<List<String>> keyboard = new ArrayList<>();
        List<String> row = new ArrayList<>();
        row.add("/start");
        row.add("/up");
        row.add("/b");
        keyboard.add(row);
        row = new ArrayList<>();
        row.add("/left");
        row.add("/a");
        row.add("/right");
        keyboard.add(row);
        row = new ArrayList<>();
        row.add("/select");
        row.add("/down");
        row.add("/save");
        keyboard.add(row);
        
        return keyboard;
	}
	
	private static SendMessage messageOnMainMenu(Message message) {
		SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplayToMessageId(message.getMessageId());
        sendMessage.setText(BotReplies.getUnknownMessage());

        return sendMessage;
    }
	
	private static SendMessage createMessage(Message message, String text) {
		SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        return sendMessage;
    }
	
	private static SendMessage messageOnKill(Long chatId) {
		SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);

        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(BotReplies.getKillMessage());

        return sendMessage;

    }
	
	/**
	 * Returns true if the given original is equal to one of the strings in compare
	 * @param original
	 * @param compare
	 * @return
	 */
	private static boolean isEqual(String original, String[] compare){
		String template = original.toLowerCase();
		for(String s : compare){
			if(template.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
	private static int getCommand(String message){
		System.out.println("Compare for "+message);
		if(isEqual(message, new String[]{"/up", "up", "/u", "u"})){
        	return GBAKeyboard.UP;
        }
		if(isEqual(message, new String[]{"/down", "down", "/d", "d"})){
        	return GBAKeyboard.DOWN;
        }
		if(isEqual(message, new String[]{"/left", "left", "/l", "l"})){
        	return GBAKeyboard.LEFT;
        }
		if(isEqual(message, new String[]{"/right", "right", "/r", "r"})){
        	return GBAKeyboard.RIGHT;
        }
		if(isEqual(message, new String[]{"/a", "a"})){
        	return GBAKeyboard.BUTTONA;
        }
		if(isEqual(message, new String[]{"/b", "b"})){
        	return GBAKeyboard.BUTTONB;
        }
		if(message.equalsIgnoreCase("start") || message.equalsIgnoreCase("/start")){
        	return GBAKeyboard.START;
        }
		if(message.equalsIgnoreCase("select") || message.equalsIgnoreCase("/select")){
        	return GBAKeyboard.SELECT;
        }
		if(message.equalsIgnoreCase("save") || message.equalsIgnoreCase("/save")){
        	return GBAKeyboard.SAVE;
        }
		if(message.equalsIgnoreCase("status") || message.equalsIgnoreCase("/status")){
        	return GBAKeyboard.STATUS;
        }
		return -1;
	}
	
	private void writeHistory(String[] messageArray){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date date = new Date();
		String line = dateFormat.format(date)+ " - ";
		for(int i = 1; i<messageArray.length; i++){
			line += messageArray[i]+" ";
		}
		List<String> lines = Arrays.asList(line);
		try{
			Files.write(history.toPath(), lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String readHistory(){
		String out = "I can't remember anything.";
		try{
			out = new String(Files.readAllBytes(history.toPath()));
		}catch(Exception e){
			e.printStackTrace();
		}	
		return out;
	}
	
	/**
	 * Concatenates all but the first elements of the array into a string and saves it to a file
	 * @param messageArray
	 */
	private void writeGoal(String[] messageArray){
		DateFormat dateFormat = new SimpleDateFormat("dd.MM HH:mm");
		Date date = new Date();
		String line = dateFormat.format(date)+ " - ";
		for(int i = 1; i<messageArray.length; i++){
			line += messageArray[i]+" ";
		}
		List<String> lines = Arrays.asList(line);
		try{
			Files.write(goal.toPath(), lines, Charset.forName("UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String readGoal(){
		String out = "I don't remember anything about a goal, sorry.";
		try{
			out = new String(Files.readAllBytes(goal.toPath()));
			if(out.isEmpty()){
				out = "I might have forgotten your goal, or you never told me.";
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
		return out;
	}
	
	private void exitGame(){
		statistics.close();
		System.out.println("Received kill command, shutting down.");
		System.exit(0);
	}
	
	private void logMessage(Message message){
		//log the message in the database
		statistics.insertMessage(message.getFrom().getUserName(), message.getFrom().getId(), message.getText(), message.getDate());
		//save the chatid for later to inform all active chats that the
        //session has ended
        if(!chatSessions.contains(message.getChatId())){
        	chatSessions.add(message.getChatId());
        }
	}
}
