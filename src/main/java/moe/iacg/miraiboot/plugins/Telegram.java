package moe.iacg.miraiboot.plugins;

import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.telegram.AE86QQBot;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class Telegram extends BotPlugin {
    @Autowired
    AE86QQBot ae86QQBot;

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        for (Map.Entry<Long, Long> tgGroupAndQQGroup : ae86QQBot.tgGroupIdByGroupId().entrySet()) {

            if (event.getGroupId() == tgGroupAndQQGroup.getValue()) {
                OnebotEvent.GroupMessageEvent.Sender sender = event.getSender();
                String senderTitle = sender.getNickname() + "(" + sender.getUserId() + ")" + "：\n";
                for (OnebotBase.Message message : event.getMessageList()) {
                    if (message.getType().equals("image")) {
                        ae86QQBot.sendImageFromUrl(senderTitle
                                + message.getDataOrThrow("url"), tgGroupAndQQGroup.getKey(), senderTitle);
                    }
                    if (message.getType().equals("text")) {
                        ae86QQBot.sendTextMessage(senderTitle + message.getDataOrThrow("text"), tgGroupAndQQGroup.getKey());
                    }
                }
            }
        }
        return MESSAGE_IGNORE;
    }
}
