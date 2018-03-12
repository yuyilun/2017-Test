package cn.test.springmvc.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServletResponse;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
/**
 * 工具类
 * @author xyd-yuyilun
 *
 */
public class Tools {
	
	/**
	 *  获取类中指定方法的参数名
	 * @param clazz
	 * @param method
	 * @return
	 */
	public static String[] getMethodParameterNamesByAsm4(Class<?> clazz, Method method) {
		//获取方法名
		String methodName = method.getName();
		//获取方法参数类型
		Class<?>[] methodParameterTypes = method.getParameterTypes();
		//表示几个参数
		int methodParameterlength = methodParameterTypes.length;
		// Method 对象表示的方法的类或接口的 Class 对象名词
		String className = method.getDeclaringClass().getName();
		//getModifiers(),获取修饰符；isStatic，判断是否静态修饰符
		boolean isStatic = Modifier.isStatic(method.getModifiers());
		String[] methodParametersNames = new String[methodParameterlength];
		
		int lastDoIndex = className.lastIndexOf(".");
		className = className.substring(lastDoIndex + 1) + ".class";
		
		InputStream inputStream = clazz.getResourceAsStream(className);
		
		try {
			ClassReader classReader = new ClassReader(inputStream);
			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			
			classReader.accept(new ClassVisitor(Opcodes.ASM5, classWriter) {
				public MethodVisitor visitMethod(int access, String name, String desc,
			            String signature, String[] exceptions) {
					MethodVisitor mv = cv.visitMethod(access, name, desc, signature,exceptions);
					//获取参数类型
					Type[] argumentTypes = Type.getArgumentTypes(desc);
					//类型不匹配
					if(!methodName.equals(name) || !matchTypes(argumentTypes, methodParameterTypes)) {
						return mv;
					}
					return new MethodVisitor(Opcodes.ASM5,mv) {
						@Override
						public void visitLocalVariable(String name, String desc, String signature, Label start,
								Label end, int index) {
							//如果是静态方法，第一个参数就是方法参数，非静态方法，则第一个参数是 this ,然后才是方法的参数  
							int methodParameterIndex = (isStatic ? index : index - 1);  
                            if (0 <= methodParameterIndex && methodParameterIndex < methodParameterlength) {  
                                methodParametersNames[methodParameterIndex] = name;  
                            }  
							
							super.visitLocalVariable(name, desc, signature, start, end, index);
						}
						
					};
				}	
			}, ClassReader.SKIP_CODE);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return methodParametersNames;
	}
	
	
	/**
	 * 
	 * @param argumentTypes
	 * @param methodParameterTypes
	 * @return
	 */
	private static boolean matchTypes(Type[] argumentTypes, Class<?>[] methodParameterTypes) {
		if(argumentTypes.length != methodParameterTypes.length) {
			return false;
		}
		for(int i=0;i<argumentTypes.length ;i++) {
			if(!Type.getType(methodParameterTypes[i]).equals(argumentTypes[i])) {
				return false;
			}
		}
		return true;
	}
	
	
	/** 
     * 将用户传来的参数转换为方法需要的参数类型 
     */  
    public static Object convert(String parameter, Class<?> targetType) {  
        if (targetType == String.class) {  
            return parameter;  
        } else if (targetType == Integer.class || targetType == int.class) {  
            return Integer.valueOf(parameter);  
        } else if (targetType == Long.class || targetType == long.class) {  
            return Long.valueOf(parameter);  
        } else if (targetType == Boolean.class || targetType == boolean.class) {  
            if (parameter.toLowerCase().equals("true") || parameter.equals("1")) {  
                return true;  
            } else if (parameter.toLowerCase().equals("false") || parameter.equals("0")) {  
                return false;  
            }  
            throw new RuntimeException("不支持的参数");  
        }  
        else {  
            //TODO 还有很多其他的类型，char、double之类的依次类推，也可以做List<>, Array, Map之类的转化  
            return null;  
        }  
    } 
    
    public static void out(HttpServletResponse resp, String str) {
		resp.setContentType("application/json;charset=utf-8");  
		try {
			resp.getWriter().print(str);
		} catch (IOException e) {
		}  
	}

}
