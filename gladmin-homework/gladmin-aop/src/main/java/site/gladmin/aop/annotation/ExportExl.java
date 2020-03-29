package site.gladmin.aop.annotation;

import java.lang.annotation.*;

/**
 * @Author: gladmin
 * @Date: 2020/3/29 3:55 下午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExportExl {

    int aopLimit() default 2000;


    int aopSize() default 500;
}
