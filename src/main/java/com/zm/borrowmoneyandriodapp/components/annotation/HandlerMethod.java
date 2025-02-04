package com.zm.borrowmoneyandriodapp.components.annotation;

import java.lang.annotation.*;

/**
 * @Describle This Class Is 全局日志记录注解类
 * @Author ZengMin
 * @Date 2019/1/13 16:43
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HandlerMethod {

    String optName() default  "";

    String optDesc() default  "";

}
