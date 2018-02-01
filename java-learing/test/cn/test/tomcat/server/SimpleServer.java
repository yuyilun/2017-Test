package cn.test.tomcat.server;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.test.tomcat.connector.Connector;

public class SimpleServer implements Server {
	private static Logger logger = LoggerFactory.getLogger(SimpleServer.class);
	
	private volatile ServerStatus serverStatus = ServerStatus.STOPED;
	
	private final Set<Connector> connectors;
	
	public SimpleServer(Set<Connector> connectors) {
		this.connectors = connectors;
	}

	@Override
	public void start() throws IOException{
		connectors.forEach(LifeCycle::start);
		this.serverStatus = ServerStatus.STARTED;
	}
	
	@Override
	public void stop() {
		connectors.forEach(LifeCycle::stop);
		this.serverStatus = ServerStatus.STOPED;
		logger.info("Server stop");
	}

	@Override
	public ServerStatus getStatus() {
		return serverStatus;
	}
	

	@Override
	public Set<Connector> getConnectors() {
		return connectors;
	}
	
}
