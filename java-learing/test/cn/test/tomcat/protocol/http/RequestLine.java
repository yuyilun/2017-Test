package cn.test.tomcat.protocol.http;

import java.net.URI;

/**
 * HTTP Request «Î«Û––
 * ≤Œøºhttps://tools.ietf.org/html/rfc2616#page-31
 * Request-Line   = Method SP Request-URI SP HTTP-Version CRLF
 */
public class RequestLine implements StartLine{
	 
	private final String method;
	private final URI requestURI;
	private final String httpVersion;
	
	public RequestLine(String method,URI requestURI , String httpVersion) {
		this.method = method;
		this.requestURI = requestURI;
		this.httpVersion = httpVersion;
	}
	
	public String getMethod() {
		return method;
	}
	
	public URI getRequestURI() {
		return requestURI;
	}
	
	@Override
	public String getHttpVersion() {
		return httpVersion;
	}

}
