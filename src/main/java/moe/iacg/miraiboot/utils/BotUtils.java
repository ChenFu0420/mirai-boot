package moe.iacg.miraiboot.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.constants.MsgDataConstant;
import moe.iacg.miraiboot.constants.MsgTypeConstant;
import moe.iacg.miraiboot.constants.SenderRoleConstant;
import moe.iacg.miraiboot.enums.FileType;
import moe.iacg.miraiboot.limit.RedisRaterLimiter;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BotUtils {

    @Autowired
    BotContainer botContainer;

    @Autowired
    RedisRaterLimiter redisRaterLimiter;

    public Bot getFirstBot() {
        return botContainer.getBots().values().stream().findFirst().get();
    }

    /**
     * @param command
     * @param content
     * @return java.lang.String
     * @Description 删除命令字符
     * @author wlwang3
     * @date 2020/9/6
     */
    public static String removeCommandPrefix(String command, String content) {

        return content.replace("/" + command, "").trim();
    }

    public static String getSecondCommand(String command, String content) {
        String secondContent = removeCommandPrefix(command, content);
        secondContent = secondContent.trim();

        String[] splitSecondContent = secondContent.split(" ");
        if (splitSecondContent.length != 1) {
            return null;
        }
        return secondContent;
    }


    public <T> int sendMessage(Bot bot, T event, Msg msg) {

        long userId;

        if (event instanceof OnebotEvent.PrivateMessageEvent) {
            var eventPrivate = (OnebotEvent.PrivateMessageEvent) event;
            userId = eventPrivate.getUserId();
            Boolean hasAcq = redisRaterLimiter.acquireByRedis(String.valueOf(userId), 1L, 3000L);

            if (!hasAcq) {
                msg.setMessageChain(new ArrayList<>());
                msg.text("你发送的消息太快了！休息一下？快男？");
            }else {
                bot.sendPrivateMsg(eventPrivate.getUserId(), msg, false);
            }
        }

        if (event instanceof OnebotEvent.GroupMessageEvent) {
            var eventGroup = (OnebotEvent.GroupMessageEvent) event;
            userId = eventGroup.getUserId();
            Boolean hasAcq = redisRaterLimiter.acquireByRedis(String.valueOf(userId), 1L, 3000L);
            if (!hasAcq) {
                msg.setMessageChain(new ArrayList<>());
                msg.text("你发送的消息太快了！休息一下？快男？").at(userId);
            }else {
                bot.sendGroupMsg(eventGroup.getGroupId(),
                        msg, false);
            }
        }
        return BotPlugin.MESSAGE_BLOCK;
    }

    public static boolean hasGroupAdmin(OnebotEvent.GroupMessageEvent event) {
        String role = event.getSender().getRole();
        return (role.equals(SenderRoleConstant.ADMIN) || role.equals(SenderRoleConstant.OWNER));
    }

    /**
     * 中文数字转为阿拉伯数字
     *
     * @param zhNumStr 中文数字
     * @return 阿拉伯数字
     */
    public static int zh2arbaNum(String zhNumStr) {
        Stack<Integer> stack = new Stack<>();
        String numStr = "一两三四五六七八九";
        String unitStr = "十百千万亿";

        String[] ssArr = zhNumStr.split("");
        for (String e : ssArr) {
            int numIndex = numStr.indexOf(e);
            int unitIndex = unitStr.indexOf(e);
            if (numIndex != -1) {
                stack.push(numIndex + 1);
            } else if (unitIndex != -1) {
                int unitNum = (int) Math.pow(10, unitIndex + 1);
                if (stack.isEmpty()) {
                    stack.push(unitNum);
                } else {
                    stack.push(stack.pop() * unitNum);
                }
            }
        }
        ;

        return stack.stream().mapToInt(s -> s).sum();
    }

    /**
     * 根据type获取消息
     *
     * @param messageList
     * @param type
     * @return
     */
    public static List<String> getMessageForType(List<OnebotBase.Message> messageList, String type) {
        //不同消息类型
        var messageStream = messageList.stream()
                .filter(message -> message.getType().equals(type));

        //返回消息文字
        if (MsgTypeConstant.TEXT.equals(type)) {
            return messageStream
                    .map(message -> message.getDataOrThrow(type))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());
        }
        //返回at QQ号
        if (MsgTypeConstant.AT.equals(type)) {
            return messageStream
                    .map(message -> message.getDataOrThrow(MsgDataConstant.QQ))
                    .collect(Collectors.toList());
        }
        //返回图片
        if (MsgTypeConstant.IMAGE.equals(type)) {
            return messageStream
                    .map(message -> message.getDataOrThrow(MsgDataConstant.URL))
                    .collect(Collectors.toList());
        }
        //表情
        if (MsgTypeConstant.FACE.equals(type)) {
            return messageStream
                    .map(message -> message.getDataOrThrow(MsgDataConstant.ID))
                    .collect(Collectors.toList());
        }
        //回复消息的内容
        if (MsgTypeConstant.REPLY.equals(type)) {
            return getMessageForType(messageList, MsgDataConstant.TEXT);
        }
        return Collections.emptyList();
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public int sendGroupMsg(Long groupId, Msg msg) {
        getFirstBot().sendGroupMsg(groupId, msg, false);
        return BotPlugin.MESSAGE_BLOCK;
    }
    public int sendPrivateMsg(Long qq, Msg msg) {
        getFirstBot().sendPrivateMsg(qq, msg, false);
        return BotPlugin.MESSAGE_BLOCK;
    }

    public String getFileSuffix(String url) {

        File image = FileUtil.touch("images/tmpImage");
        HttpUtil.downloadFile(url, image);

        byte[] byteType = new byte[3];
        BufferedInputStream fileInputStream = FileUtil.getInputStream(image);
        try {
            fileInputStream.read(byteType, 0, byteType.length);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        String hex = bytesToHexString(byteType).toUpperCase();

        return FileType.getSuffix(hex);
    }

}
