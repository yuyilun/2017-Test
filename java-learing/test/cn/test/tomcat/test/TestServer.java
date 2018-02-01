package cn.test.tomcat.test;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.test.tomcat.config.ServerConfig;
import cn.test.tomcat.connector.Connector;
import cn.test.tomcat.server.Server;
import cn.test.tomcat.server.ServerFactory;
import cn.test.tomcat.server.ServerStatus;

public class TestServer extends TestServerBase{
	
	private static Server  server;
	
	@BeforeClass
	public static void init() {
		ServerConfig serverConfig = new ServerConfig(ServerConfig.builder());
		server = ServerFactory.getServer(serverConfig);
	}
	
	@Test
	public void testServerStart() {
		startServer(server);
		waitServerStart(server);
		assertTrue("服务器启动后，状态是STARTED",server.getStatus().equals(ServerStatus.STARTED));
		
	}
	
	@Test
	public void testServerStop() {
		server.stop();
		assertTrue("服务器关闭后，状态是STOPED",server.getStatus().equals(ServerStatus.STOPED));
	}
	
	@Test
	public void testServerPort() {
		Set<Connector> connectors = server.getConnectors();
		connectors.forEach(connector -> {
			assertTrue("默认端口号", ServerConfig.DEFAULT_PORT == connector.getPort());
		});
		
	}
	
}
