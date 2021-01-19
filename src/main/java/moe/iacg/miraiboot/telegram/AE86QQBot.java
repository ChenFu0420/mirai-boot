package moe.iacg.miraiboot.telegram;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


/**
 * @author Ghost
 */
//@Component
public class AE86QQBot extends TelegramLongPollingBot {

    private static boolean hookCalled = false;
    //    @NacosValue("${telegram.AE86QQBot.token}")
    private String token;
    //    @NacosValue("${telegram.AE86QQBot.username}")
    private String username;

    AE86QQBot() {
        super(defaultBotOptions());
    }

    private static DefaultBotOptions defaultBotOptions() {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        defaultBotOptions.setProxyHost("127.0.0.1");
        defaultBotOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
        defaultBotOptions.setProxyPort(1080);
        return defaultBotOptions;
    }

    @Override
    public String getBotUsername() {
        return token;
    }

    @Override
    public String getBotToken() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText(update.getMessage().getText());

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


}