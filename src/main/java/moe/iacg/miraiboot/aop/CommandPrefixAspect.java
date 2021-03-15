package moe.iacg.miraiboot.aop;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.constants.MsgTypeConstant;
import moe.iacg.miraiboot.utils.BotUtils;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Aspect
@Component
@Slf4j
public class CommandPrefixAspect {


    /**
     * AOP中扫描指定注解相关说明
     * （1）@annotation：用来拦截所有被某个注解修饰的方法
     * （2）@within：用来拦截所有被某个注解修饰的类
     * （3）within：用来指定扫描的包的范围
     */
    @Pointcut("@within(moe.iacg.miraiboot.annotation.CommandPrefix)")
    private void aspectPointcut() {

    }

    @SneakyThrows
    @Around("aspectPointcut()")
    public Object doAround(final ProceedingJoinPoint joinPoint) {
        var commandPrefix = AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(), CommandPrefix.class);
        var command = commandPrefix.command().getCommand();
        var prefix = commandPrefix.prefix();
        var alias = commandPrefix.alias();

        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return BotPlugin.MESSAGE_IGNORE;

        }

        Object event = args[1];
        String prefixCommand = prefix + command;
        if (event instanceof OnebotEvent.PrivateMessageEvent) {
            //私聊消息
            List<String> texts = BotUtils.getMessageForType(((OnebotEvent.PrivateMessageEvent) event).getMessageList(),  MsgTypeConstant.TEXT);
            if (!CollectionUtils.isEmpty(texts)) {
                return judge(joinPoint, prefixCommand, alias, texts.get(0));
            }else {
                return judge(joinPoint, prefixCommand, alias, "");
            }
        } else {
            //群消息处理
            List<String> texts = BotUtils.getMessageForType(((OnebotEvent.GroupMessageEvent) event).getMessageList(), MsgTypeConstant.TEXT);
            if (!CollectionUtils.isEmpty(texts)) {
                return judge(joinPoint, prefixCommand, alias, texts.get(0));
            }else {
                return judge(joinPoint, prefixCommand, alias, "");
            }
        }
    }

    @SneakyThrows
    private Object judge(ProceedingJoinPoint joinPoint, String command, String[] alias, String rawMessage) {
        return rawMessage.startsWith(command) || StrUtil.containsAny(rawMessage, alias) ? joinPoint.proceed() : BotPlugin.MESSAGE_IGNORE;
    }
}
