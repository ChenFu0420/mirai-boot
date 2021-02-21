package moe.iacg.miraiboot.plugins;

import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.enums.Commands;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@CommandPrefix(command = Commands.WATCH)
public class Watch extends BotPlugin {

    @Autowired
   private BotUtils botUtils;
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        Msg builder = Msg.builder();
        builder.xml(1,"");

       return botUtils.sendMessage(bot,event,builder);
    }
}
