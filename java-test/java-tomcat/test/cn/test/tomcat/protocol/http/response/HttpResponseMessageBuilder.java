package cn.test.tomcat.protocol.http.response;

import java.util.Optional;

import cn.test.tomcat.protocol.http.HttpResponseMessage;
import cn.test.tomcat.protocol.http.ResponseLine;
import cn.test.tomcat.protocol.http.body.HttpBody;
import cn.test.tomcat.protocol.http.header.IMessageHeaders;

public class HttpResponseMessageBuilder {
	private ResponseLine responseLine;
	private IMessageHeaders messageHeaders;
	private Optional<HttpBody> httpBody = Optional.empty();
	
	private HttpResponseMessageBuilder() {}
	
	public static HttpResponseMessageBuilder builder() {
		return new HttpResponseMessageBuilder();
	}
	
	public HttpResponseMessageBuilder withResponseLine(ResponseLine responseline) {
		this.responseLine = responseLine;
		return this;
	}
	
	public HttpResponseMessageBuilder withMessageHeaders(IMessageHeaders messageHeaders) {
		this.messageHeaders = messageHeaders;
		return this;
	}

	public HttpResponseMessageBuilder withHttpBody(Optional<HttpBody> httpBody) {
		this.httpBody = httpBody;
		return this;
	}
	
	public HttpResponseMessage build() {
		return new HttpResponseMessage(responseLine, messageHeaders, httpBody);
	}
	
}
