package cn.test.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
/**
 * 将类代理成接口：抽取某个类的方法生成接口
 * 只抽取方法
 * @author xyd-yuyilun
 *
 */
public class InterfaceMakerFactory {
	
	private Class<?> clazz;
	public InterfaceMakerFactory() {
		
	}
	
	public void setClass(Class<?> clazz) {
		this.clazz=clazz;
	}
	
	/**
	 * 将类抽象成接口
	 * @return
	 */
	public Class<?> getTargetInterface(){
		InterfaceMaker interfaceMaker =new InterfaceMaker();  
        //抽取某个类的方法生成接口方法
        interfaceMaker.add(clazz);  
		return interfaceMaker.create();
	}
	
	
	
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {  
        InterfaceMaker interfaceMaker =new InterfaceMaker();  
        //抽取某个类的方法生成接口方法  
        interfaceMaker.add(AbstractCalculator.class);  
        Class<?> targetInterface=interfaceMaker.create();
        for(Method method : targetInterface.getMethods()){  
            System.out.println(
            		"IsInterface:"+targetInterface.isInterface()
            		+" 	IsClass:"+targetInterface.equals(Object.class)
            		+" 	Name:"+targetInterface.getName()
            		+"  Method:"+method.getName()
            );  
        }  
        //对接口进行代理，并设置代理接口方法拦截 
        Object object = Enhancer.create(Object.class, 
        		new Class[]{targetInterface}, 
        		new MethodInterceptor(){  
            @Override  
            public Object intercept(Object obj, Method method, Object[] args,  
                    MethodProxy methodProxy) throws Throwable {  
                if(method.getName().equals("add")){  
                    System.out.println("add: parameters a add parameters b,a+b");
                    int ret=0;
                    for(Object ob:args) {
                    	if(ob.getClass().equals(Integer.class)) {
                    		ret=ret + (int)ob;
                    	}
                    }
                    return ret;  
                }  
                if(method.getName().equals("reduce")){  
                    System.out.println("reduce: parameters a reduce parameters b,a-b ");  
                    int ret=0;
                    if(args.length == 2) {
                    	if(args[0].getClass().equals(Integer.class) &&
                    			args[1].getClass().equals(Integer.class)) 
                    	{
                    		ret=(int)args[0] - (int)args[1];
                    	}
                    }
                    return ret;  
                }  
                
                return "default";  
            }});  
        Method targetMethod1=object.getClass().getMethod("reduce",new Class[]{int.class,int.class});  
        System.out.println(targetMethod1.invoke(object, new Object[]{33,12}));
        
        Method targetMethod=object.getClass().getMethod("add",new Class[]{int.class,int.class});  
        System.out.println(targetMethod.invoke(object, new Object[]{1,2}));  
        
       /* Method method = object.getClass().getMethod("getObean", null);
        System.out.println( method.invoke(object, null));*/
    }
}
