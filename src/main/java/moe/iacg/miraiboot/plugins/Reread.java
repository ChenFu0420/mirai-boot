package moe.iacg.miraiboot.plugins;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.utils.RedisUtil;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Reread extends BotPlugin {

    @Autowired
    RedisUtil redisUtil;

    private static final String REREAD_RECORD_KEY = "bot:rereadRecord:";
    private static final String REREAD_RECORD_KEY_OTHER = "bot:rereadRecordOther:";

    @Override

    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        Msg builder = Msg.builder();
        String rawMessage = event.getRawMessage();
        long groupId = event.getGroupId();
        String lastMessage = redisUtil.get(REREAD_RECORD_KEY + groupId);
        String lastRereadMessage = redisUtil.get(REREAD_RECORD_KEY_OTHER + groupId);

        boolean eqLastMsg = StringUtils.isNotBlank(lastMessage) && lastMessage.equals(rawMessage);
        if (StringUtils.isBlank(lastRereadMessage)) {
            lastRereadMessage = "null";
        }

        if (eqLastMsg && !lastRereadMessage.equals(rawMessage)) {
            builder.setMessageChain(event.getMessageList());
            redisUtil.set(REREAD_RECORD_KEY_OTHER + groupId, rawMessage);

            builder.sendToGroup(bot, groupId);
        } else {
            redisUtil.set(REREAD_RECORD_KEY + groupId, rawMessage);
        }
        return MESSAGE_IGNORE;
    }
}
