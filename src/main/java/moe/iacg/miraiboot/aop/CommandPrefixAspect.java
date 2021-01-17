package moe.iacg.miraiboot.aop;

import cn.hutool.core.annotation.AnnotationUtil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import moe.iacg.miraiboot.annotation.CommandPrefix;
import moe.iacg.miraiboot.constants.Commands;
import moe.iacg.miraiboot.enums.MessageType;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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

        Object event = joinPoint.getArgs()[1];
        String prefixCommand = prefix + command;
        if (event instanceof OnebotEvent.PrivateMessageEvent) {

            //私聊消息
            return judge(joinPoint, prefixCommand, ((OnebotEvent.PrivateMessageEvent) event).getRawMessage());
        } else {
            //群消息处理
            return judge(joinPoint, prefixCommand, ((OnebotEvent.GroupMessageEvent) event).getRawMessage());
        }
    }

    @SneakyThrows
    private Object judge(ProceedingJoinPoint joinPoint, String command, String rawMessage) {
        return rawMessage.startsWith(command) ? joinPoint.proceed() : BotPlugin.MESSAGE_IGNORE;
    }
}
