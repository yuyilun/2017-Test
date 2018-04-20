package com.mybatis.orm.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import com.mybatis.orm.core.bean.Function;
import com.mybatis.orm.core.bean.MapperBean;
import com.mybatis.orm.core.configuration.Configuration;

public class MapperProxy implements InvocationHandler {

	private  Sqlsession sqlSession;
	private Configuration configuration;
	
	public MapperProxy(Configuration configuration,Sqlsession sqlSession) {
		this.configuration = configuration;
		this.sqlSession = sqlSession;
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		MapperBean readMapper = configuration.readMapper("UserMapper.xml");
		//是否是xml文件对应的接口
		if(!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())){
			return null;  
		}
		List<Function> list = readMapper.getList();
		if(null != list || 0 != list.size()) {
			for(Function function : list) {
				if(method.getName().equals(function.getFunName())) {
					return sqlSession.selectOne(function.getSql(), String.valueOf(args[0]));
				}
			}
		}
		return null;
	}

}
