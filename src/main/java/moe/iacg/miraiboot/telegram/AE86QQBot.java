package moe.iacg.miraiboot.telegram;

import cn.hutool.core.io.FileUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.luciad.imageio.webp.WebPReadParam;
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
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
//        super(defaultBotOptions());
    }

    private static DefaultBotOptions defaultBotOptions() {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        defaultBotOptions.setProxyHost("127.0.0.1");
        defaultBotOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
        defaultBotOptions.setProxyPort(1080);
        return defaultBotOptions;
    }

    public void sendImageFromUrl(String url, Long chatId,String caption) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();

        // Set destination chat id
        sendPhotoRequest.setChatId(String.valueOf(chatId));
        // Set the photo url as a simple photo
        sendPhotoRequest.setPhoto(new InputFile(url));
        sendPhotoRequest.setCaption(caption);
        try {
            // Execute the method
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public Map<Long, Long> tgGroupIdByGroupId() {
        String[] split = tgGroupByQQGroup.split(",");
        Map<Long, Long> tgGroupIdByGroupId = new HashMap<>();
        tgGroupIdByGroupId.put(Long.valueOf(split[0]), Long.valueOf(split[1]));
        return tgGroupIdByGroupId;
    }

    public java.io.File downloadPhotoByFilePath(String filePath) {
        try {
            // Download the file calling AbsSender::downloadFile method
            return downloadFile(filePath);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }

        return null;
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
                User user = updateMessage.getFrom();
                msg.text(user.getUserName() + "：");

                if (updateMessage.getChatId().equals(tgGroupIdByGroupId.getKey())) {
                    if (updateMessage.hasSticker()) {
                        String fileId = updateMessage.getSticker().getFileId();
                        GetFile getFile = new GetFile();
                        getFile.setFileId(fileId);
                        File execute = execute(getFile);

                        java.io.File webpFile = downloadPhotoByFilePath(execute.getFilePath());

                        // Obtain a WebP ImageReader instance
                        ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();


                        reader.setInput(new FileImageInputStream(webpFile));

                        // Configure decoding parameters
                        WebPReadParam readParam = new WebPReadParam();
                        readParam.setBypassFiltering(true);

                        // Configure the input on the ImageReader

                        // Decode the image
                        BufferedImage image = reader.read(0, readParam);
                        java.io.File file ;
                        try {
                             file = new java.io.File("image/"+fileId + ".png");

                            ImageIO.write(image, "png", file);
                        } catch (IOException e) {
                             file = new java.io.File(fileId + ".gif");
                            ImageIO.write(image, "gif", file);

                        }

                        log.info(FileUtil.getMimeType(file.getPath()));
//                        msg.image(fileUrl);
                        msg.image(file.getPath());
                        botUtils.sendGroupMsg(tgGroupIdByGroupId.getValue(), msg);
                    }
                    if (updateMessage.hasText()) {
                        msg.text(updateMessage.getText());
                        botUtils.sendGroupMsg(tgGroupIdByGroupId.getValue(), msg);

                    }
                    if (updateMessage.hasPhoto()) {

                        for (PhotoSize photoSize : updateMessage.getPhoto()) {
                            GetFile getFile = new GetFile();
                            getFile.setFileId(photoSize.getFileId());
                            File execute = execute(getFile);

                            String fileUrl = execute.getFileUrl(getBotToken());
                            msg.image(fileUrl);
                        }
                        botUtils.sendGroupMsg(tgGroupIdByGroupId.getValue(), msg);
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

        }
    }

    public void sendTextMessage(String text, Long chatId) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }


}
