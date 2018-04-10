package cn.test.tomcat.protocol.http.parser;

import cn.test.tomcat.protocol.http.RequestLine;

public interface HttpRequestLineParser {
	
	/**
     * ����start line��������RequestLine����
     * <p>
     * Method SP Request-URI SP HTTP-Version CRLF
     *
     * @return
     */
	RequestLine parse();
	
}
