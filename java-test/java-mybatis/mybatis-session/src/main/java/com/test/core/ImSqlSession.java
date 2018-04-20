package com.test.core;

import java.lang.reflect.Proxy;

/**
 * sql会话
 * @author yuyilun
 *
 */
public class ImSqlSession {
	
	private Excutor excutor = new SimpleExcutor();
	
	public <T> T selectOne(String statement , Object parameter) {
		return excutor.query(statement,parameter);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T getMapper(Class<T> clazz) {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, 
				new MyMapperHandler(this));
	}
	
}
