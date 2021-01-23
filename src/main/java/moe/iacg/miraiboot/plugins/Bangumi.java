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
import net.lz1998.pbbot.bot.ApiSender;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
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






}
