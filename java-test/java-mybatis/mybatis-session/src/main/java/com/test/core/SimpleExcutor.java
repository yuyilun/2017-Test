package com.test.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.test.pojo.User;

public class SimpleExcutor implements Excutor {

	@SuppressWarnings("unchecked")
	public <T> T query(String statement, Object parameter) {
		
		Connection connection = null;
		@SuppressWarnings("unused")
		PreparedStatement preparedStatement = null;
		connection = getConnection();
		
		try {
			PreparedStatement pre = connection.prepareStatement(statement);
			ResultSet resultSet = pre.executeQuery();
			User u = new User();
			
			while(resultSet.next()) {
				u.setName(resultSet.getString(0));
			}
			return (T)u;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Connection getConnection() {
		
		String driver = "";
		String url = "";
		String username = "";
		String password = "";
		
		try {
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url,username,password);
			return connection;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
