package moe.iacg.miraiboot.plugins;

import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.log4j.Log4j2;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.model.SeTuResponseModel;
import moe.iacg.miraiboot.utils.BotUtils;
import moe.iacg.miraiboot.utils.RedisUtil;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Ghost
 */
@Log4j2
@Component
public class SeTu extends BotPlugin {

    @Autowired
    RedisUtil redisUtil;
    private int tmpNextKeyIndex = 0;
    @NacosValue("${setu.api.key}")
    private String seTuAPIKeys;

    @Override
    @CommandPrefix(command = "/setu")
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        return onMessage(bot, event,sendSeTu(event.getRawMessage()));
    }
    @CommandPrefix(command = "/setu")
    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        return onMessage(bot, event,sendSeTu(event.getRawMessage()));
    }

    private Msg sendSeTu(String message) {
        String keyword = BotUtils.removeCommandPrefix("setu", message);
        Msg builder = Msg.builder();
        SeTuResponseModel seTuResponseModel = seTuApi(keyword, 2, 1);

        if (seTuResponseModel == null) {
            builder.text("调用色图服务出错");
            return builder;
        }
        if (seTuResponseModel.getCode() != 0) {
            if (seTuResponseModel.getCode() == HttpStatus.HTTP_NOT_FOUND) {
                builder.text("找不到符合关键字的色图");
            }
            if (seTuResponseModel.getCode() == HttpStatus.HTTP_FORBIDDEN) {
                builder.text("由于不规范的操作而被拒绝调用");
            }
            if (seTuResponseModel.getCode() == HttpStatus.HTTP_UNAUTHORIZED) {
                builder.text("APIKEY 不存在或被封禁");
            }
            if (seTuResponseModel.getCode() == 429) {
                builder.text("达到调用额度限制");
            }
            if (seTuResponseModel.getCode() == -1) {
                builder.text("内部错误，请向 i@loli.best 反馈");
            }
            return builder;
        }
        SeTuResponseModel.Setu firstSeTu = seTuResponseModel.getData().stream().findFirst().get();
        builder.image(firstSeTu.getUrl());
        builder.text("作品名称：").text(firstSeTu.getTitle()).text("\n画师：").text(firstSeTu.getAuthor());
        return builder;
    }

    private <T> int onMessage(Bot bot, T event, Msg msg) {

        if (event instanceof OnebotEvent.PrivateMessageEvent) {
            var eventPrivate = (OnebotEvent.PrivateMessageEvent) event;
            bot.sendPrivateMsg(eventPrivate.getUserId(), msg, false);
        }

        if (event instanceof OnebotEvent.GroupMessageEvent) {
            var eventGroup = (OnebotEvent.GroupMessageEvent) event;
            bot.sendPrivateMsg(eventGroup.getGroupId(),
                    msg, false);

        }

        return MESSAGE_BLOCK;
    }


    /**
     * 文档：https://api.lolicon.app/
     *
     * @param keyword 若指定关键字，将会返回从插画标题、作者、标签中模糊搜索的结果
     * @param r18     0为非 R18，1为 R18，2为混合
     * @param num     一次返回的结果数量，范围为1到10，不提供 APIKEY 时固定为1；在指定关键字的情况下，结果数量可能会不足指定的数量
     * @return
     */


    public SeTuResponseModel seTuApi(String keyword, int r18, int num) {
        Map<String, Object> requestData = new HashMap<>();
        while (true) {
            List<String> seTuKeys = Arrays.asList(seTuAPIKeys.split(","));
            if (tmpNextKeyIndex < seTuKeys.size()) {
                requestData.put("apikey", seTuKeys.get(tmpNextKeyIndex));
                tmpNextKeyIndex++;
                break;
            } else {
                tmpNextKeyIndex = 0;
            }
        }
        if (!StringUtils.isEmpty(keyword)) {
            requestData.put("keyword", keyword);
        }
        requestData.put("r18", r18);
        requestData.put("num", num);
        requestData.put("proxy", "i.pixiv.cat");
        requestData.put("size1200", true);
        String jsonData;
        SeTuResponseModel seTuResponseModel;
        try {
            jsonData = HttpUtil.get("https://api.lolicon.app/setu/", requestData);
            seTuResponseModel = JSON.parseObject(jsonData, SeTuResponseModel.class);
        } catch (Exception e) {
            log.error("调用色图服务出错", e);
            return null;
        }
        Optional<SeTuResponseModel.Setu> first = seTuResponseModel.getData().stream().findFirst();

//        if (!redisUtil.hasKey(RedisUtil.BOT_SETU_COUNT)){
//            redisUtil.set(RedisUtil.BOT_SETU_COUNT, "1");
//
//        }

        redisUtil.incrBy(RedisUtil.BOT_SETU_COUNT, 1);

        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 58);
        redisUtil.expireAt(RedisUtil.BOT_SETU_COUNT, instance.getTime());


        return seTuResponseModel;
    }
}
