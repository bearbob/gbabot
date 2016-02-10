# gbabot
A telegram bot to controll a GBA emulator (or SNES etc) with via chat commands.

# What it does
The bot is running on a seperate machine (preferably a virtual machine, but a server should also work if it can run an emulator) and polls the telegram api for new messages. When it receives one, the message is parsed for commands and these commands are executed. After that, a picture of the current screen is send back to the chat where the message was received.

# How to use it
Download the archive and edit the BotConfig.java to match your settings. Than start and enjoy.

# Moor detailz Plz
Register a new bot with the Telegram BotFather (https://core.telegram.org/bots/), open the BotConfig.java and edit the token, username and address to match your new bot. Then open your emulator and measure the coordinates of the upper left corner of the screen (this will give you startx and starty) and the lower right corner (endx and endy). If you only have the start coordinates and width and height - you can calculate the endx and endy, I'm sure. 

Go to your emulator keyboard settings and edit the configuration to either match the one given by the bot (see GBAKeyboard.java) or edit the settings in GBAKeyboard.java (setKeyMap()) to match your config. You might also want to edit the getSaveKeys()-methode, as there is set what buttons are to pressed to save the game.

Now start your game in the emulator, fire up the bot and start playing!

# Referenced libraries
Ruben Bermudez' TelegramBot: https://github.com/rubenlagus/TelegramBots
SQLite (Version 3.8.7 and higher)

# Commands
Call "/help" to see a list of commands.
The kill command will not be shown here, as it is supposed to be triggered only in a private chat by you, the owner of the bot, to take the bot down for maintainance. 

# Known problems
The bot seems to go idle and stop working if no message is send for a longer time. From time to time pictures are not send because the telegram api will refuse them. Normally this can be ignored and after a few minutes it will work again. 
