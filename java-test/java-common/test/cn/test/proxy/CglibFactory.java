package cn.test.proxy;

import java.lang.reflect.Method;
import org.junit.Test;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.LazyLoader;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;
import net.sf.cglib.proxy.ProxyRefDispatcher;
/**
 * 详细探究cglib的使用，底层使用asm字节码技术来实现动态代理。
 * MethodInterceptor:方法拦截器，与InvocationHandler不同，多了个MethodProxy，有2个方法
 * InvocationHandler:方法拦截器
 * FixedValue:可以在拦截器中指定返回固定值；
 * NoOp: NoOp.INSTANCE,把对回调方法的调用直接委派到这个方法的父类，即不进行拦截
 * LazyLoader：当实际对象需要延迟加载时，可以使用LazyLoader回调。一旦实际对象被装载，它将被每一个调用代理对象的方法使用（即实际对象被访问时才调用此回调callback的loadObject方法）
 * Dispatcher：和LazyLoader回调具有相同的特点，区别是当代理方法被调用时，装载对象的方法也总是要被调用
 * ProxyRefDispatcher：与Dispatcher一样，只不过可以把代理对象作为参数进行传递
 * @author xyd-yuyilun
 *
 */
public class CglibFactory {
	
	public static void main(String[] args) {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "target/classes");
		Enhancer enhancer2=new Enhancer();
		enhancer2.setSuperclass(AbstractCalculator.class);
		enhancer2.setCallbacks(
				new Callback[]{new MethodInterceptor() {
			@Override//0
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				System.out.println("begin0");
				Object invoke = proxy.invoke(new CalculatorGImpl(), args);//传入增强类，被代理类
	            //Object invoke= proxy.invokeSuper(obj, args);//传入代理类
	            System.out.println("end0");
	            return	invoke;
			}},new InvocationHandler() {
				@Override//1
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					System.out.println("begin1");
					Object invoke=method.invoke(new CalculatorGImpl(), args);
					System.out.println("end1");
					return invoke;
			}},new FixedValue() {
				@Override//2
				public Object loadObject() throws Exception {
					return 999;
				}
				//3	
			},NoOp.INSTANCE,
				new LazyLoader() {
				@Override//4
				public Object loadObject() throws Exception {
					System.out.println(" LazyLoader ");
					//可以对其他的对象，进行懒加载
					OBean obean=new OBean();
					obean.setAge(18);
					obean.setName("nice");
					AbstractCalculator ac=new AbstractCalculator();
					ac.setObean(obean);
					System.out.println(" LazyLoader end");
					return ac;
				}
			},new Dispatcher() {
				@Override//5
				public Object loadObject() throws Exception {
					return null;
				}
				
			},new ProxyRefDispatcher() {
				@Override//6
				public Object loadObject(Object proxy) throws Exception {
					return null;
				}
				
			}});
		
		enhancer2.setCallbackFilter(new CallbackFilter() {
			@Override
			public int accept(Method method) {
				if(method.getName().equals("add")) {
					return 0;
				}
				if(method.getName().equals("reduce")) {
					return 4;
				}
				return 1;
			}
		});
		AbstractCalculator proxy2 =(AbstractCalculator) enhancer2.create();
		
		System.out.println(proxy2.add(1,2));
		System.out.println(proxy2.reduce(1,2));
		OBean obean = proxy2.getObean();
		System.out.println(obean.getAge());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void testNoOperator() {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "target/classes");
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(Calculator.class);
		enhancer.setCallback(NoOp.INSTANCE);
		Calculator proxy =(Calculator) enhancer.create();
		proxy.add(1,2);
	}
	
	
	
	@Test
	public void testMethodInterceptor() {
		//设置class文件输出位置
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "target/classes");
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(Calculator.class);
		//方法拦截器
		enhancer.setCallback(new MethodInterceptor() {
	        //类似invokerhanddler的invoke方法
	        @Override
	        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
	            System.out.println("begin");
	            Object invoke = methodProxy.invoke(new CalculatorGImpl(), objects);
	            System.out.println("end");
	            return invoke;
	        }
		});
		Calculator proxy =(Calculator) enhancer.create();
		proxy.add(1,2);
	}
	
	
	
	
	
	
	
	
	
}
