package cn.test.tomcat.protocol.http;

import java.util.Optional;

import cn.test.tomcat.protocol.http.body.HttpBody;
import cn.test.tomcat.protocol.http.header.IMessageHeaders;

public interface HttpMessage {
	
	/**
	 * ��ȡ��ʼ��
	 * @return
	 */
	StartLine getStartLine();
	
	/**
	 * ��ȡHTTPͷ����
	 * @return
	 */
	IMessageHeaders getMessageHeaders();
	
	/**
	 * ��ȡHTTP Body
	 * @return
	 */
	Optional<HttpBody> getHttpBody();

}
