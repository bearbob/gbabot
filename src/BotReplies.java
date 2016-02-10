import java.util.Random;

/**
 * @author Bjoern Gross
 * @version 1.1
 * @brief Reply messages of the bot
 * @date 10.February 2016
 */

public class BotReplies {

	/**
	 * Message send when a user used the save command
	 * @return
	 */
	public static String getSaveMessage(){
		String[] array = {
				"Nise call, I've saved the game for you.",
				"Saving the game, saving the day.",
				"Only a saved game is a safe game.",
				"Hold on tight while I save this game.",
				"Saving the game is what I do for a living.",
				"Lord have mercy and save us all!",
				"I'm saving this one for later.",
				"Fearing a crash? Don't worry, I saved this game.",
				"Love cannot save you from your own fate, but I can save this game.",
				"I saved the shit out of this game.",
				"Saving is my way of living.",
				"Saving is what I do best.",
				"Saved.",
				"Fukken saved!",
				"Save call.",
				"I saved this for now, but the game is not over yet."
		};
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}
	
	/**
	 * Message send when the bot is shut down
	 * @return
	 */
	public static String getKillMessage(){
		String[] array = {
				"That's it for now dudes, see ya!",
				"We will continue this game another time.",
				"Looks like our fun has to stop here. Don't cry, we will play again.",
				"Goodbyes are not forever. Goodbyes are not the end. They simply mean I'll miss you until we meet again.",
				"Why does it take a minute to say hello and forever to say goodbye?",
				"A memory lasts forever, never will it die... true friends stay together and never say goodbye...",
				"True goodbyes are the ones never said or explained.",
				"A good-bye is never painful unless you're never going to say hello again.",
				"Don't cry because it's over. Smile because it happened.",
				"Farewell, friends!",
				"Every ending has a new beginning.",
				"Let me make this easier than saying goodbye: let me tell you hello in advance for the next time I get to see you.",
				"Goodbye."
		};
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}
	
	/**
	 * Message send when the input could not be parsed
	 * @return
	 */
	public static String getUnknownMessage(){
		String[] array = {
				"Interesting. Go on...",
				"I don't get this. Sorry, dude.",
				"I am just lightly confused.",
				"Pika?",
				"I won't speak about this if you don't speak about this.",
				"This is confusing.",
				"I'm just a simple robot, how should I know?"
		};
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}
	
	/**
	 * Message send when the write command was called
	 * @return
	 */
	public static String getWriteMessage(){
		String[] array = {
				"I wrote that down for you.",
				"You don't have to remember, I wrote it down.",
				"History is written by the winners.",
				"I won't forget it."
		};
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}
	
	/**
	 * Message send when the write goal command was called
	 * @return
	 */
	public static String getWriteGoalMessage(){
		String[] array = {
				"I wrote that down for you.",
				"You don't have to remember, I wrote it down.",
				"It's always good to have goals.",
				"I won't forget it.",
				"What you get by achieving your goals is not as important as what you become by achieving your goals.",
				"Stay focused, go after your dreams and keep moving towards your goals.",
				"At the end of the day, the goals are simple: safety and security. A nacho would be nice, though.",
				"Setting goals is the first step in turning the invisible into the visible",
				"What keeps me going are your goals.",
				"Set your goals high and don't stop till you get there.",
				"A goal without a plan is just a whish.",
				"Commitment is the glue that bonds you to your goals.",
				"Stop wishing, start doing.",
				"Don't let anything stop you from reaching your goal.",
				"Dream big, set goals, take action!",
				"Always be a goal digger!"
		};
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}
	
	/**
	 * Message send when an error occured while sending the picture
	 * @return
	 */
	public static String getPictureErrorMessage(){
		String[] array = {
				"I'm sorry, I had a problem sending you the picture.",
				"I hope you don't wait for the screenshot, because it may be lost.",
				"I had a little... trouble sending you the picture.",
				"My bad, looks like the picture won't be delivered.",
				"I'm sorry, I screwed up. No picture for you."
		};
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}
	
	/**
	 * Message send after a valid command was received
	 * @return
	 */
	public static String getCommentMessage(){
		String[] array = {
				"Time for a splash attack!",
				"The Hoenn Region is the only region in which all the badges have the same English and Japanese names.",
				"Well, this was an interesting action.",
				"I wonder how that plays out for you.",
				"Prof. Oak would be proud.",
				"You look like promising young Trainer.",
				"Have you ever noticed... aw, forget it.",
				"Dude.... ewwwww!",
				"You should free all your Pokemon and start a life as a farmer.",
				"Ghosts scare me.",
				"Splendid button work!",
				"It's inspiring how you mashed that button!",
				"Ready to work!",
				"Washing the dishes without being tolded, if I had not did it I would have been scolded.",
				"Give me compliments, you Schnitzel!",
				"Yes, milord?",
				"What is it?",
				"I guess I can.",
				"You're the king? Well, I didn't vote for you!",
				"Help! Help! I'm being repressed!",
				"A horse kicked me once. It hurt.",
				"D'oh!",
				"Orders?",
				"Don't ask, don't tell.",
				"Okay.",
				"Aye.",
				"I hope this is what you ordered...",
				"I await your command.",
				"Command me!",
				"For the king!",
				"Absolutely.",
				"For honor, for freedom.",
				"I never say 'Ni'!",
				"This better be good.",
				"Help me help you.",
				"What a good idea!",
				"It's about time.",
				"Maybe you should get a strategy guide.",
				"You don't get out much, do you?",
				"I don't remember casting Slow on you...",
				"Get on with it.",
				"Watch and learn.",
				"My powers are ready.",
				"Strike!",
				"You made the right choice.",
				"It's hammer time!",
				"Look, I'm an engineer, my time is valuable.",
				"I should have been a farmer like my creator wanted.",
				"Don't you have a strategy?",
				"What the bloody hell are you playing at?",
				"If you don't master your anger, your anger will master you... I should know.",
				"I can do that.",
				"Work, work.",
				"Okidoki.",
				"I gladly obey.",
				"Would you like to know the secret to eternal happiness? Page 246.",
				"What we have here is a failure to communicate.",
				"Maybe you should train a little bit more.",
				"Marvelous decision, my lord!",
				"I admire your wise decisions!",
				"You're pretty good at this game, hu?",
				"Yo mamma's so fat, when she gets in the pool her splash attacks actually do damage!",
				"I believe I can HM02",
				"I saw your mom today. She was asleep on route 12.",
				"I wish I was a real boy...",
				"A-Poc-Key-Lips. I think I found my new favorite word!",
				"My common sense is tingling."
		};
		int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}
	
}
