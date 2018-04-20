package com.test.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;

/**
 * 通过容器来管理事务
 * 
 * @author xyd-yuyilun
 *
 */
public class ManagedTransaction implements Transaction {

	private static final Log log = LogFactory.getLog(ManagedTransaction.class);

	private DataSource dataSource;
	private TransactionIsolationLevel level;
	private Connection connection;
	private boolean closeConnection;

	public ManagedTransaction(Connection connection) {
		this.connection = connection;
	}

	public ManagedTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean closeConnection) {
		this.dataSource = dataSource;
		this.level = level;
		this.closeConnection = closeConnection;
	}

	public Connection getConnection() throws SQLException {
		if (this.connection == null) {
			openConnection();
		}
		return this.connection;
	}

	public void commit() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void rollback() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void close() throws SQLException {
		if (this.closeConnection && this.connection != null) {
			if (log.isDebugEnabled()) {
				log.debug("Closing JDBC Connection [" + this.connection + "]");
			}
			// 关闭连接
			this.connection.close();
		}

	}

	protected void openConnection() throws SQLException {
		if (log.isDebugEnabled()) {
			log.debug("Opening JDBC Connection");
		}
		this.connection = this.dataSource.getConnection();
		if (this.level != null) {
			this.connection.setTransactionIsolation(this.level.getLevel());
		}
	}

	public Integer getTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
