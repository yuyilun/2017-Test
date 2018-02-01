package cn.test.tomcat.handler;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import cn.test.tomcat.connection.Connection;
import cn.test.tomcat.connection.SocketConnection;
import cn.test.tomcat.protocol.http.HttpRequestMessage;
import cn.test.tomcat.protocol.http.HttpResponseMessage;

public abstract class AbstractHttpEventHandler extends AbstractEventHandler<Connection>{
	
	@Override
	protected void doHandle(Connection connection) {
		 //从输入中构造出HTTP请求对象,Body的内容是延迟读取
		HttpRequestMessage requestMessage = doParserRequestMessage(connection);
		//构造HTTP响应对象
		HttpResponseMessage responseMessage = doGenerateResponseMessage(requestMessage);
		
		try {
			doTransferToClient(responseMessage, connection);
		} catch (IOException e) {
			throw new HandlerException(e);
		}finally {
			 //完成响应后，关闭Socket
			if(connection instanceof SocketConnection) {
				IOUtils.closeQuietly(((SocketConnection) connection).getSocket());
			}
		}
	}
	
	/**
	 * 写入HttpResponseMessage到客户端
	 * @param responseMessage
	 * @param connection
	 * @throws IOException
	 */
    protected abstract void doTransferToClient(HttpResponseMessage responseMessage,
            Connection connection) throws IOException;
    
    /**
     *  根据HttpRequestMessage生成HttpResponseMessage
     * @param requestMessage
     * @return
     */
	protected abstract HttpResponseMessage doGenerateResponseMessage(HttpRequestMessage requestMessage);
	
	/**
	 * 通过输入构造HttpRequestMessage
	 * @param connection
	 * @return
	 */
	protected abstract HttpRequestMessage doParserRequestMessage(Connection connection);
	
	
}
