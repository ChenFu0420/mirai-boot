package moe.iacg.miraiboot.plugins;


import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.constants.SenderRoleConstant;
import moe.iacg.miraiboot.enums.Commands;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
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

    @Override
    @SneakyThrows
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        Msg builder = Msg.builder();
        String role = event.getSender().getRole();
        String rawMessage = event.getRawMessage();
        if (role.equals(SenderRoleConstant.ADMIN) || role.equals(SenderRoleConstant.OWNER)) {

            String command = BotUtils.getSecondCommand(Commands.MCSM.getCommand(), rawMessage.trim());


            if (StringUtils.isBlank(command)) {
                command = "status";
            }

            String data = HttpUtil.get(mcsmApiURL + command + "/" + event.getGroupId() + "?apikey=" + mcsmAdminKey);

            if (data == "null") {
                builder.text("查询服务端不存在，请联系管理员绑定");
                return BotUtils.sendMessage(bot, event, builder);
            }
            JSONObject jsonData = JSON.parseObject(data);
            int status = jsonData.getInteger(STATUS);
            String error = jsonData.getString("error");
            if (status == HttpStatus.HTTP_OK) {
                if (command.equals(START_SERVER)) {
                    builder.text("服务器启动成功，请稍等一会在服务器列表刷新查看");

                }
                if (command.equals(STOP_SERVER)) {
                    builder.text("服务器已关闭");
                }
            } else if (status == 0) {
                builder.text("当前服务器不在线，已关闭，如有需要请启动");
            } else {
                builder.text(error);
            }
        } else {
            builder.text("您不是管理员，无权启动服务器，请找群管理启动哈~");
        }

        return BotUtils.sendMessage(bot, event, builder);


    }
}
