package moe.iacg.miraiboot.plugins;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.enums.Commands;
import moe.iacg.miraiboot.model.HpoolMiningDetail;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@CommandPrefix(command = Commands.POOL)
public class Pool extends BotPlugin {

    private static String hpoolApi = "https://hpool.com/api/pool/miningdetail?language=zh&type=chia&count=100&page=";

    private static String xchPriceChart = "https://api2.chiaexplorer.com/chart/xchPriceChart?period=24h";

    @NacosValue("${hpool.cookie}")
    private String hpoolCookie;

    @Autowired
    BotUtils botUtils;

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        int page = 1;
        List<HpoolMiningDetail.Data.List> allData = new ArrayList<>();
        Integer total = getPoolMinigDetail(page, allData);

        int pages = (total + 100 - 1) / 100;
        if (pages > 1) {
            for (int i = 2; i <= pages; i++) {
                getPoolMinigDetail(i, allData);
            }
        }
        BigDecimal unSettlement = BigDecimal.ZERO;
        BigDecimal settlements = BigDecimal.ZERO;
        for (HpoolMiningDetail.Data.List allDatum : allData) {
            if (allDatum.getStatus() == 0) {
                unSettlement = unSettlement.add(allDatum.getBlock_reward());
            }
            if (allDatum.getStatus() == 2) {
                settlements = settlements.add(allDatum.getBlock_reward());
            }

        }
        BigDecimal count = settlements.add(unSettlement);
        Msg builder = Msg.builder();
        builder.text("老王hpool矿池CHIA结算情况统计\n");
        builder.text("当日未结算XCH：" + unSettlement.divide(new BigDecimal(2),8, RoundingMode.HALF_EVEN)).text("\n");
        builder.text("已结算XCH：" + settlements.divide(new BigDecimal(2),8, RoundingMode.HALF_EVEN)).text("\n");
        builder.text("矿池总收益XCH：" + count.divide(new BigDecimal(2),8, RoundingMode.HALF_EVEN)).text("\n");
        builder.text("矿池总收益USDT：" + count.multiply(currentXCHPriceChart()).divide(new BigDecimal(2),8, RoundingMode.HALF_EVEN)).text("\n");
        builder.text("（如上统计总收益已除2才与实际收益核对上,怀疑矿池偷吃50%");

        return botUtils.sendMessage(bot, event, builder);
    }

    private BigDecimal currentXCHPriceChart() {
        String data = HttpUtil.get(xchPriceChart);
        JSONArray priceList = JSON.parseObject(data).getJSONArray("data");
        int size = priceList.size();
        return (BigDecimal) priceList.get(size - 1);
    }

    private Integer getPoolMinigDetail(int page, List<HpoolMiningDetail.Data.List> allData) {
        HttpRequest request = HttpUtil.createGet(hpoolApi + page);
        request.cookie(new HttpCookie("auth_token", hpoolCookie));
        HttpResponse execute = request.execute();
        String body = execute.body();

        HpoolMiningDetail hpoolMiningDetail = JSONObject.parseObject(body, HpoolMiningDetail.class);
        HpoolMiningDetail.Data data = hpoolMiningDetail.getData();

        Integer total = data.getTotal();
        allData.addAll(data.getList());
        return total;
    }
}