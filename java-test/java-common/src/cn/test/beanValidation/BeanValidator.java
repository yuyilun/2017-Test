package cn.test.beanValidation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

/**
 * 对象验证器
 * @author xyd-yuyilun
 *
 */
public class BeanValidator {
	
	public static <T> void validate(T object) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		
		Set<ConstraintViolation<T>> constraintViolations  = validator.validate(object);
		
		ConstraintViolation<T> constraintViolation = getFirst(constraintViolations,null);
		
		if (constraintViolation != null) {
            throw new ValidationException(constraintViolation.getMessage());
        }
		
		
		
	}
	
	/**
	 * 取第一个值
	 * @param constraintViolations
	 * @param object
	 * @return
	 */
	private static <T> ConstraintViolation<T> getFirst(Set<ConstraintViolation<T>> constraintViolations, Object object) {
		if(constraintViolations.size() < 1 ) {
			return null;
		}
		for(ConstraintViolation<T> constraintViolation : constraintViolations) {
			return constraintViolation;
		}
		return null;
	}
}
