package moe.iacg.miraiboot.plugin;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class DemoPlugin extends BotPlugin {

    @NacosValue(value = "${url:asdag}", autoRefreshed = true)
    private String useLocalCache;

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        long userId = event.getUserId();
        String useLocalCache = this.useLocalCache;
        Msg msg = Msg.builder()
                .face(1)
                .text("2")
                .text("hello2")
                .image("https://www.baidu.com/img/flexible/logo/pc/result@2.png");
        bot.sendPrivateMsg(userId, msg, false);
        return MESSAGE_BLOCK;
    }
}
