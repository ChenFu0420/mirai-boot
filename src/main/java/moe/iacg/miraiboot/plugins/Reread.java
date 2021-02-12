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

    private static final String REREAD_RECORD_KEY = "bot:rereadRecord";
    private static final String REREAD_RECORD_KEY_OTHER = "bot:rereadRecordOther";

    @Override

    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        Msg builder = Msg.builder();
        String rawMessage = event.getRawMessage();

        String lastMessage = redisUtil.get(REREAD_RECORD_KEY);
        String lastRereadMessage = redisUtil.get(REREAD_RECORD_KEY_OTHER);

        boolean eqLastMsg = StringUtils.isNotBlank(lastMessage) && lastMessage.equals(rawMessage);
        boolean eqLastRereadMsg = StringUtils.isNotBlank(lastMessage) &&!lastRereadMessage.equals(rawMessage);


        if ((eqLastMsg && eqLastRereadMsg)) {
            builder.setMessageChain(event.getMessageList());
            redisUtil.set(REREAD_RECORD_KEY_OTHER, rawMessage);

            builder.sendToGroup(bot, event.getGroupId());
        } else {
            redisUtil.set(REREAD_RECORD_KEY, rawMessage);
        }
        return MESSAGE_IGNORE;
    }
}
