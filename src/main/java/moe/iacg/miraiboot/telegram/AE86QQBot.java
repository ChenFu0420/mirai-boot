package moe.iacg.miraiboot.telegram;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.luciad.imageio.webp.WebPReadParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
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

    AE86QQBot(@Autowired DefaultBotOptions defaultBotOptions) {
        super(defaultBotOptions);
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
                msg.text(user.getFirstName() + " " + user.getLastName() + "(" + updateMessage.getMessageId() + ")：\n");

                if (updateMessage.getChatId().equals(tgGroupIdByGroupId.getKey())) {
                    if (updateMessage.hasSticker()) {
                        String fileId = updateMessage.getSticker().getFileId();
                        GetFile getFile = new GetFile();
                        getFile.setFileId(fileId);
                        File execute = execute(getFile);
                        if (execute.getFilePath().contains(".tgs")) {
                            //TODO 解析tgs
                            return;
                        }
                        java.io.File webpFile = downloadPhotoByFilePath(execute.getFilePath());
                        ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();
                        reader.setInput(new FileImageInputStream(webpFile));
                        WebPReadParam readParam = new WebPReadParam();
                        readParam.setBypassFiltering(true);
                        BufferedImage image = reader.read(0, readParam);
                        java.io.File file = FileUtil.touch("images/" + fileId + ".png");
                        ImageIO.write(image, "png", file);
                        msg.image(file.getPath());
                        botUtils.sendGroupMsg(tgGroupIdByGroupId.getValue(), msg);
                    }

                    if (updateMessage.hasText()) {

                        if (updateMessage.isReply()) {
                            String text = updateMessage.getReplyToMessage().getText();
                            String messageId = ReUtil.get("\\((.*)\\)：\n", text, 1);
                            if (StringUtils.isNotBlank(messageId)) {
                                msg.reply(Integer.parseInt(messageId));
                            }
                        }

                        msg.text(updateMessage.getText());
                        botUtils.sendGroupMsg(tgGroupIdByGroupId.getValue(), msg);

                    }

                    if (updateMessage.hasPhoto()) {
                        List<PhotoSize> photos = updateMessage.getPhoto();
                        PhotoSize photo = photos.get(photos.size() - 1);
                        GetFile getFile = new GetFile();
                        getFile.setFileId(photo.getFileId());
                        File execute = execute(getFile);

                        String fileUrl = execute.getFileUrl(getBotToken());
                        msg.image(fileUrl);
                        botUtils.sendGroupMsg(tgGroupIdByGroupId.getValue(), msg);
                    }


                    if (updateMessage.hasAnimation()) {
                        Animation animation = updateMessage.getAnimation();
                        GetFile getFile = new GetFile();
                        getFile.setFileId(animation.getFileId());
                        File execute = execute(getFile);
                        String fileUrl = execute.getFileUrl(getBotToken());
                        msg.video(fileUrl, "http://ww1.sinaimg.cn/large/be659b6cly1gn3f0wmthdj208w06ot9b.jpg", true);
                        botUtils.sendGroupMsg(tgGroupIdByGroupId.getValue(), msg);
                    }
                }
            }
        }
    }

    public void sendImageFromUrl(String url, Long chatId, String caption) {
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
            log.error(e.getMessage(), e);
        }
    }

    public void sendAnimationForUrl(String url, Long chatId, String caption) {
        SendAnimation sendAnimation = new SendAnimation();
        sendAnimation.setCaption(caption);
        sendAnimation.setChatId(String.valueOf(chatId));
        sendAnimation.setAnimation(new InputFile(url));
        try {
            // Execute the method
            execute(sendAnimation);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
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


    public void sendTextMessage(String text, Long chatId, Integer messageId) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setReplyToMessageId(messageId);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sendTextMessage(String text, Long chatId) {
        sendTextMessage(text, chatId, null);
    }
}
