package moe.iacg.miraiboot.plugins;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.SneakyThrows;
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

@Slf4j
@Component
@CommandPrefix(command = Commands.MCSM)
public class StartServer extends BotPlugin {
    private static final String STATUS = "status";
    private static final String STOP_SERVER = "stop_server";
    private static final String START_SERVER = "start_server";
    @NacosValue("${mcsm.api.admin.key}")
    private String mcsmAdminKey;
    @NacosValue("${mcsm.api.url}")
    private String mcsmApiURL;

    @Autowired
    private BotUtils botUtils;

    @Override
    @SneakyThrows
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        Msg builder = Msg.builder();
        String rawMessage = event.getRawMessage();

        String secondCommand = BotUtils.getSecondCommand(Commands.MCSM.getCommand(),
                rawMessage.trim());

        String tmpMCSMCommand;


        switch (secondCommand) {
            case STATUS:
                tmpMCSMCommand = STATUS;
                break;
            case START_SERVER:
                tmpMCSMCommand = START_SERVER;
                break;
            case STOP_SERVER:
                tmpMCSMCommand = STOP_SERVER;
                break;
            default:
                if (StringUtils.isBlank(secondCommand)) {
                    tmpMCSMCommand = STATUS;
                } else {
                    return botUtils.sendMessage(bot, event, builder.text("?????????????????????????????????????????????????????????"));

                }
                break;
        }
        HttpRequest request = HttpUtil.createGet(mcsmApiURL + tmpMCSMCommand + "/" + event.getGroupId() + "?apikey=" + mcsmAdminKey);

        String data = request.execute().body();
        if (data.equals("null")) {
            builder.text("???????????????????????????????????????????????????");
            return botUtils.sendMessage(bot, event, builder);
        }


        if (BotUtils.hasGroupAdmin(event)) {
            if (tmpMCSMCommand.equals(STOP_SERVER) || tmpMCSMCommand.equals(START_SERVER)) {
                JSONObject jsonData = JSON.parseObject(data);
                int status = jsonData.getInteger(STATUS);
                String error = jsonData.getString("error");

                if (status == HttpStatus.HTTP_OK) {
                    if (tmpMCSMCommand.equals(START_SERVER)) {
                        builder.text("?????????????????????????????????????????????????????????????????????");

                    }
                    if (tmpMCSMCommand.equals(STOP_SERVER)) {
                        builder.text("??????????????????");
                    }
                } else if (status == HttpStatus.HTTP_INTERNAL_ERROR) {
                    builder.text("????????????");

                } else if (status == 0) {
                    builder.text("????????????????????????????????????????????????????????????");
                } else {
                    builder.text(error);
                }
            }
        } else if (STATUS.equals(tmpMCSMCommand)) {
            builder.text(data);
            return botUtils.sendMessage(bot, event, builder);
        } else {
            builder.text("?????????????????????????????????????????????????????????????????????~");
        }


        return botUtils.sendMessage(bot, event, builder);


    }
}
