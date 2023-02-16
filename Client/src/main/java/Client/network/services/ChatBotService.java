package Client.network.services;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

import java.util.Locale;
import java.util.Scanner;

public class ChatBotService {
    ChatterBotFactory factory;
    ChatterBot bot;
    ChatterBotSession botSession;

    public ChatBotService() {
        try {
            this.factory = new ChatterBotFactory();
            this.bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            this.botSession = bot.createSession(Locale.ENGLISH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessageFromBot(String message) {
        try {
            return botSession.think(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error Message";
    }
}
