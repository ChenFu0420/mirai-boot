package moe.iacg.miraiboot.aop;

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
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     */
    @SneakyThrows
    public static Map<String, Object> getMethodInfo(JoinPoint joinPoint) {

        Map<String, Object> map = new HashMap<>(16);
        //获取目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取相关参数
        Object[] arguments = joinPoint.getArgs();
        //生成类对象
        Class<?> targetClass = Class.forName(targetName);
        //获取该类中的方法
        Method[] methods = targetClass.getMethods();

        String command;

        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            Class[] clazzs = method.getParameterTypes();
            if (clazzs.length != arguments.length) {
                //比较方法中参数个数与从切点中获取的参数个数是否相同，原因是方法可以重载哦
                continue;
            }

//            command = targetClass.getAnnotation(CommandPrefix.class).command();
            CommandPrefix commandPrefix = method.getAnnotation(CommandPrefix.class);

            String prefix = commandPrefix.prefix();
            command = commandPrefix.command().getCommand();
            map.put("command", command);
            map.put("prefix", prefix);

        }
        return map;
    }

    @Pointcut("@annotation(moe.iacg.miraiboot.annotation.CommandPrefix)")
    private void aspectPointcut() {

    }

    @SneakyThrows
    @Around("aspectPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        var methodInfo = getMethodInfo(joinPoint);
        var command = (String) methodInfo.get("command");
        var prefix = (String) methodInfo.get("prefix");
        Object event = joinPoint.getArgs()[1];
        String prefixCommand = prefix + command;
        if (event instanceof OnebotEvent.PrivateMessageEvent) {

            //私聊消息
            return judge(joinPoint,prefixCommand, ((OnebotEvent.PrivateMessageEvent) event).getRawMessage());
        } else {
            //群消息处理
            return judge(joinPoint, prefixCommand, ((OnebotEvent.GroupMessageEvent) event).getRawMessage());
        }
//        log.info("响应命令：{} 内容：{}",prefixCommand);
    }

    @SneakyThrows
    private Object judge(ProceedingJoinPoint joinPoint, String command, String rawMessage) {
        return rawMessage.startsWith(command) ? joinPoint.proceed() : BotPlugin.MESSAGE_IGNORE;
    }
}
