package cn.test.tomcat.connector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.test.tomcat.connection.Connection;
import cn.test.tomcat.connection.SocketConnection;
import cn.test.tomcat.listener.EventListener;
import cn.test.tomcat.utils.IoUtils;

public class SocketConnector extends AbstractConnector{
	
	private static Logger logger = LoggerFactory.getLogger(SocketConnector.class);
	private static final String LOCALHOST = "localhost";
	private static final int DEFAULT_BACKLOG = 50;
	 
	private volatile boolean started = false;
	private ServerSocket serverSocket;
	
	private final int port;
	private final String host;
	private final int backLog;
	    
	private final EventListener<Connection> eventListener;
	
	public SocketConnector(int port, EventListener<Connection> eventListener) {
        this(port, LOCALHOST, DEFAULT_BACKLOG, eventListener);
    }
	
	public SocketConnector(int port, String host, int backLog,EventListener<Connection> eventListener) {
		this.port = port;
		//this.host = StringUtils.isBlank(host) ? LOCALHOST : host;
		this.host = host;
		this.backLog = backLog;
	    this.eventListener = eventListener;
	}
	
	@Override
	public void stop() {
		this.started = false;
		IoUtils.closeQuietly(this.serverSocket);
	}

	@Override
	protected void init() {
		try {
			InetAddress inetAddress = InetAddress.getByName(this.host);
			this.serverSocket = new ServerSocket(this.port,backLog, inetAddress);
			this.started = true;
		} catch (IOException e) {
			throw new ConnectorException();
		}
		
	}

	@Override
	protected void acceptConnect() {
		
		new Thread(
		()->{
			while(true && started) {
				Socket socket = null;
				try {
					socket = serverSocket.accept();
					communicate(new SocketConnection(socket));
					logger.info("新增连接：" + socket.getInetAddress() + ":" + socket.getPort());
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}finally {
					 IoUtils.closeQuietly(socket);
				}
			}
		}
		).start();
	}

	@Override
	public int getPort() {
		return this.port;
	}

	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	protected void communicate(Connection connection) throws ConnectorException {
		this.eventListener.onEvent(connection);
	}
	
}
