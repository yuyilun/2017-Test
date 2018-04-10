package cn.test.tomcat.config;

public class SocketConnectorConfig {
	private final int port;
	private final String host;
	private final int backLog;
	
	public SocketConnectorConfig(int port,String host, int backLog) {
		this.port = port;
		this.host = host;
		this.backLog = backLog;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getBackLog() {
        return backLog;
    }
}
