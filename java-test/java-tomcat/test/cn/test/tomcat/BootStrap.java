package cn.test.tomcat;

import java.io.IOException;
import java.nio.channels.SelectionKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.test.tomcat.config.ServerConfig;
import cn.test.tomcat.connection.Connection;
import cn.test.tomcat.connector.SocketChannelConnector;
import cn.test.tomcat.connector.SocketConnector;
import cn.test.tomcat.connector.SocketConnectorFactory;
import cn.test.tomcat.handler.EchoEventHandler;
import cn.test.tomcat.handler.FileEventHandler;
import cn.test.tomcat.handler.HttpStaticResourceEventHandler;
import cn.test.tomcat.handler.NIOEchoEventHandler;
import cn.test.tomcat.listener.ConnectionEventListener;
import cn.test.tomcat.listener.EventListener;
import cn.test.tomcat.listener.SelectableChannleEventListener;
import cn.test.tomcat.protocol.http.parser.DefaultHttpBodyParser;
import cn.test.tomcat.protocol.http.parser.DefaultHttpHeaderParser;
import cn.test.tomcat.protocol.http.parser.DefaultHttpQueryParameterParser;
import cn.test.tomcat.protocol.http.parser.DefaultHttpRequestLineParser;
import cn.test.tomcat.protocol.http.parser.DefaultHttpRequestMessageParser;
import cn.test.tomcat.server.Server;
import cn.test.tomcat.server.ServerFactory;

public class BootStrap {
	public static void main(String[] args) throws IOException {
		
		Logger logger = LoggerFactory.getLogger(BootStrap.class);
	/*	
		EventListener<Connection> socketEventListener = new ConnectionEventListener(new EchoEventHandler());
		SocketConnector connector = SocketConnectorFactory.build(18080, socketEventListener);
		
		EventListener<Connection> socketEventListener2 = new ConnectionEventListener(new FileEventHandler(System.getProperty("user.dir")));
		SocketConnector connector2 = SocketConnectorFactory.build(18081, socketEventListener2);
		
		EventListener<SelectionKey> nioEventListener = new SelectableChannleEventListener(new NIOEchoEventHandler());
		SocketChannelConnector connector3 = new SocketChannelConnector(18082, nioEventListener);*/
		
	   logger.info(System.getProperty("user.dir"));
	   
	   EventListener<Connection> socketEventListener3 =new ConnectionEventListener(
			   					new HttpStaticResourceEventHandler(System.getProperty("user.dir"),
	                        		new DefaultHttpRequestMessageParser(
	                        			new DefaultHttpRequestLineParser(),
		                                new DefaultHttpQueryParameterParser(),
		                                new DefaultHttpHeaderParser(),
		                                new DefaultHttpBodyParser())));
	   
        SocketConnector connector4 = SocketConnectorFactory.build(18083, socketEventListener3);
		
		ServerConfig  serverConfig  = ServerConfig.builder().
											/*addConnector(connector).
											addConnector(connector2).
											addConnector(connector3).*/
											addConnector(connector4).build();
		
		Server server = ServerFactory.getServer(serverConfig);
		server.start();
	}

}
