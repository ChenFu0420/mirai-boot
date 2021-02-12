package moe.iacg.miraiboot.plugins;

import cn.hutool.core.util.ReUtil;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.constants.MsgTypeConstant;
import moe.iacg.miraiboot.enums.FileType;
import moe.iacg.miraiboot.telegram.AE86QQBot;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
@Slf4j
public class Telegram extends BotPlugin {
//    @Autowired
    AE86QQBot ae86QQBot;

//    @Autowired
    BotUtils botUtils;

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        for (Map.Entry<Long, Long> tgGroupAndQQGroup : ae86QQBot.tgGroupIdByGroupId().entrySet()) {
            Long tgGroupId = tgGroupAndQQGroup.getKey();
            long qqGroupId = event.getGroupId();
            int messageId = event.getMessageId();
            var sender = event.getSender();
            List<OnebotBase.Message> messageList = event.getMessageList();
            List<String> types = messageList.stream().map(OnebotBase.Message::getType).collect(Collectors.toList());

            if (qqGroupId == tgGroupAndQQGroup.getValue()) {
                String senderTitle = sender.getNickname() + "(" + messageId + ")" + "：\n";

                if (types.contains(MsgTypeConstant.REPLY)) {
                    List<String> messageForText = BotUtils.getMessageForType(messageList, MsgTypeConstant.REPLY);

                    String msg = messageForText.stream().reduce("", (a, b) -> a + b);
                    String tgMessageId = ReUtil.get("\\((.*)\\)：\n", msg, 1);
                }


                for (OnebotBase.Message message : messageList) {
                    String type = message.getType();
                    if (types.contains("reply")) {
                        if (type.equals("reply")) {
                            String rawMessage = message.getDataOrThrow("raw_message");
                            String tgMessageId = ReUtil.get("\\((.*)\\)：\n", rawMessage, 1);
                            ae86QQBot.sendTextMessage(senderTitle + messageList.get(messageList.size() - 1)
                                            .getDataOrThrow("text"),
                                    tgGroupId, Integer.parseInt(tgMessageId));
                            break;
                        }

                    } else if (type.equals("text")) {
                        ae86QQBot.sendTextMessage(senderTitle + message.getDataOrThrow("text"), tgGroupId);
                    }

                    if (type.equals("image")) {
                        String url = message.getDataOrThrow("url");
                        String fileSuffix = botUtils.getFileSuffix(url);
                        if (fileSuffix.equals(FileType.GIF.getSuffix())) {
                            ae86QQBot.sendAnimationForUrl(url, tgGroupId, senderTitle);
                        }
                        if (fileSuffix.equals(FileType.PNG.getSuffix()) || fileSuffix.equals(FileType.JPG.getSuffix())) {
                            ae86QQBot.sendImageFromUrl(url, tgGroupId, senderTitle);
                        }
                    }

                }
            }
        }
        return MESSAGE_IGNORE;
    }
}
