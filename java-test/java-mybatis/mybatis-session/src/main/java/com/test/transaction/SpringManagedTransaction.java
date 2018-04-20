package com.test.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.transaction.Transaction;

/**
 * SpringManagedTransaction实现类：它其实也是通过使用JDBC来进行事务管理的，
 * 当spring的事务管理有效时，不需要操作commit/rollback/close，spring事务管理会自动帮我们完成
 * 
 * @author yuyilun
 *
 */
public class SpringManagedTransaction implements Transaction {

	private static final Log LOGGER = LogFactory.getLog(SpringManagedTransaction.class);

	private final DataSource dataSource;

	private Connection connection;

	private boolean isConnectionTransactional;

	private boolean autoCommit;

	/**
	 * 
	 * @param dataSource
	 */
	public SpringManagedTransaction(DataSource dataSource) {
		notnull(dataSource, "No DataSoure specified");
		this.dataSource = dataSource;
	}

	private void notnull(DataSource dataSource2, String string) {

	}

	public Connection getConnection() throws SQLException {
		if (this.connection == null) {
			openConnection();
		}
		return this.connection;
	}

	private void openConnection() {

	}

	public void commit() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void rollback() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void close() throws SQLException {
		// TODO Auto-generated method stub

	}

	public Integer getTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
