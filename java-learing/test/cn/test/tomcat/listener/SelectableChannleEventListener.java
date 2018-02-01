package cn.test.tomcat.listener;

import java.nio.channels.SelectionKey;

import cn.test.tomcat.handler.EventHandler;

public class SelectableChannleEventListener extends AbstractEventListener<SelectionKey> {
	
	private final EventHandler<SelectionKey> eventHandler;
	
	public SelectableChannleEventListener(EventHandler<SelectionKey> eventHandler) {
		this.eventHandler = eventHandler;
	}

	@Override
	protected EventHandler<SelectionKey> getEventHandler(SelectionKey event) {
		return this.eventHandler;
	}
}
