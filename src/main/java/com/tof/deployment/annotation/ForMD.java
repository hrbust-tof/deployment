package com.tof.deployment.annotation;

import java.lang.annotation.*;

/**
 * WangSong
 * 2019-08-05
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ForMD {
    String value() default "";

    boolean includeFile() default false;
}
