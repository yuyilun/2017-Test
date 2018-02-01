package cn.test.tomcat.listener;

import java.nio.channels.SelectionKey;

import cn.test.tomcat.handler.EventHandler;

public class NIOEventListener extends AbstractEventListener<SelectionKey>{
	private final EventHandler<SelectionKey> eventHandler;
	
	public NIOEventListener(EventHandler<SelectionKey> eventHandler) {
		this.eventHandler = eventHandler;
	}
	
	@Override
	protected EventHandler<SelectionKey> getEventHandler(SelectionKey event) {
		return this.eventHandler;
	}
	
}
