package cn.test.tomcat.server;

import java.io.IOException;
import java.util.Set;

import cn.test.tomcat.connector.Connector;

public interface Server {
	/**
	 * 启动服务器
	 * @throws IOException 
	 * @throws Exception 
	 */
	void start() throws IOException;
	
	/**
	 * 关闭服务器
	 */
	void stop();
	
	/**
	 * 获取服务器状态
	 * @return
	 */
	ServerStatus getStatus();
	
	/**
	 * 获取服务器管理的Connector列表
	 * @return
	 */
	Set<Connector> getConnectors();

}
