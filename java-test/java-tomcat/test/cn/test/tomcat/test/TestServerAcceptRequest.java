package cn.test.tomcat.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.test.tomcat.config.ServerConfig;
import cn.test.tomcat.server.Server;
import cn.test.tomcat.server.ServerFactory;
import cn.test.tomcat.server.ServerStatus;
import cn.test.tomcat.utils.IoUtils;

public class TestServerAcceptRequest extends TestServerBase{
	private static Logger logger = LoggerFactory.getLogger(TestServerAcceptRequest.class);
	
	private static Server server;
	private static final int TIMEOUT = 500;

	@BeforeClass
    public static void init() {
        ServerConfig serverConfig = new ServerConfig(ServerConfig.builder());
        server = ServerFactory.getServer(serverConfig);
    }
	
	@Test
    public void testServerAcceptRequest() {
		if(server.getStatus().equals(ServerStatus.STOPED)) {
			startServer(server);
	        waitServerStart(server);
			Socket socket = new Socket();
			SocketAddress socketAddress = new InetSocketAddress("localhost", ServerConfig.DEFAULT_PORT);
			try {
				socket.connect(socketAddress, TIMEOUT);
				assertTrue("服务器启动后，能接受请求",socket.isConnected());
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}finally {
				IoUtils.closeQuietly(socket);
			}
		}
	}
	
	@AfterClass
	public static void destory() {
		server.stop();
	}
}
