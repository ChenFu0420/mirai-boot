package moe.iacg.miraiboot.plugins;

import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.constants.Commands;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.boot.BotProperties;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@CommandPrefix(command = Commands.HELP)
public class Help extends BotPlugin {

    @Autowired
    BotProperties botProperties;

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        return BotUtils.sendMessage(bot, event, help());
    }

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        return BotUtils.sendMessage(bot, event, help());
    }


    private Msg help() {

        Msg builder = Msg.builder();
        List<Class<? extends BotPlugin>> onPluginList = botProperties.getPluginList();
        for (Class<? extends BotPlugin> aClass : onPluginList) {
            CommandPrefix commandPrefix = aClass.getAnnotation(CommandPrefix.class);
            if (commandPrefix == null) {
                continue;
            }
            Commands command = commandPrefix.command();
            builder.text(commandPrefix.prefix()).text(command.getCommand()).text("：")
                    .text(command.getDescription()).text("\n");
        }
        return builder;
    }


}
