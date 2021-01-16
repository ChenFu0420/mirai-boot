package moe.iacg.miraiboot.annotation;


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
