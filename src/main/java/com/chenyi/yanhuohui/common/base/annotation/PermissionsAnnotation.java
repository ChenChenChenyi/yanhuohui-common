package com.chenyi.yanhuohui.common.base.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionsAnnotation {
    //可以自定义一个防止重复访问的注解，在切面类的before方法里给该接口加redis锁，afterReturning里面解锁
    String value();
}
