package site.gladmin.aop.annotation;

import java.lang.annotation.*;

/**
 * @Author: gladmin
 * @Date: 2020/3/29 4:13 下午
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExportParam {
}
