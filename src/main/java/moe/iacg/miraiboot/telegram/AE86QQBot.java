package moe.iacg.miraiboot.telegram;

import cn.hutool.core.io.FileUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Ghost
 */
@Component
@Slf4j
public class AE86QQBot extends TelegramLongPollingBot {

    @NacosValue("${telegram.AE86QQBot.token}")
    private String token;
    @NacosValue("${telegram.AE86QQBot.username}")
    private String username;
    @NacosValue("${telegramGroup.by.qqGroup:-1001482310527,199324349}")
    private String tgGroupByQQGroup;

    @Autowired
    private BotUtils botUtils;




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

    private Map<Long, Long> tgGroupIdByGroupId() {
        String[] split = tgGroupByQQGroup.split(",");
        Map<Long, Long> tgGroupIdByGroupId = new HashMap<>();
        tgGroupIdByGroupId.put(Long.valueOf(split[0]), Long.valueOf(split[1]));
        return tgGroupIdByGroupId;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        Message updateMessage = update.getMessage();
        if (update.hasMessage()) {

            for (var tgGroupIdByGroupId : tgGroupIdByGroupId().entrySet()) {

                Msg msg = Msg.builder();
                if (updateMessage.getChatId().equals(tgGroupIdByGroupId.getKey())) {
                    if (updateMessage.hasSticker()) {
                        String fileId = updateMessage.getSticker().getFileId();
                        GetFile getFile = new GetFile();
                        getFile.setFileId(fileId);
                        FileUtil.file( execute(getFile).getFilePath());
//                        sendSticker.getSticker();
                        botUtils.sendGroupMsg(tgGroupIdByGroupId.getValue(),msg);
                    }
                    if (updateMessage.hasText()) {

                    }
                    if (updateMessage.hasPhoto()) {

                    }
                    if (updateMessage.hasAnimation()) {

                    }
                }


            }


        }


        // We check if the update has a message and the message has text
        if (update.hasMessage() && updateMessage.hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(String.valueOf(updateMessage.getChatId()));
            message.setText(updateMessage.getText());
//            try {
//
//                executeAsync(message); // Call method to send the message
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
        }
    }


}
