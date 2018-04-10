package cn.test.proxy;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

import sun.misc.ProxyGenerator;
/**
 * java jdk动态代理,利用反射实现
 * @author xyd-yuyilun
 *
 */
public class ProxyFactory implements InvocationHandler{
	private Class<?> target;
	private Object real;
	
	public ProxyFactory(Class<?> target) {
		this.target=target;
	}
	
	public Object bind(Object real) {
		this.real=real;
		return Proxy.newProxyInstance(target.getClassLoader(), new Class[] {target} , this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("begin ");
		Object invoke = method.invoke(real , args);
		System.out.println("end ");
		return invoke;
	}
	
	public static void main(String[] args) throws Exception {
		Calculator cl = (Calculator)new ProxyFactory(Calculator.class).bind(new Calculator.CalculatorImpl());
		System.out.println(cl.add(1, 2));
		
		Calculator cl2 = (Calculator)new ProxyFactory(Calculator.class).bind(new CalculatorGImpl());
		System.out.println(cl2.add(0, 2));
		
		generateClassFile(Calculator.class, "Calcultor$ProxyCode");
		
	}
	
	@SuppressWarnings("rawtypes")
	public static void generateClassFile(Class clazz,String proxyName) throws Exception {
		
		byte[] proxyClass = ProxyGenerator.generateProxyClass(proxyName, new Class[]{clazz});
		
		String path = clazz.getResource(".").getPath();
		
		System.out.println(path);
		
		FileOutputStream out = null;     
	    //保留到硬盘中  
        out = new FileOutputStream(path+proxyName+".class");    
        out.write(proxyClass);    
        out.flush();  
        out.close();
		
	}
	
	
	@Test
	public static void genClassFile() throws Exception {
		generateClassFile(Calculator.class, "Calcultor$ProxyCode");
	}
	
	
}
