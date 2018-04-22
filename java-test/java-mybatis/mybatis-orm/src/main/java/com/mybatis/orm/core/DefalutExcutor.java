package com.mybatis.orm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mybatis.orm.core.configuration.Configuration;
import com.mybatis.orm.pojo.User;

public class DefalutExcutor implements Excutor {
	
	private Configuration configuration = new Configuration();
	

	@Override
	public <T> T query(String statement, Object parameter) {
		Connection connection = getConnection();
		
		ResultSet set = null;
		PreparedStatement pre =null;
		
		try {
			pre = connection.prepareStatement(statement);
			pre.setString(1,  parameter.toString());
			set = pre.executeQuery();
			User u =new User();
			while(set.next()) {
				u.setId(set.getInt(1));
				u.setUsername(set.getString(2));
				u.setPassword(set.getString(3));
			}
			return (T)u;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			 try{  
                 if(set!=null){  
              	   set.close();  
                 }if(pre!=null){  
              	   pre.close();  
                 }if(connection!=null){  
              	   connection.close();  
                 }  
             }catch(Exception e2){  
                 e2.printStackTrace();  
             }  
		}
		return null;
	}


	private Connection getConnection() {
		Connection connection = configuration.build("database.xml");
		return connection;
	}

}
