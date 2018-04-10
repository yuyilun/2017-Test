package cn.test.tomcat.listener;

import java.net.Socket;

import cn.test.tomcat.handler.EventHandler;

public class SocketEventListener extends AbstractEventListener<Socket> {
	
	private final EventHandler<Socket> eventHandler;
	
	public SocketEventListener(EventHandler<Socket> eventHandler) {
		this.eventHandler = eventHandler;
	}
	
	@Override
	protected EventHandler<Socket> getEventHandler(Socket event) {
		return eventHandler;
	}
	
	
}
