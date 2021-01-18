package moe.iacg.miraiboot.telegram;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


/**
 * @author Ghost
 */
@Component
public class AE86QQBot extends TelegramLongPollingBot {

    @NacosValue("${telegram.AE86QQBot.token}")
    private String token;
    @NacosValue("${telegram.AE86QQBot.username}")
    private String username;


    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
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

    @Override
    public void onUpdatesReceived(List<Update> updates) {

    }
}
