package com.ktjiaoyu.demo.mapper;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface OperLog {
    String operModel() default ""; // 操作模块
    String operType() default "";  // 操作类型
    String operDescs() default "";  // 操作说明
}
