package com.mybatis.orm.core.configuration;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mybatis.orm.core.bean.Function;
import com.mybatis.orm.core.bean.MapperBean;

/**
 * ���������ļ�
 * @author yuyilun
 *
 */
public class Configuration {
	
	
	private static ClassLoader loader = ClassLoader.getSystemClassLoader();
	
	/**
	 * ��ȡ����Դ
	 * @param resource
	 * @return
	 */
	public Connection build(String resource) {
		
		InputStream stream = loader.getResourceAsStream(resource);
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(stream);
			Element rootElement = document.getRootElement();
			return evalDataSource(rootElement);
		} catch (Exception e) {
			throw new RuntimeException("error occured while evaling xml" + resource);
		}
	}


	private Connection evalDataSource(Element node) throws ClassNotFoundException {
		
		
		if(!node.getName().equals("database")) {
			throw new RuntimeException("root should be <database>");
		}
		
		String driverClassName = null;
		String url = null;
		String username = null;
		String password = null;
		
		for(Object item : node.elements("property")) {
			Element i = (Element) item;
			String value = getValue(i);
			String name = i.attributeValue("name");
			
			if(name == null  || value == null) {
				throw new RuntimeException("[database]: <property> should contain name and value");
			}
			
			switch(name) {
				case "url" : url =value; break;
				case "username" : username = value; break;
				case "password" : password = value; break;
				case "driverClassName" : driverClassName = value; break; 
				
				default : throw new RuntimeException("[database]: <property> unknown name");
			}
		}
		
		Class.forName(driverClassName);
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	
	private String getValue(Element node) {
		return (node.hasContent() ? node.getText() : node.attributeValue("value"));
		
	}
	
	
	@SuppressWarnings("rawtypes")
	public MapperBean readMapper(String path) {
		MapperBean mapper = new MapperBean();
		try {
			InputStream inputStream = loader.getResourceAsStream(path);
			
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(inputStream);
			Element rootElement = document.getRootElement();
			
			mapper.setInterfaceName(rootElement.attributeValue("nameSpace").trim());
			
			List<Function> list = new ArrayList<Function>();
			
			for(Iterator rootIter  = rootElement.elementIterator();rootIter.hasNext();) {
				
				Function fun = new Function();
				
				Element e = (Element)rootIter.next();
				
				String sqlType = e.getName().trim();
				String funName = e.attributeValue("id").trim();
				String sql = e.getText().trim();
				String resultType = e.attributeValue("resultType").trim();
				
				fun.setFunName(funName);
				fun.setSqlType(sqlType);
				
				Object newInstance = null;
				try {
					newInstance = Class.forName(resultType).newInstance();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				
				fun.setResutlType(newInstance);
				fun.setSql(sql);
				list.add(fun);
			}
			mapper.setList(list);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return mapper;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
