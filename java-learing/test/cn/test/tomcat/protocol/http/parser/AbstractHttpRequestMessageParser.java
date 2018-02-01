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
	 * �����������
	 */
	@Override
	public HttpRequestMessage parse(InputStream inputStream) throws IOException {
		//1 ���������ģ������Ƿ���body��body֮ǰbyte���飬�Լ�body֮ǰbyte���鳤�ȵ���������
		getAndSetBytesBeforeBodyToContext(inputStream);
		//2 ��������requestLine
		RequestLine requestLine = parseRequestLine();
		//3 ��������QueryParameters
		 HttpQueryParameters httpQueryParameters = parseHttpQueryParameters();
		//4 ��������HTTP����ͷ
		 IMessageHeaders messageHeaders = parseRequestHeaders();
		//5 ��������HTTP Body,����и��Ļ�
		 Optional<HttpBody> httpBody = parseRequestBody();
		
		HttpRequestMessage httpRequestMessage = new HttpRequestMessage(requestLine, messageHeaders, httpBody, httpQueryParameters);
		return httpRequestMessage;
	}
	
	/**
	 *  ����������HTTP����Headers����
	 * @return
	 */
	protected abstract IMessageHeaders parseRequestHeaders();
	
	/**
	 *  ����������HTTP ����Body
	 * @return
	 */
	protected abstract Optional<HttpBody> parseRequestBody();

	/**
	 * ����������QueryParameter����
	 * @return
	 */
	protected abstract HttpQueryParameters parseHttpQueryParameters();


	/**
	 * ����������RequestLine
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
