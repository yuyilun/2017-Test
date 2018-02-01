package cn.test.tomcat.protocol.http;

import java.util.Optional;
import cn.test.tomcat.protocol.http.body.HttpBody;
import cn.test.tomcat.protocol.http.header.IMessageHeaders;

public class HttpRequestMessage implements HttpMessage{
	
	
	private final RequestLine requestLine;
	private final IMessageHeaders messageHeaders;
	private final Optional<HttpBody> httpBody;
	private final HttpQueryParameters httpQueryParameters;
	
	public HttpRequestMessage(RequestLine requestLine, IMessageHeaders messageHeaders, Optional<HttpBody> httpBody,
            HttpQueryParameters httpQueryParameters) {
		this.requestLine = requestLine;
        this.messageHeaders = messageHeaders;
        this.httpBody = httpBody;
        this.httpQueryParameters = httpQueryParameters;
	}
	
	
	@Override
	public StartLine getStartLine() {
		return StartLine.class.cast(this.requestLine);
	}
	@Override
	public IMessageHeaders getMessageHeaders() {
		return this.messageHeaders;
	}
	@Override
	public Optional<HttpBody> getHttpBody() {
		return this.httpBody;
	}
	
	public RequestLine getRequestLine() {
		return requestLine;
	}
	
	public HttpQueryParameters getHttpQueryParameters() {
		return httpQueryParameters;
	}
}
