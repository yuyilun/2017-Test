package cn.test.tomcat.listener;

import cn.test.tomcat.handler.EventHandler;

public abstract class AbstractEventListener<T> implements EventListener<T>{
	
	 /**
     * 事件处理流程模板方法
     * @param event 事件对象
     * @throws EventException
     */
	@Override
	public void onEvent(T event) throws EventException{
		EventHandler<T> eventHandler = getEventHandler(event);
		eventHandler.handle(event);
	}
	
	protected abstract EventHandler<T> getEventHandler(T event);
	
	
	
}
