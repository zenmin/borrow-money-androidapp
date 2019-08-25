package com.zm.borrowmoneyandriodapp.components.annotation;

import java.lang.annotation.*;

/**
 * @Describle This Class Is 权限注解类
 * @Author ZengMin
 * @Date 2019/6/29 16:43
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRole {

    String value() default "1";

}
