package cn.test.springmvc.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 参数注解
 * @author xyd-yuyilun
 */
@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface RequestParam {
	String value() default "";
	boolean required() default true;
}

