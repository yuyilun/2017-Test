package cn.test.tomcat.listener;

public interface  EventListener<T> {
	void onEvent(T event) throws EventException;
}
