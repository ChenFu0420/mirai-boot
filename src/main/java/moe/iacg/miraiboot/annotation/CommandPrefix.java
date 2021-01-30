package moe.iacg.miraiboot.annotation;


import moe.iacg.miraiboot.enums.Commands;

import java.lang.annotation.*;

/**
 * @author Ghost
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommandPrefix {
    Commands command();

    String prefix() default "/";

    String[] alias() default {};

}
