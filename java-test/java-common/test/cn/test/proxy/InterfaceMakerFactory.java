package cn.test.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
/**
 * �������ɽӿڣ���ȡĳ����ķ������ɽӿ�
 * ֻ��ȡ����
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
	 * �������ɽӿ�
	 * @return
	 */
	public Class<?> getTargetInterface(){
		InterfaceMaker interfaceMaker =new InterfaceMaker();  
        //��ȡĳ����ķ������ɽӿڷ���
        interfaceMaker.add(clazz);  
		return interfaceMaker.create();
	}
	
	
	
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {  
        InterfaceMaker interfaceMaker =new InterfaceMaker();  
        //��ȡĳ����ķ������ɽӿڷ���  
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
        //�Խӿڽ��д��������ô���ӿڷ������� 
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
