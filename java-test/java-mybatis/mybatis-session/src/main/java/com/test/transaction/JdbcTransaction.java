package com.test.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionException;

/**
 * JdbcTransaction实现类：Transaction的实现类，通过使用jdbc提供的方式来管理事务，通过Connection提供的事务管理方法来进行事务管理
 * @author yuyilun
 *
 */
public class JdbcTransaction implements Transaction {
	
	private static final Log log = LogFactory.getLog(JdbcTransaction.class);
	
	//连接
	protected Connection connection;
	//数据源
	protected DataSource dataSource;
	//事务等级
	protected TransactionIsolationLevel level;
	//事务提交
	protected boolean autoCommit;
	
	
	
	public JdbcTransaction(Connection connection) {
		this.connection = connection;
	}


	public JdbcTransaction(DataSource dataSource, TransactionIsolationLevel level,
			boolean autoCommit) {
		this.dataSource = dataSource;
		this.level = level;
		this.autoCommit = autoCommit;
	}


	public Connection getConnection() throws SQLException {
		if(connection == null) {
			openConnection();
		}
		return connection;
	}
	
	/**
	 * 打开数据库连接
	 * @throws SQLException 
	 * 
	 */
	protected void openConnection() throws SQLException {
		if(log.isDebugEnabled()) {
			log.debug("Opening JDBC Connection");
		}
		
		connection = dataSource.getConnection();
		if(level != null) {
			//设置数据库事务级别
			connection.setTransactionIsolation(level.getLevel());
		}
		//设置自动提交
		setDesireAutoCommit(autoCommit);
	}

	/**
	 * 事务提交状态不一致时修改
	 * @param desireAutoCommit
	 */
	protected void setDesireAutoCommit(boolean desireAutoCommit) {
		try {
			if(connection.getAutoCommit() != desireAutoCommit) {
				if(log.isDebugEnabled()) {
					log.debug("Setting autocommit to " + desireAutoCommit + " on JDBC Connection [" + connection + "]");  
				}
				connection.setAutoCommit(desireAutoCommit);
				
			}
		} catch (SQLException e) {
			throw new TransactionException("Error configuring AutoCommit.  "  
			          + "Your driver may not support getAutoCommit() or setAutoCommit(). "  
			          + "Requested setting: " + desireAutoCommit + ".  Cause: " + e, e);  
		}
		
		
	}

	
	public void commit() throws SQLException {
		if(connection != null &&  !connection.getAutoCommit()) {
			if(log.isDebugEnabled()) {
				log.debug("Committing JDBC Connection [" + connection + "]");  
			}
			connection.commit();
		}
		
	}

	public void rollback() throws SQLException {
		if (connection != null && !connection.getAutoCommit()) {  
	      if (log.isDebugEnabled()) {  
	        log.debug("Rolling back JDBC Connection [" + connection + "]");  
	      }  
	      //连接回滚  
	      connection.rollback();  
	    }  
		
	}

	public void close() throws SQLException {
		if(connection != null) {
			resetAutoCommit();
			 if (log.isDebugEnabled()) {  
		        log.debug("Closing JDBC Connection [" + connection + "]");  
		      }  
		      //关闭连接 
			 connection.close();
		}
	}
	
	protected void resetAutoCommit() {
		try {
			if(!connection.getAutoCommit()) {
				if(log.isDebugEnabled()) {
					 log.debug("Resetting autocommit to true on JDBC Connection [" + connection + "]");  
				}
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			if (log.isDebugEnabled()) {  
		        log.debug("Error resetting autocommit to true "  
		          + "before closing the connection.  Cause: " + e);  
		     } 
		}
	}


	public Integer getTimeout() throws SQLException {
		return null;
	}
	
	
	
	
}
