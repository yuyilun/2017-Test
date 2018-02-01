package cn.test.tomcat.protocol.http;

import java.util.Optional;

import cn.test.tomcat.protocol.http.body.HttpBody;
import cn.test.tomcat.protocol.http.header.IMessageHeaders;

public class HttpResponseMessage implements HttpMessage{
	
	private final ResponseLine responseLine;
	private final IMessageHeaders messageHeaders;
	private final Optional<HttpBody> httpBody;
	
	
	public HttpResponseMessage(ResponseLine responseLine,IMessageHeaders messageHeaders, Optional<HttpBody> httpBody) {
		this.responseLine = responseLine;
		this.messageHeaders = messageHeaders;
		this.httpBody = httpBody;
	}
	@Override
    public StartLine getStartLine() {
        return StartLine.class.cast(this.responseLine);
    }

    @Override
    public IMessageHeaders getMessageHeaders() {
        return this.messageHeaders;
    }

    @Override
    public Optional<HttpBody> getHttpBody() {
        return this.httpBody;
    }

    public ResponseLine getResponseLine() {
        return responseLine;
    }
	

}
