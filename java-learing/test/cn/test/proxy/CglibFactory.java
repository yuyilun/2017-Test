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
 * ��ϸ̽��cglib��ʹ�ã��ײ�ʹ��asm�ֽ��뼼����ʵ�ֶ�̬����
 * MethodInterceptor:��������������InvocationHandler��ͬ�����˸�MethodProxy����2������
 * InvocationHandler:����������
 * FixedValue:��������������ָ�����ع̶�ֵ��
 * NoOp: NoOp.INSTANCE,�ѶԻص������ĵ���ֱ��ί�ɵ���������ĸ��࣬������������
 * LazyLoader����ʵ�ʶ�����Ҫ�ӳټ���ʱ������ʹ��LazyLoader�ص���һ��ʵ�ʶ���װ�أ�������ÿһ�����ô������ķ���ʹ�ã���ʵ�ʶ��󱻷���ʱ�ŵ��ô˻ص�callback��loadObject������
 * Dispatcher����LazyLoader�ص�������ͬ���ص㣬�����ǵ�������������ʱ��װ�ض���ķ���Ҳ����Ҫ������
 * ProxyRefDispatcher����Dispatcherһ����ֻ�������԰Ѵ��������Ϊ�������д���
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
				Object invoke = proxy.invoke(new CalculatorGImpl(), args);//������ǿ�࣬��������
	            //Object invoke= proxy.invokeSuper(obj, args);//���������
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
					//���Զ������Ķ��󣬽���������
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
		//����class�ļ����λ��
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "target/classes");
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(Calculator.class);
		//����������
		enhancer.setCallback(new MethodInterceptor() {
	        //����invokerhanddler��invoke����
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
