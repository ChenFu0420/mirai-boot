package moe.iacg.miraiboot.plugins;

import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.enums.Commands;
import moe.iacg.miraiboot.utils.BotUtils;
import moe.iacg.miraiboot.utils.RCONUtils;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@CommandPrefix(command = Commands.RCON)
public class Rcon extends BotPlugin {

    @Autowired
    RCONUtils rconUtils;
    @Autowired
    BotUtils botUtils;

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        return botUtils.sendMessage(bot, event, retRCONCommand(event.getUserId(), event.getRawMessage()));
    }

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        return botUtils.sendMessage(bot, event, retRCONCommand(event.getUserId(), event.getRawMessage()));
    }

    private Msg retRCONCommand(Long qq, String rawMessage) {
        Msg builder = Msg.builder();
        if (!botUtils.isBotAdmin(qq)) {
            return builder.text("您无权执行RCON命令");
        }
        String content = BotUtils.removeCommandPrefix(Commands.RCON.getCommand(), rawMessage);

        String result = rconUtils.send(content);
        builder.text(result);
        return builder;
    }
}
