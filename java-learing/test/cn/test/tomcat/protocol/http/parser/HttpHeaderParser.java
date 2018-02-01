package cn.test.tomcat.protocol.http.parser;

import cn.test.tomcat.protocol.http.header.HttpMessageHeaders;

public interface HttpHeaderParser {
	
	/**
	 * 解析并返回HttpMessageHeader集合
	 * @return
	 */
	 HttpMessageHeaders parse();
}
