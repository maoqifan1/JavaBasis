package com.maoqifan.demo.pojo;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Documented
public @interface UserAnnotation {
    String name() default "";
    String dictValue() default "";
}
