package cn.test.springmvc.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 逻辑层注解
 * @author xyd-yuyilun
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Service {
	String value() default "";
}
