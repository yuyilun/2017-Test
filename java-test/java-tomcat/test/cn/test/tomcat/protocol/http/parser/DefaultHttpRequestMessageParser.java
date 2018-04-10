package cn.test.tomcat.protocol.http.parser;

import java.util.Optional;

import cn.test.tomcat.protocol.http.HttpQueryParameters;
import cn.test.tomcat.protocol.http.RequestLine;
import cn.test.tomcat.protocol.http.body.HttpBody;
import cn.test.tomcat.protocol.http.header.HttpMessageHeaders;
import cn.test.tomcat.protocol.http.header.IMessageHeaders;

public class DefaultHttpRequestMessageParser extends AbstractHttpRequestMessageParser{
	
	private final HttpRequestLineParser httpRequestLineParser;
    private final HttpQueryParameterParser httpQueryParameterParser;
    private final HttpHeaderParser httpHeaderParser;
    private final HttpBodyParser httpBodyParser;
	
	public DefaultHttpRequestMessageParser(HttpRequestLineParser httpRequestLineParser,
            HttpQueryParameterParser httpQueryParameterParser,
            HttpHeaderParser httpHeaderParser,
            HttpBodyParser httpBodyParser) {
		this.httpRequestLineParser = httpRequestLineParser;
		this.httpQueryParameterParser = httpQueryParameterParser;
		this.httpHeaderParser = httpHeaderParser;
		this.httpBodyParser = httpBodyParser;
	}
	
	@Override
	protected IMessageHeaders parseRequestHeaders() {
		HttpMessageHeaders httpMessageHeaders = this.httpHeaderParser.parse();
		if(httpMessageHeaders.hasHeader(CONTENT_TYPE)) {
			HttpParserContext.setContentType(httpMessageHeaders.getFirstHeader(CONTENT_TYPE).getValue());
		}
		return httpMessageHeaders;
	}

	@Override
	protected Optional<HttpBody> parseRequestBody() {
		if(isHasBodyMethod()) {
			HttpBody httpBody = this.httpBodyParser.parse();
			return Optional.ofNullable(httpBody);
		}
		return Optional.empty();
	}

	private boolean isHasBodyMethod() {
		return ("POST".equals(HttpParserContext.getHttpMethod())
				|| "PUT".equals(HttpParserContext.getHttpMethod()))
				&& HttpParserContext.getHasBody();
	}

	@Override
	protected HttpQueryParameters parseHttpQueryParameters() {
		return this.httpQueryParameterParser.parse();
	}

	@Override
	protected RequestLine parseRequestLine() {
		RequestLine requestLine = this.httpRequestLineParser.parse();
		HttpParserContext.setHttpMethod(requestLine.getMethod());
		HttpParserContext.setRequestQueryString(requestLine.getRequestURI().getQuery());
		return requestLine;
	}
	
	

}
