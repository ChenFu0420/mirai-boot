package moe.iacg.miraiboot.plugins;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.constants.Commands;
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
@Slf4j
@Component
@CommandPrefix(command = Commands.SETU, alias = {"开车", "涩图", "色图", "营养", "开冲", "够色", "摩多", "兴奋", "黄图"})
public class SeTu extends BotPlugin {

    @Autowired
    RedisUtil redisUtil;
    private int tmpNextKeyIndex = 0;

    @NacosValue("${setu.api.key}")
    private String seTuAPIKeys;

    @NacosValue("${lolicon.proxy.url}")
    private String loliconProxyURL;


    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        return BotUtils.sendMessage(bot, event, sendSeTu(event.getRawMessage()));
    }

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        return BotUtils.sendMessage(bot, event, sendSeTu(event.getRawMessage()));
    }

    private Msg sendSeTu(String message) {

        String keyword = BotUtils.removeCommandPrefix(Commands.SETU.getCommand(), message);
        Msg builder = Msg.builder();

        for (String alias : this.getClass().getAnnotation(CommandPrefix.class).alias()) {
            if (message.contains(alias)) {
                keyword = null;
                break;
            }
        }

        if ("count".equals(keyword)) {
            String setuCount = redisUtil.get(RedisUtil.BOT_SETU_COUNT);

            return builder.text("今天咱发了").text(setuCount).text("份色图，要节制啊QwQ");
        }
        builder.image(getSeTuApi(keyword, 1));

//        SeTuResponseModel seTuResponseModel = seTuApi(keyword, 1, 1);
//
//        if (seTuResponseModel == null) {
//            builder.text("调用色图服务出错");
//            return builder;
//        }
//        if (seTuResponseModel.getCode() != 0) {
//            if (seTuResponseModel.getCode() == HttpStatus.HTTP_NOT_FOUND) {
//                builder.text("找不到符合关键字的色图");
//            }
//            if (seTuResponseModel.getCode() == HttpStatus.HTTP_FORBIDDEN) {
//                builder.text("由于不规范的操作而被拒绝调用");
//            }
//            if (seTuResponseModel.getCode() == HttpStatus.HTTP_UNAUTHORIZED) {
//                builder.text("APIKEY 不存在或被封禁");
//            }
//            if (seTuResponseModel.getCode() == 429) {
//                builder.text("达到调用额度限制");
//            }
//            if (seTuResponseModel.getCode() == -1) {
//                builder.text("内部错误，请向 i@loli.best 反馈");
//            }
//            return builder;
//        }
//        SeTuResponseModel.Setu firstSeTu = seTuResponseModel.getData().stream().findFirst().get();
//        builder.image(firstSeTu.getUrl());
//        builder.text("作品名称：").text(firstSeTu.getTitle()).text("\n画师：").text(firstSeTu.getAuthor());
        return builder;
    }

    /**
     * lolicon_proxy
     *
     * @param keyword
     * @param r18
     * @return
     */
    public String getSeTuApi(String keyword, int r18) {
        setuCount();
        Map<String, Object> requestData = new HashMap<>();
        if (!StringUtils.isEmpty(keyword)) {
            requestData.put("keyword", keyword);
        }
        requestData.put("r18", r18);
        return loliconProxyURL + ReUtil.get("href=\"(.*)\">",
                HttpUtil.get(loliconProxyURL + "/lolicon", requestData), 1);
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

        jsonData = HttpUtil.get("https://api.lolicon.app/setu/", requestData);
        seTuResponseModel = JSON.parseObject(jsonData, SeTuResponseModel.class);

        setuCount();

        return seTuResponseModel;
    }

    private void setuCount() {
        redisUtil.incrBy(RedisUtil.BOT_SETU_COUNT, 1);

        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 58);
        redisUtil.expireAt(RedisUtil.BOT_SETU_COUNT, instance.getTime());
    }



}
