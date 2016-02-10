import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

public class Main {
	public static final String VERSION = "1.52"; 
	public static void main(String[] args) {
		System.out.println("GBABot started. Version "+VERSION);

		//TelegramBot:
		//https://github.com/rubenlagus/
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new DewdeHandler());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

	}
	
}
