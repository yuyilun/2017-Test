package cn.test.tomcat.handler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import cn.test.tomcat.connection.Connection;
import cn.test.tomcat.protocol.http.ContentTypeUtil;
import cn.test.tomcat.protocol.http.HttpRequestMessage;
import cn.test.tomcat.protocol.http.HttpResponseMessage;
import cn.test.tomcat.protocol.http.ResponseLine;
import cn.test.tomcat.protocol.http.body.HttpBody;
import cn.test.tomcat.protocol.http.header.HttpHeader;
import cn.test.tomcat.protocol.http.header.HttpMessageHeaders;
import cn.test.tomcat.protocol.http.parser.AbstractHttpRequestMessageParser;
import cn.test.tomcat.protocol.http.response.HttpResponseConstants;
import cn.test.tomcat.protocol.http.response.HttpResponseMessageWriter;
import cn.test.tomcat.protocol.http.response.ResponseLineConstants;

public class HttpStaticResourceEventHandler  extends AbstractHttpEventHandler{
	
	private final String docBase;
	private final AbstractHttpRequestMessageParser HttpRequestMessageParser;
	
	public HttpStaticResourceEventHandler(String docBase,AbstractHttpRequestMessageParser httpRequestMessageParser) {
		this.docBase = docBase;
		this.HttpRequestMessageParser = httpRequestMessageParser;
	}
	
	/**
	 * 将文件流写入返回信息
	 */
	@Override
	protected void doTransferToClient(HttpResponseMessage responseMessage, Connection connection) throws IOException {
		 HttpResponseMessageWriter httpResponseMessageWriter = new HttpResponseMessageWriter();
	     httpResponseMessageWriter.write(responseMessage, connection);
	}
	
	/**
	 * 生成返回信息
	 */
	@Override
	protected HttpResponseMessage doGenerateResponseMessage(HttpRequestMessage requestMessage) {
		
		String path = requestMessage.getRequestLine().getRequestURI().getPath();
		Path filePath = Paths.get(docBase, path);
		
		if(Files.isDirectory(filePath) || !Files.isReadable(filePath)) {
			return HttpResponseConstants.HTTP_404;
		}else {
			ResponseLine ok = ResponseLineConstants.RES_200;
			HttpMessageHeaders headers = HttpMessageHeaders.newBuilder().addHeader("status", "200").build();
			
			setContentType(filePath,headers); 
			HttpBody httpBody = null;
			try {
				httpBody = new HttpBody(new FileInputStream(filePath.toFile()));
			} catch (FileNotFoundException e) {
				return HttpResponseConstants.HTTP_404;
			}
		    HttpResponseMessage httpResponseMessage = new HttpResponseMessage(ok, headers,Optional.ofNullable(httpBody));
            return httpResponseMessage;
		}
	}
	
	/**
	 * 根据文件后缀设置文件Content-Type
	 * @param filePath
	 * @param headers
	 */
	 private void setContentType(Path filePath, HttpMessageHeaders headers) {
        String fileName = filePath.toFile().getName();
        if (fileName.contains(".")) {
            int idx = fileName.lastIndexOf(".");
            fileName = fileName.substring(idx);
        }
        String contentType = ContentTypeUtil.getContentType(fileName);
        headers.addHeader(new HttpHeader("Content-Type", contentType));
    }

	@Override
	protected HttpRequestMessage doParserRequestMessage(Connection connection){
		try {
			HttpRequestMessage httpRequestMessage = HttpRequestMessageParser.parse(connection.getInputStream());
			return httpRequestMessage;
		} catch (IOException e) {
			throw new HandlerException(e);
		}
	}
	
	
	
}
