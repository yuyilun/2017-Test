package cn.test.tomcat.protocol.http.parser;

import cn.test.tomcat.protocol.http.RequestLine;

public interface HttpRequestLineParser {
	
	/**
     * 解析start line，并返回RequestLine对象
     * <p>
     * Method SP Request-URI SP HTTP-Version CRLF
     *
     * @return
     */
	RequestLine parse();
	
}
