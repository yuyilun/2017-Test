package cn.test.springmvc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.test.springmvc.annotation.Autowired;
import cn.test.springmvc.annotation.Controller;
import cn.test.springmvc.annotation.RequestMapping;
import cn.test.springmvc.annotation.RequestParam;
import cn.test.springmvc.annotation.Service;
import cn.test.springmvc.util.Tools;

/**
 * 初始化-分发类
 * @author xyd-yuyilun
 *
 */
public class DispatcherServlet extends HttpServlet{

	/**
	 */
	private static final long serialVersionUID = 4903427425441462334L;
	
	//存放扫描类
	private List<String> classNameList = new ArrayList<>();
	//存放实例化对象
	private Map<String, Object> instanceMap = new HashMap<>();
	//
	private Map<String, HandlerModel> handlerMapping = new HashMap<>();
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//扫描
		scanPackage(config.getInitParameter("scanPackage"));
		
		//实例化
		doInstance();
		
		//自动注入
		doAutoWired();
		
		//处理映射请求
		doHandlerMapping();
		System.out.println("init success");
	}
	
	/**
	 *	内部类
	 * @author xyd-yuyilun
	 *
	 */
	private class HandlerModel {
		Method method;//方法
		Object controller;//对象
		Map<String, Integer> paramMap;//参数
		
		public HandlerModel(Method method, Object controller, Map<String, Integer> paramMap) {  
            this.method = method;  
            this.controller = controller;  
            this.paramMap = paramMap;  
        }  
		
	}
	
	/**
	 * 处理映射请求
	 */
	private void doHandlerMapping() {
		if(instanceMap.isEmpty()) {
			return;
		}
		for(Map.Entry<String , Object> entry : instanceMap.entrySet()) {
			
			Class<?> clazz = entry.getValue().getClass();
			if(!clazz.isAnnotationPresent(Controller.class)) {
				continue;
			}
			String url = "/";
			//判断类上面的注解是：RequestMapping
			if(clazz.isAnnotationPresent(RequestMapping.class)) {
				//获取注解的值
				RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
				url += requestMapping.value();
			}
			
			//获取类的方法
			Method[] methods = clazz.getMethods(); 
			
			for(Method method : methods) {
				if(!method.isAnnotationPresent(RequestMapping.class)) {
					//判断方法上面的注解是：RequestMapping
					continue;
				}
				// 将多个斜线"/"变成一个
				RequestMapping  requestMapping= method.getAnnotation(RequestMapping.class);
				String realUrl = url + "/" + requestMapping.value();
				realUrl = realUrl.replaceAll("/+", "/");
				
				//获取每个方法的的参数注解，一个参数可能有多个注解
				Annotation[][] parameterAnnotations = method.getParameterAnnotations();
				Map<String, Integer> paramMap = new HashMap<>();
				
				//
				String[] paramNames = Tools.getMethodParameterNamesByAsm4(clazz, method); 
				
				//获取方法参数类型
				Class<?>[] paramTypes = method.getParameterTypes();
				
				for(int i=0; i<parameterAnnotations.length; i++) {
					Annotation[] annotations = parameterAnnotations[i];
					if(annotations.length == 0) {
						//表示没有注解
						//从参数类型数组中，获取方法的参数类型
						Class<?> type = paramTypes[i];
						//如果是HttpServletRequest，HttpServletResponse这两种类型的参数，直接放入参数结合中
						if(type == HttpServletRequest.class || type == HttpServletResponse.class) {
							paramMap.put(type.getName(), i);
						}else {
							//否则，从paramNames数组中获取参数类型
							paramMap.put(paramNames[i], i);
						}
						continue;
					}
					//循环注解
					for(Annotation ans : annotations) {
						//判断注解类型为RequestParam
						if(ans.annotationType() == RequestParam.class) {
							//获取注解的值
							String paramName  = ((RequestParam)ans).value();
							if(!"".equals(paramName.trim())) {
								paramMap.put(paramName, i);
							}
						}
					}
				}
				HandlerModel model = new HandlerModel(method, entry.getValue(), paramMap);  
				handlerMapping.put(realUrl, model);
			}
		}
	}


	/**
	 * 自动注入
	 */
	private void doAutoWired() {
		if(instanceMap.isEmpty()) {
			return;
		}
		for(Map.Entry<String, Object> entry : instanceMap.entrySet()) {
			Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
			for(Field field : declaredFields) {
				if(!field.isAnnotationPresent(Autowired.class)) {
					continue;
				}
				String beanName;
				Autowired autowired = field.getAnnotation(Autowired.class);
				if("".equals(autowired.value())) {
					beanName = lowerFirstChar(field.getType().getSimpleName());
				}else {
					beanName = autowired.value();
				}
				field.setAccessible(true);
				if(instanceMap.get(beanName) != null) {
					try {
						field.set(entry.getValue(), instanceMap.get(beanName));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 实例化类
	 */
	private void doInstance() {
		if(classNameList.size() == 0) {
			return;
		}
		
		for(String className : classNameList) {
			try {
				Class<?> clazz = Class.forName(className);
				if(clazz.isAnnotationPresent(Controller.class)) {
					instanceMap.put(lowerFirstChar(clazz.getSimpleName()), clazz.newInstance());
				}else if(clazz.isAnnotationPresent(Service.class)){
					Service service = clazz.getAnnotation(Service.class);
					String value = service.value();
					if(!"".equals(value)) {
						instanceMap.put(value.trim(), clazz.newInstance());
					}else {
						Class<?>[] interfaces = clazz.getInterfaces();
						for(@SuppressWarnings("rawtypes") Class inter : interfaces) {
							instanceMap.put(lowerFirstChar(inter.getSimpleName()), clazz.newInstance());
							break;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 首字母变小写
	 * @param simpleName
	 * @return
	 */
	private String lowerFirstChar(String simpleName) {
		char[] charArray = simpleName.toCharArray();
		charArray[0] += 32;//将className首大写字母变成小写字母
		return String.valueOf(charArray);
	}

	/**
	 * 扫描包目录下所有的类
	 * @param initParameter
	 */
	private void scanPackage(String packageName) {
		URL resource = getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.","/"));
		File dir = new File(resource.getFile());
		
		for(File file : dir.listFiles()) {
			
			if(file.isDirectory()) {
				scanPackage(packageName + "." + file.getName());
			}else {
				if(!file.getName().endsWith(".class")) {
					continue;
				}
				String className = packageName + "." + file.getName().replace(".class", ""); 
				try {
					Class<?> clazz = Class.forName(className);
					if(clazz.isAnnotationPresent(Controller.class) 
							|| clazz.isAnnotationPresent(Service.class)) {
						classNameList.add(className);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
			}
			
			
		}
		
		
		
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//根据请求的URL去查找对应的method  
		 try {  
	            boolean isMatcher = pattern(req, resp);  
	            if (!isMatcher) {  
	                Tools.out(resp,"404 not found");  
	            }  
	        } catch (Exception ex) {  
	            ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();  
	            ex.printStackTrace(new java.io.PrintWriter(buf, true));  
	            String expMessage = buf.toString();  
	            buf.close();  
	            Tools.out(resp, "500 Exception" + "\n" + expMessage);  
	        }  
	}
	
	
	
	/**
	 * 匹配方法
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private boolean pattern(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		if(handlerMapping.isEmpty()) {
			return false;
		}
		
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		
		requestURI = requestURI.replace(contextPath, "").replaceAll("/+", "/");
		
		for(Map.Entry<String, HandlerModel> entry : handlerMapping.entrySet()) {
			 if (entry.getKey().equals(requestURI)) {  
				 HandlerModel handlerModel = entry.getValue();  
				 
				 Map<String, Integer> paramIndexMap = handlerModel.paramMap;  
				 Object[] paramValues = new Object[paramIndexMap.size()]; 
				 
				 Class<?>[] types = handlerModel.method.getParameterTypes();  
				 
				 for (Map.Entry<String, Integer> param : paramIndexMap.entrySet()) {  
			    	 String key = param.getKey();  
			    	 if (key.equals(HttpServletRequest.class.getName())) {  
	                     paramValues[param.getValue()] = req;  
	                 } else if (key.equals(HttpServletResponse.class.getName())) {  
	                     paramValues[param.getValue()] = resp;  
	                 } else {  
	                     //如果用户传了参数，譬如 name= "wolf"，做一下参数类型转换，将用户传来的值转为方法中参数的类型  
	                     String parameter = req.getParameter(key);  
	                     if (parameter != null) {  
	                         paramValues[param.getValue()] = Tools.convert(parameter.trim(), types[param.getValue()]);  
	                     }  
	                 }  
				 }
				 //激活该方法  
                handlerModel.method.invoke(handlerModel.controller, paramValues);  
	            return true; 
		    }
		}
		
		return false;
	}

}
