package cn.test.tomcat.protocol.http.parser;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import cn.test.tomcat.protocol.http.RequestLine;

public class DefaultHttpRequestLineParser extends AbstractParser implements HttpRequestLineParser{
	
	private static final String SPLITER = "\\s+";
	
	@Override
	public RequestLine parse() {
		String exceptionMsg = "startline format illegal:";
		try {
			byte[] bytes = HttpParserContext.getHttpMessageBytes();
			String httpString = new String(bytes,"utf-8");
			String startLine = httpString.split(CRLF,2)[0];
			
			String str = startLine.replaceAll(CRLF, "").trim();
			String[] parts = str.split(SPLITER);
			
			if(parts.length == 3) {
				String method  = parts[0];
				URI uri = URI.create(parts[1]);
				HttpParserContext.setRequestQueryString(uri.getQuery());
				String httpVersion = parts[2];
				return new RequestLine(method, uri, httpVersion);
			}
			exceptionMsg += startLine;
			
		} catch (UnsupportedEncodingException e) {
			 throw new ParserException("Unsupported Encoding", e);
		}
		throw new ParserException(exceptionMsg);
	}
	
}
