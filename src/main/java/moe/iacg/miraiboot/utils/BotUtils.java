package moe.iacg.miraiboot.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.enums.FileType;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class BotUtils extends BotPlugin {

    @Autowired
    BotContainer botContainer;

    /**
     * @param command
     * @param content
     * @return java.lang.String
     * @Description 删除命令字符
     * @author wlwang3
     * @date 2020/9/6
     */
    public static String removeCommandPrefix(String command, String content) {

        return content.replace("/" + command, "").trim();
    }

    public static <T> int sendMessage(Bot bot, T event, Msg msg) {

        if (event instanceof OnebotEvent.PrivateMessageEvent) {
            var eventPrivate = (OnebotEvent.PrivateMessageEvent) event;
            bot.sendPrivateMsg(eventPrivate.getUserId(), msg, false);
        }

        if (event instanceof OnebotEvent.GroupMessageEvent) {
            var eventGroup = (OnebotEvent.GroupMessageEvent) event;
            bot.sendGroupMsg(eventGroup.getGroupId(),
                    msg, false);
        }

        return BotPlugin.MESSAGE_BLOCK;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public Bot getFirstBot() {
        return botContainer.getBots().values().stream().findFirst().get();
    }

    public int sendGroupMsg(Long groupId, Msg msg) {
        getFirstBot().sendGroupMsg(groupId, msg, false);
        return BotPlugin.MESSAGE_BLOCK;
    }

    public String getFileSuffix(String url) {

        File image = FileUtil.touch("images/tmpImage");
        HttpUtil.downloadFile(url, image);

        byte[] byteType = new byte[3];
        BufferedInputStream fileInputStream = FileUtil.getInputStream(image);
        try {
            fileInputStream.read(byteType, 0, byteType.length);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        String hex = bytesToHexString(byteType).toUpperCase();

        String suffix = FileType.getSuffix(hex);

        return suffix;
    }

}
