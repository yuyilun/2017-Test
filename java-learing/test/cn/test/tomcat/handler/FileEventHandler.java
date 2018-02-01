package cn.test.tomcat.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import cn.test.tomcat.connection.Connection;

public class FileEventHandler extends AbstractEventHandler<Connection> {
	
	private final String docBase;
	private final FileTransfer fileTransfer = new FileTransfer();
	
	public FileEventHandler(String docBase) {
		this.docBase = docBase;
	}
	
	
	@Override
	protected void doHandle(Connection connection) {
		try {
			fileTransfer.getFile(this.docBase, connection.getInputStream(),
			connection.getOutputStream());
		} catch (IOException e) {
			 throw new HandlerException(e);
		}
	}
	
	
	private void getFile(Socket socket,InputStream inputStream,OutputStream outputStream) {
		 fileTransfer.getFile(docBase, inputStream, outputStream);
	}
}
