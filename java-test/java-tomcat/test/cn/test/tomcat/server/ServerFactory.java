package cn.test.tomcat.server;

import cn.test.tomcat.config.ServerConfig;

public class ServerFactory {
	
	/**
	 * ·µ»ØServerÊµÀý
	 * @return
	 */
	public static Server getServer(ServerConfig serverConfig) {
	
		return new SimpleServer(serverConfig.getConnectors());
	}

}
