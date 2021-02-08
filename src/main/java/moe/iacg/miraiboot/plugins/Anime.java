package moe.iacg.miraiboot.plugins;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.enums.Commands;
import moe.iacg.miraiboot.model.AnimeModel;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
@CommandPrefix(command = Commands.ANIME)
public class Anime extends BotPlugin {

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        return BotUtils.sendMessage(bot, event, queryAnime(event.getMessage(1)));

    }

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        return BotUtils.sendMessage(bot, event, queryAnime(event.getMessage(1)));

    }
    private Msg queryAnime(OnebotBase.Message message) {
        Msg builder = Msg.builder();
        String result = HttpUtil.get("https://trace.moe/api/search?url=" +message.getDataMap().get("url"));
        if ("\"Error reading imagenull\"".equals(result)) {
            builder.text("你发送的图片过大或是GIF导致搜索失败:P");
            return builder;
        }
        AnimeModel animeModel = null;
        try {
            animeModel = JSONObject.parseObject(result, AnimeModel.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            builder.text("查询失败，请稍后再试=_=||");
            return builder;
        }

        if (CollectionUtils.isEmpty(animeModel.getDocs())) {
            builder.text("没有找到该番剧信息OAO");
            return builder;
        }
        AnimeModel.DocsItem docsItem = animeModel.getDocs().stream().findFirst().get();
        builder.text(docsItem.getAnime()).text("\n");
        if (docsItem.getEpisode() != 0) {
            builder.text("第" + docsItem.getEpisode() + "集\n");

        }
        builder.text("相似度：").text(String.valueOf((int) (docsItem.getSimilarity() * 100))).text("%\n")
                .text("场景片段：")
                .text(String.valueOf((int) docsItem.getFrom() / 60)).text(":").text(String.valueOf((int) docsItem.getFrom() % 60))
                .text(" - ")
                .text(String.valueOf((int) docsItem.getTo() / 60)).text(":").text(String.valueOf((int) docsItem.getTo() % 60));
        return builder;
    }
}

