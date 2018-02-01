package cn.test.tomcat.listener;

import cn.test.tomcat.connection.Connection;
import cn.test.tomcat.handler.EventHandler;

public class ConnectionEventListener extends AbstractEventListener<Connection>{
	private final EventHandler<Connection> eventHandler;
	
	public ConnectionEventListener(EventHandler<Connection> eventHandler) {
		this.eventHandler = eventHandler;
	}
	
	@Override
	protected EventHandler<Connection> getEventHandler(Connection event) {
		return this.eventHandler;
	}

}
