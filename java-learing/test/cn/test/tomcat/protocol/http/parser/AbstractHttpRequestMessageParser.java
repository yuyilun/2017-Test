package cn.test.tomcat.protocol.http.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.test.tomcat.protocol.http.HttpQueryParameters;
import cn.test.tomcat.protocol.http.HttpRequestMessage;
import cn.test.tomcat.protocol.http.RequestLine;
import cn.test.tomcat.protocol.http.body.HttpBody;
import cn.test.tomcat.protocol.http.header.IMessageHeaders;

public abstract class AbstractHttpRequestMessageParser extends AbstractParser implements HttpRequestMessageParser{
	private static final Logger logger = LoggerFactory.getLogger(AbstractHttpRequestMessageParser.class);
	
	/**
	 * 定义解析流程
	 */
	@Override
	public HttpRequestMessage parse(InputStream inputStream) throws IOException {
		//1 设置上下文：设置是否有body、body之前byte数组，以及body之前byte数组长度到上下文中
		getAndSetBytesBeforeBodyToContext(inputStream);
		//2 解析构造requestLine
		RequestLine requestLine = parseRequestLine();
		//3 解析构造QueryParameters
		 HttpQueryParameters httpQueryParameters = parseHttpQueryParameters();
		//4 解析构造HTTP请求头
		 IMessageHeaders messageHeaders = parseRequestHeaders();
		//5 解析构造HTTP Body,如果有个的话
		 Optional<HttpBody> httpBody = parseRequestBody();
		
		HttpRequestMessage httpRequestMessage = new HttpRequestMessage(requestLine, messageHeaders, httpBody, httpQueryParameters);
		return httpRequestMessage;
	}
	
	/**
	 *  解析并构建HTTP请求Headers集合
	 * @return
	 */
	protected abstract IMessageHeaders parseRequestHeaders();
	
	/**
	 *  解析并构建HTTP 请求Body
	 * @return
	 */
	protected abstract Optional<HttpBody> parseRequestBody();

	/**
	 * 解析并构建QueryParameter集合
	 * @return
	 */
	protected abstract HttpQueryParameters parseHttpQueryParameters();


	/**
	 * 解析并构建RequestLine
	 * @return
	 */
	protected abstract RequestLine parseRequestLine();



	private void getAndSetBytesBeforeBodyToContext(InputStream inputStream) throws IOException {
		byte[] bytes = copyRequestBytesBeforeBody(inputStream);
		HttpParserContext.setHttpMessageBytes(bytes);
		HttpParserContext.setBytesLengthBeforeBody(bytes.length);
		HttpParserContext.setInputStream(inputStream);
	}



	private byte[] copyRequestBytesBeforeBody(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputStream.available());
        int i = -1;
        byte[] temp = new byte[3];
        while ((i = inputStream.read()) != -1) {
            byteArrayOutputStream.write(i);
            if ((char) i == '\r') {
                int len = inputStream.read(temp, 0, temp.length);
                byteArrayOutputStream.write(temp, 0, len);
                if ("\n\r\n".equals(new String(temp))) {
                    break;
                }
            }
        }
        return byteArrayOutputStream.toByteArray();
	}

	
	

}
