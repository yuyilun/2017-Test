package cn.test.tomcat.handler;

public interface EventHandler<T> {
	
	void handle(T obj) throws HandlerException;
}
