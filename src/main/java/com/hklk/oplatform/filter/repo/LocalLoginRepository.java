package com.hklk.oplatform.filter.repo;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author 曹良峰
 * @Description 标识需要拦截的类
 * @Date 10:59 2018/5/28
 * @Param
 * @Return 
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface LocalLoginRepository {
    String value() default "";
}