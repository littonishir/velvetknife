package com.littonishir.velvetknife.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by littonishir on 2018/9/13.
 * Check if the network is connected
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {
    String netErrMsg()default "";
    boolean showToast() default true;
}
