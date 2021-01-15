package moe.iacg.miraiboot.plugins;

import lombok.extern.log4j.Log4j2;
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
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        
        long groupId = event.getGroupId();

        return MESSAGE_BLOCK;
    }
}
