package moe.iacg.miraiboot.annotation;

import moe.iacg.miraiboot.enums.MessageType;

import java.lang.annotation.*;

/**
 * @author Ghost
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommandPrefix {
    String command() default "";

}
