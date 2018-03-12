package cn.test.springmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动注入注解
 * @author xyd-yuyilun
 *
 */
@Target({ElementType.FIELD}) //规定注解的作用范围
@Retention(RetentionPolicy.RUNTIME) //规定注解的生命周期
@Documented    //注解是否将包含在JavaDoc中	
public @interface Autowired {
	String value() default "";
}
