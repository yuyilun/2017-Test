package cn.test.tomcat.protocol.http.parser;

import cn.test.tomcat.protocol.http.header.HttpMessageHeaders;

public interface HttpHeaderParser {
	
	/**
	 * ����������HttpMessageHeader����
	 * @return
	 */
	 HttpMessageHeaders parse();
}
