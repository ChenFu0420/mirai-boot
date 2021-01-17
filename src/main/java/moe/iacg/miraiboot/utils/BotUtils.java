package moe.iacg.miraiboot.utils;

import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.springframework.stereotype.Component;

@Component
public class BotUtils extends BotPlugin {
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

}
