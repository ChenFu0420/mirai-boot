package moe.iacg.miraiboot.schedule;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrSpliter;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import moe.iacg.miraiboot.entity.BangumiStatus;
import moe.iacg.miraiboot.entity.BangumiStatusKey;
import moe.iacg.miraiboot.model.BangumiList;
import moe.iacg.miraiboot.service.BangumiStatusService;
import net.lz1998.pbbot.bot.ApiSender;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class BotScheduled {


    private static String lastQuarterBangumi;
    @Autowired
    BotContainer botContainer;
    @Autowired
    BangumiStatusService bangumiStatusService;


    private Bot getBot() {
        return botContainer.getBots().values().stream().findFirst().get();
    }

    @Scheduled(cron = "0 0 5 * * ?")
    @PostConstruct
    public void getBangumiJSON() {
        String bangumiLayerYearJSON = HttpUtil.get("https://bgmlist.com/tempapi/archive.json");
        TreeMap<String, Object> data = MapUtil.sort(JSON.parseObject(bangumiLayerYearJSON).getJSONObject("data"));
        var yearURLCollect = (Collection) data.values();
        var lastYear = (Map<String, Object>) yearURLCollect.toArray()[yearURLCollect.size() - 1];
        var quarterCollect = lastYear.values();
        var lastQuarter = (Map<String, Object>) quarterCollect.toArray()[quarterCollect.size() - 1];
        var lastQuarterBangumiURL = (String) lastQuarter.get("path");
        lastQuarterBangumi = HttpUtil.get(lastQuarterBangumiURL);
    }


    @Scheduled(cron = "* */1 * * * ?")
    public void sendBangumiUpdateTime() {

        Collection<Object> bgList = JSON.parseObject(lastQuarterBangumi).values();
        List<BangumiList> bangumiList = Convert.convert(new TypeReference<>() {
        }, bgList);

        //周-1 ，当天番剧
        Map<String, List<BangumiList>> tmpBGMList = new HashMap<>();

        for (BangumiList bgm : bangumiList) {
            if (bgm.isNewBgm()) {
                tmpBGMList.computeIfAbsent(bgm.getWeekDayCN(), k -> new ArrayList<>()).add(bgm);
            }
        }
        Date date = new Date();

        SimpleDateFormat hHmm = new SimpleDateFormat("HHmm");
        String hhmmString = hHmm.format(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        Msg result = new Msg();
        List<BangumiList> todayBGM = tmpBGMList.get(String.valueOf(dayOfWeek - 1));

        int bgmId = 0;
        for (BangumiList bgm : todayBGM) {
            String timeCN = bgm.getTimeCN();
            if (!StringUtils.isEmpty(timeCN) && timeCN.equals(hhmmString)) {
                result.text("《").text(bgm.getTitleCN()).text("》").text("更新了！\n")
                        .text("放送地址：\n");
                for (String url : bgm.getOnAirSite()) {
                    result.text(url + "\n");
                }
                bgmId = bgm.getBgmId();
                break;
            }
        }
        if (CollectionUtil.isEmpty(result.getMessageChain())) {
            return;
        }
        List<BangumiStatus> bangumiStatusByBangumiFlag = bangumiStatusService.findByBangumiFlag();
//        //过滤不喜欢的番剧
        Map<Long, Set<String>> excludeBangumi = new HashMap<>();
//        //组与QQ关系
        Map<Long, List<Long>> qqByQQGroup = new HashMap<>();
//
        for (BangumiStatus coolQStatus : bangumiStatusByBangumiFlag) {
            BangumiStatusKey id = coolQStatus.getId();

            qqByQQGroup.computeIfAbsent(id.getQqGroup(), x -> new ArrayList<>()).add(id.getQq());
            if (!StringUtils.isEmpty(coolQStatus.getBangumiExclude())) {
                List<String> splitBangumiExclude = StrSpliter.split(coolQStatus.getBangumiExclude(), ",", true, true);
                excludeBangumi.computeIfAbsent(id.getQq(), x -> new HashSet<>()).addAll(splitBangumiExclude);
            }
        }
        result.text("通知下面的小伙伴开饭啦，如果不喜欢这个番剧可以：/bangumi rm ").text(String.valueOf(bgmId)).text("\n");
        int finalBgmId = bgmId;
        //过滤不喜欢的番剧
        qqByQQGroup.forEach((group, qqs) -> {
            Msg atMember = new Msg();
            qqs.forEach(qq -> {
                if (!MapUtil.isEmpty(excludeBangumi) &&
                        !(excludeBangumi.containsKey(qq) && excludeBangumi.get(qq)
                                .contains(String.valueOf(finalBgmId)))) {
                    result.at(qq);
                    atMember.at(qq);
                }
            });
            if (CollectionUtil.isEmpty(atMember.getMessageChain())) {
                return;
            }
            result.sendToGroup(getBot(), group);
        });
    }
}
