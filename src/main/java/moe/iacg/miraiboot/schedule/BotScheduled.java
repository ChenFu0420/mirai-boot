package moe.iacg.miraiboot.schedule;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import moe.iacg.miraiboot.entity.BangumiStatus;
import moe.iacg.miraiboot.model.BangumiList;
import moe.iacg.miraiboot.service.BangumiStatusService;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.utils.Msg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BotScheduled {


    private static String lastQuarterBangumi;

    private static Map<String, List<BangumiList>> todayBGMForTime;
    private static List<BangumiStatus> byBangumiFlag;


    @Autowired
    private BangumiStatusService bangumiStatusService;

    @Autowired
    private BotUtils botUtils;

    @Scheduled(cron = "0 0 0 * * ?")
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

        Collection<Object> bgList = JSON.parseObject(lastQuarterBangumi).values();
        List<BangumiList> bangumiList = Convert.convert(new TypeReference<>() {
        }, bgList);

        //周-1 ，当天番剧
        Map<String, List<BangumiList>> tmpBGMList = new HashMap<>();
        List<BangumiList> todayNoTimeCN = new ArrayList<>();
        for (BangumiList bgm : bangumiList) {

            if (StringUtils.isAllEmpty(bgm.getOnAirSite()) || StringUtils.isEmpty(bgm.getTimeCN())) {
                todayNoTimeCN.add(bgm);
                continue;
            }
            tmpBGMList.computeIfAbsent(bgm.getWeekDayCN(), k -> new ArrayList<>()).add(bgm);
        }
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //获取今天的新番列表
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        List<BangumiList> todayBGM = tmpBGMList.get(String.valueOf(dayOfWeek - 1));
        todayBGMForTime = todayBGM.stream().collect(Collectors.groupingBy(BangumiList::getTimeCN));
        byBangumiFlag = bangumiStatusService.findByBangumiFlag();
    }


    @Scheduled(cron = "0 */1 * * * ?")
    @Async
    public void sendBangumiUpdateTime() {
        SimpleDateFormat hHmm = new SimpleDateFormat("HHmm");
        String hhmmString = hHmm.format(new Date());
        List<BangumiList> nowBGM = todayBGMForTime.getOrDefault(hhmmString, null);

        if (CollectionUtil.isNotEmpty(nowBGM)) {
            List<BangumiStatus> all = bangumiStatusService.getAll();
            Map<Long, String> qqByExcludeBGM = all
                    .stream()
                    .collect(Collectors
                            .toMap(BangumiStatus::getQq, a -> a.getBangumiExclude() == null ? "" :
                                    a.getBangumiExclude()));

            for (BangumiList bangumiList : nowBGM) {
                Msg result = Msg.builder();
                result.text("《").text(bangumiList.getTitleCN()).text("》").text("更新了！\n")
                        .text("放送地址：\n");
                for (String url : bangumiList.getOnAirSite()) {
                    result.text(url + "\n\n");
                }
                String bgmId = bangumiList.getBgmId().toString();
                result.text("如果你不喜欢这个番剧，可以输入/bgm rm " + bgmId + "\n");

                List<Long> qqs = byBangumiFlag
                        .stream()
                        .map(BangumiStatus::getQq)
                        .collect(Collectors.toList());

                for (Long qq : qqs) {
                    String excludeBGMs = qqByExcludeBGM.get(qq);
                    if (StringUtils.isNotEmpty(excludeBGMs) && excludeBGMs.contains(bgmId)) {
                        return;
                    }
                    result.sendToFriend(botUtils.getFirstBot(), qq);
                }
            }
        }

    }
}
