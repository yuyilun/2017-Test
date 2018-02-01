package cn.test.tomcat.connector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.test.tomcat.listener.EventListener;
import cn.test.tomcat.utils.IoUtils;

public class SocketChannelConnector extends  AbstractChannelConnector  {
	private static final Logger logger = LoggerFactory.getLogger(SocketChannelConnector.class);
	
    private static final String LOCALHOST = "localhost";
    private static final int DEFAULT_BACKLOG = 50;
    
    private final int port;
    private final String host;
    private final int backLog;
    
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean started = false;
    private final EventListener<SelectionKey> eventListener;
	
    public SocketChannelConnector(int port, String host, int backLog, EventListener<SelectionKey> eventListener) {
    	 this.port = port;
         this.host = host;
         this.backLog = backLog;
         this.eventListener = eventListener;
    }
    
    public SocketChannelConnector(int port, EventListener<SelectionKey> eventHandler) {
    	 this(port, LOCALHOST, DEFAULT_BACKLOG, eventHandler);
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
	public void stop() {
		this.started = false;
		IoUtils.closeQuietly(this.serverSocketChannel);
	}

	@Override
	protected void communicate(SelectionKey selectionKey) throws ConnectorException {
		this.eventListener.onEvent(selectionKey);
	}

	@Override
	protected void init() throws ConnectorException {
		
		try {
			InetAddress inetAddress = InetAddress.getByName(host);
			SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
			
			this.serverSocketChannel= ServerSocketChannel.open();
			this.serverSocketChannel.configureBlocking(false);
			this.serverSocketChannel.bind(socketAddress);
			this.started = true;
			
			logger.info("NioServer started");
		} catch (IOException e) {
			throw new ConnectorException(e);
		}
	}

	@Override
	protected void acceptConnect() throws ConnectorException {
		new Thread(
			() -> {
				try {
					Selector selector = Selector.open();
					SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
					while(true) {
						try {
							selector.select();
							Set<SelectionKey> selectedKeys = selector.selectedKeys();
							Iterator<SelectionKey> iterator = selectedKeys.iterator();
							while(iterator.hasNext()) {
								key = (SelectionKey)iterator.next();
								iterator.remove();
								if(key.isAcceptable()) {
									ServerSocketChannel serverSocketChannel= (ServerSocketChannel) key.channel();
									SocketChannel socketChannel  = serverSocketChannel.accept();
									socketChannel.configureBlocking(false);
									ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
									socketChannel.register(selector, SelectionKey.OP_READ
											| SelectionKey.OP_WRITE ,byteBuffer);
									
									logger.info("NIO Connector accept Connect from {}",
	                                        socketChannel.getRemoteAddress());
								}else if(key.isReadable() || key.isWritable()) {
									communicate(key);
								}
								
							}
						}catch(IOException e) {
							logger.error("nio error", e);
	                        if (key != null) {
	                            key.cancel();
	                            IoUtils.closeQuietly(key.channel());
	                        }
						}
						
					}
				} catch (IOException e) {
					 throw new ConnectorException(e);
				}
			}
		).start();
	}
	
}
