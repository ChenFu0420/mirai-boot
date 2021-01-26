package moe.iacg.miraiboot.plugins;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrSpliter;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.constants.Commands;
import moe.iacg.miraiboot.entity.BangumiStatus;
import moe.iacg.miraiboot.entity.BangumiStatusKey;
import moe.iacg.miraiboot.model.BangumiList;
import moe.iacg.miraiboot.service.BangumiStatusService;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.bot.ApiSender;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
@CommandPrefix(command = Commands.BANGUMI)
public class Bangumi extends BotPlugin {

    @Autowired
    BangumiStatusService bangumiStatusService;

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {


        Msg messageBuilder = new Msg();
        long qq = event.getUserId();
        long groupId = event.getGroupId();


        var key = new BangumiStatusKey();

        key.setQq(qq);
        key.setQqGroup(groupId);
        BangumiStatus bangumiStatus = bangumiStatusService.get(key);
        String acceptMessage = BotUtils.removeCommandPrefix(Commands.BANGUMI.getCommand(), event.getRawMessage());


        if (StringUtils.isEmpty(acceptMessage)) {
            if (bangumiStatus == null) {
                bangumiStatus = new BangumiStatus();
                bangumiStatus.setId(key).setBangumiFlag(1);
                bangumiStatusService.save(bangumiStatus);

                messageBuilder.text("你已经成功订阅新番更新提醒，新番更新的时候会").at(qq).text("你哈");
                return BotUtils.sendMessage(bot, event, messageBuilder);
            }
            if (bangumiStatus.getBangumiFlag() == 1) {

                bangumiStatus.setId(key).setBangumiFlag(0);
                bangumiStatusService.update(bangumiStatus);
                messageBuilder.text("你已经取消订阅新番更新提醒");
            } else {
                bangumiStatus.setId(key).setBangumiFlag(1);
                bangumiStatusService.update(bangumiStatus);
                messageBuilder.text("再次订阅了新番更新提醒，新番更新的时候会").at(qq).text("你哈");
            }
            return BotUtils.sendMessage(bot, event, messageBuilder);
        }

        if (acceptMessage.startsWith("rm ")) {
            List<String> bgmIds = StrSpliter.split(acceptMessage, " ", true, true);
            StringBuilder excludeBGMIds = new StringBuilder();

            for (int i = 1; i < bgmIds.size(); i++) {
                excludeBGMIds.append(bgmIds.get(i)).append(",");
            }

            String bangumiExclude = bangumiStatus.getBangumiExclude();
            if (StringUtils.isEmpty(bangumiExclude)) {
                bangumiStatus.setBangumiExclude(excludeBGMIds.toString());
                bangumiStatusService.update(bangumiStatus);
            } else {
                bangumiStatus.setBangumiExclude(bangumiExclude + excludeBGMIds.toString());
                bangumiStatusService.update(bangumiStatus);
            }
            messageBuilder.text("已经移除番号：" + excludeBGMIds.toString() + "订阅。");
            return BotUtils.sendMessage(bot, event, messageBuilder);
        }
        messageBuilder.text("你没有订阅或错误使用命令无法进行通知番剧过滤");
        return BotUtils.sendMessage(bot, event, messageBuilder);
    }
}
