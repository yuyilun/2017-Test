package cn.test.tomcat.connector;

import cn.test.tomcat.connection.Connection;

public abstract class AbstractConnector  implements Connector {
	
	@Override
	public void start() {
		init();
		acceptConnect();
	}
	
	protected abstract void init() throws ConnectorException;

    protected abstract void acceptConnect() throws ConnectorException;
    
    protected abstract void communicate(Connection connection) throws ConnectorException;
}
