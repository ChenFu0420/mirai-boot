package moe.iacg.miraiboot.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommandPrefixAspect {

    @Pointcut("@annotation(moe.iacg.miraiboot.annotation.CommandPrefix)")
    private void aspectPointcut(){

    }

    @Before("aspectPointcut()")
    public void doBefore(){

    }
}
