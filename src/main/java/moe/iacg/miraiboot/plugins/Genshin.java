package moe.iacg.miraiboot.plugins;

import cn.hutool.core.collection.CollectionUtil;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Slf4j
@Component
@CommandPrefix(command = Commands.YUANSHEN)
public class Genshin extends BotPlugin {

    @Autowired
    BotUtils botUtils;
    private static final String SP = "sp";

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        return botUtils.sendMessage(bot, event, genshin(event));
    }


    private Msg genshin(OnebotEvent.GroupMessageEvent event) {
        String preCommand = BotUtils.removeCommandPrefix(Commands.YUANSHEN.getCommand(), event.getRawMessage());
        List<String> preCommandList = Arrays.asList(preCommand.split(" "));

        switch (preCommandList.get(0)) {
            case SP:
                return SP(preCommandList);
            default:
                return Msg.builder();
        }


    }

    private Msg SP(List<String> preCommandList) {

        Msg msg = Msg.builder();

        List<String> spList = Arrays.asList(preCommandList.get(1).split("-"));
        if (CollectionUtil.isEmpty(spList)) {
            return null;
        }
        Integer nowSp = Integer.parseInt(spList.get(0));
        Integer afterSp = Integer.valueOf(spList.get(1));
        int preSp = afterSp - nowSp;
        if (preSp <= 0) {
            return null;
        }
        //1分钟8体力*预计恢复体力 = 总恢复时间
        long preSpRestoreMin = preSp * 8L;

        Date date = new Date(System.currentTimeMillis() + 60000 * preSpRestoreMin);
        String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        msg.text("预计").text(formatDate).text("恢复到").text(spList.get(1)).text("体力.");
        return msg;
    }

}
