package cn.test.tomcat.connector;

import java.nio.channels.SelectionKey;

import cn.test.tomcat.connection.Connection;

public abstract class AbstractChannelConnector extends AbstractConnector{
	
	protected abstract void communicate(SelectionKey selectionKey) throws ConnectorException;
	 
	protected void communicate(Connection connection) throws ConnectorException{
		throw new ConnectorException("not support BIO");
	}
	
}
