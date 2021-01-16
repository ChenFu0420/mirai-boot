package moe.iacg.miraiboot.plugins;

import lombok.extern.log4j.Log4j2;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.enums.MessageType;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @author Ghost
 */
@Log4j2
@Component
public class SeTu extends BotPlugin {
    @Override
    @CommandPrefix(command = "/setu")
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        long groupId = event.getGroupId();

        return MESSAGE_BLOCK;
    }
    @CommandPrefix(command = "/setu")
    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        bot.sendPrivateMsg(event.getUserId(),event.getRawMessage(),true);
        return MESSAGE_BLOCK;
    }
}
