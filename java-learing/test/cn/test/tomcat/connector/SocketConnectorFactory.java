package cn.test.tomcat.connector;

import cn.test.tomcat.config.SocketConnectorConfig;
import cn.test.tomcat.connection.Connection;
import cn.test.tomcat.listener.EventListener;

public class SocketConnectorFactory{

	public static SocketConnector build(SocketConnectorConfig socketConnectorConfig,EventListener<Connection> eventListener) {
		return new SocketConnector(socketConnectorConfig.getPort(),socketConnectorConfig.getHost(),socketConnectorConfig.getBackLog(),eventListener);
	}
	

	public static SocketConnector build(int port, EventListener<Connection> eventListener) {
		  return new SocketConnector(port, eventListener);
	}
	
}
