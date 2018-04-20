package com.mybatis.orm.core;

import java.lang.reflect.Proxy;

import com.mybatis.orm.core.configuration.Configuration;

public class Sqlsession {
	
	private Excutor excutor = new DefalutExcutor();
	
	private Configuration configuration = new Configuration();
	
	public <T> T selectOne(String statement,Object parameter){
		return excutor.query(statement, parameter);
	}
	
	public <T> T getMapper(Class<T> clazz){
		return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, 
				new MapperProxy(configuration,this)
				);
	}
	

}
