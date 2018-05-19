package cn.test.beanValidation;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;

import org.junit.Test;
/**
 * 使用beanValidation
 * @author xyd-yuyilun
 *
 */
public class BeanValidatorTest {

	public static void main(String[] args) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		
		//验证类
		Car car =new Car();
		Set<ConstraintViolation<Car>> beanValidates = validator.validate(car);
		for(ConstraintViolation<Car> constraintViolation : beanValidates) {
			//打印验证后的信息
			System.out.println(constraintViolation.getMessage());
		}
		
		//验证方法
		Method method = null;
		try {
			method = Car.class.getMethod("drive", int.class);
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		}
		Object[] parameterValues = {80};
		ExecutableValidator forExecutables = validator.forExecutables();
		Set<ConstraintViolation<Car>> validateParameters = forExecutables.validateParameters(car, method, parameterValues);
		for(ConstraintViolation<Car> constraintViolation : validateParameters) {
			System.out.println(constraintViolation.getMessage());
		}

	}
	@Test
	public void test() {
		try {
			BeanValidator.validate(new Car());
		}catch (Exception e) {
			assertEquals("车主不能为空",e.getMessage());
		}
	}
	
	
	public static class Car {
	
	    private String name;
	
	    @NotNull(message = "车主不能为空")
	    public String getRentalStation() {
	        return name;
	    }
	
	    public void drive(@Max(75) int speedInMph) {
	
	    }
	
	  }
	
	  
	  
}
