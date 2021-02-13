package moe.iacg.miraiboot.plugins;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.enums.Commands;
import moe.iacg.miraiboot.utils.RedisUtil;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Reread extends BotPlugin {

    @Autowired
    RedisUtil redisUtil;

    private static final String REREAD_RECORD_GROUP = "bot:rereadRecord:";
    private static final String REREAD_RECORDED_GROUP = "bot:rereadRecorded:";

    @Override

    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        Msg builder = Msg.builder();
        String rawMessage = event.getRawMessage();
        long groupId = event.getGroupId();

        List<String> allCommand = Arrays.stream(Commands.values()).map(Commands::getCommand).collect(Collectors.toList());
        boolean isCommand = allCommand.stream().anyMatch(rawMessage::contains);
        if (isCommand) {
            return MESSAGE_IGNORE;
        }

        String lastMessage = redisUtil.get(REREAD_RECORD_GROUP + groupId);
        String lastRereadMessage = redisUtil.get(REREAD_RECORDED_GROUP + groupId);

        boolean eqLastMsg = StringUtils.isNotBlank(lastMessage) && lastMessage.equals(rawMessage);
        if (StringUtils.isBlank(lastRereadMessage)) {
            lastRereadMessage = "null";
        }

        if (eqLastMsg && !lastRereadMessage.equals(rawMessage)) {
            builder.setMessageChain(event.getMessageList());
            redisUtil.set(REREAD_RECORDED_GROUP + groupId, rawMessage);
            redisUtil.expire(REREAD_RECORDED_GROUP + groupId, 1, TimeUnit.DAYS);
            builder.sendToGroup(bot, groupId);
        } else {
            redisUtil.set(REREAD_RECORD_GROUP + groupId, rawMessage);
            redisUtil.expire(REREAD_RECORD_GROUP + groupId, 1, TimeUnit.DAYS);

        }
        return MESSAGE_IGNORE;
    }
}
