package moe.iacg.miraiboot.plugins;

import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.constants.Commands;
import moe.iacg.miraiboot.telegram.AE86QQBot;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QQTelegramBot extends BotPlugin {

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        return 0;
    }
}
