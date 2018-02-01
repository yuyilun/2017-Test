package cn.test.tomcat.listener;

import cn.test.tomcat.handler.EventHandler;

public abstract class AbstractEventListener<T> implements EventListener<T>{
	
	 /**
     * �¼���������ģ�巽��
     * @param event �¼�����
     * @throws EventException
     */
	@Override
	public void onEvent(T event) throws EventException{
		EventHandler<T> eventHandler = getEventHandler(event);
		eventHandler.handle(event);
	}
	
	protected abstract EventHandler<T> getEventHandler(T event);
	
	
	
}
