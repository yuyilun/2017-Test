package cn.test.tomcat.server;

import cn.test.tomcat.config.ServerConfig;

public class ServerFactory {
	
	/**
	 * ����Serverʵ��
	 * @return
	 */
	public static Server getServer(ServerConfig serverConfig) {
	
		return new SimpleServer(serverConfig.getConnectors());
	}

}
