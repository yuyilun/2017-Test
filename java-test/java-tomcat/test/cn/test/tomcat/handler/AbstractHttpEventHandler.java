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
		 //�������й����HTTP�������,Body���������ӳٶ�ȡ
		HttpRequestMessage requestMessage = doParserRequestMessage(connection);
		//����HTTP��Ӧ����
		HttpResponseMessage responseMessage = doGenerateResponseMessage(requestMessage);
		
		try {
			doTransferToClient(responseMessage, connection);
		} catch (IOException e) {
			throw new HandlerException(e);
		}finally {
			 //�����Ӧ�󣬹ر�Socket
			if(connection instanceof SocketConnection) {
				IOUtils.closeQuietly(((SocketConnection) connection).getSocket());
			}
		}
	}
	
	/**
	 * д��HttpResponseMessage���ͻ���
	 * @param responseMessage
	 * @param connection
	 * @throws IOException
	 */
    protected abstract void doTransferToClient(HttpResponseMessage responseMessage,
            Connection connection) throws IOException;
    
    /**
     *  ����HttpRequestMessage����HttpResponseMessage
     * @param requestMessage
     * @return
     */
	protected abstract HttpResponseMessage doGenerateResponseMessage(HttpRequestMessage requestMessage);
	
	/**
	 * ͨ�����빹��HttpRequestMessage
	 * @param connection
	 * @return
	 */
	protected abstract HttpRequestMessage doParserRequestMessage(Connection connection);
	
	
}
