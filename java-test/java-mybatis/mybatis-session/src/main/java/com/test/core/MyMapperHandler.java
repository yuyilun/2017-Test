package com.test.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyMapperHandler<T> implements InvocationHandler {
	
	private ImSqlSession sqlSession;
	
	public MyMapperHandler(ImSqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(method.getDeclaringClass().getName().equals("namespace")) {
			String sql = "sql";
			
			return sqlSession.selectOne(sql, String.valueOf(args[0]));
		}
		return null;
	}

}
