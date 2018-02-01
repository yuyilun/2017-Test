package cn.test.tomcat.protocol.http.response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import org.apache.commons.io.IOUtils;
import cn.test.tomcat.connection.Connection;
import cn.test.tomcat.protocol.http.HttpResponseMessage;
import cn.test.tomcat.protocol.http.ResponseLine;
import cn.test.tomcat.protocol.http.body.HttpBody;
import cn.test.tomcat.protocol.http.header.IMessageHeaders;

public class HttpResponseMessageWriter {
	
	/**
	 * 返回信息
	 * @param httpResponseMessage
	 * @param connection
	 * @throws IOException
	 */
	public void write(HttpResponseMessage httpResponseMessage, Connection connection)
            throws IOException {
		OutputStream outputStream = connection.getOutputStream();
		ResponseLine responseLine = httpResponseMessage.getResponseLine();
		
		//responseLine:
		String asString = responseLine.asString();
		write(outputStream, asString);
		//headers
		IMessageHeaders headers = httpResponseMessage.getMessageHeaders();
		String headersString = headers.asString();
		write(outputStream, headersString);
		
		//可以存null的容器
		Optional<HttpBody> opHttpBody = httpResponseMessage.getHttpBody();
		//如果值存在则方法会返回true，否则返回 false
		if(opHttpBody.isPresent()) {
			IOUtils.copy(opHttpBody.get().getInputStream(), outputStream);
		}
		outputStream.flush();
	}

	private void write(OutputStream outputStream, String message) throws IOException{
		outputStream.write(message.getBytes("utf-8"));
		outputStream.write("\r\n".getBytes());
	}
	
}
