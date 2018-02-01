package cn.test.tomcat.connector;

import cn.test.tomcat.server.LifeCycle;

public interface Connector extends LifeCycle{
	/**
	 * 获取监听的端口
	 * @return
	 */
	int getPort();
	
	/**
	 * 获取监听的IP、主机名
	 * @return
	 */
	String getHost();
}
