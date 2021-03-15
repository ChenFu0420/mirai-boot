package moe.iacg.miraiboot.plugins;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import io.github.mzdluo123.silk4j.AudioUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.constants.MsgTypeConstant;
import moe.iacg.miraiboot.enums.Commands;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author a8156
 */
@Slf4j
@Component
@CommandPrefix(command = Commands.TOMP3)
public class RecordToMp3 extends BotPlugin {
    @Autowired
    BotUtils botUtils;


    @PostConstruct
    @SneakyThrows
    private void init() {
        AudioUtils.init(FileUtil.mkdir("tmpAudio"));
    }

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        return MESSAGE_IGNORE;
    }

    @Override
    @SneakyThrows
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        var replyIds = BotUtils.getMessageForType(event.getMessageList(), MsgTypeConstant.REPLY);
        var replyIdsOpt = replyIds.stream().findFirst();
        if (replyIdsOpt.isEmpty()) {
            return MESSAGE_BLOCK;
        }

        var msgId = Integer.parseInt(replyIdsOpt.get());
        var msg = bot.getMsg(msgId);
        var recordURLs = BotUtils.getMessageForType(msg.getMessageList(), MsgTypeConstant.RECORD);
        var recordURLOpt = recordURLs.stream().findFirst();

        if (recordURLOpt.isEmpty()) {
            return MESSAGE_BLOCK;
        }
        var recordURL = recordURLOpt.get();
        var tempFileSilk = FileUtil.createTempFile(replyIdsOpt.get(), ".silk", FileUtil.mkdir("record"), true);

        HttpUtil.downloadFile(recordURL, tempFileSilk);
        var file = AudioUtils.silkToMp3(tempFileSilk);

        Msg builder = Msg.builder();
        builder.text("mp3已经生成，点击下面链接下载：\n");
        builder.text("http://vimc.cc:8081/File/recordMp3/" + Base64.encode(file.getAbsolutePath()));
        return botUtils.sendMessage(bot, event, builder);
    }
}
