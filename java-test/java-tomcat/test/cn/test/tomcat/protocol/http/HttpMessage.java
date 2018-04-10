package cn.test.tomcat.protocol.http;

import java.util.Optional;

import cn.test.tomcat.protocol.http.body.HttpBody;
import cn.test.tomcat.protocol.http.header.IMessageHeaders;

public interface HttpMessage {
	
	/**
	 * 获取起始行
	 * @return
	 */
	StartLine getStartLine();
	
	/**
	 * 获取HTTP头集合
	 * @return
	 */
	IMessageHeaders getMessageHeaders();
	
	/**
	 * 获取HTTP Body
	 * @return
	 */
	Optional<HttpBody> getHttpBody();

}
