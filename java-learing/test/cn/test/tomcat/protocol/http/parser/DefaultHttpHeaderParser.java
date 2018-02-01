package cn.test.tomcat.protocol.http.parser;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.test.tomcat.protocol.http.header.HttpHeader;
import cn.test.tomcat.protocol.http.header.HttpMessageHeaders;

public class DefaultHttpHeaderParser extends AbstractParser implements HttpHeaderParser{
	
	private static final String SPLITER = ":";
	private static final Logger logger = LoggerFactory.getLogger(DefaultHttpHeaderParser.class);

	
	
	@Override
	public HttpMessageHeaders parse() {
		
		try {
			String httpText = getHttpTextFromCOntext();
			HttpMessageHeaders httpMessageHeaders = doParseHttpMessageHeaders(httpText);
			setHasBody(httpMessageHeaders);
			return httpMessageHeaders;
		} catch (UnsupportedEncodingException e) {
			throw new ParserException("Unsupported Encoding",e);
		}
		
	}


	/**
	 * 设置报文是否包含Body到上下文中
	 * @param httpMessageHeaders
	 */
	private void setHasBody(HttpMessageHeaders httpMessageHeaders) {
		if(httpMessageHeaders.hasHeader("Content-Length")) {
			HttpParserContext.setHasBody(true);
			HttpParserContext.getBodyInfo().setContentLength(Integer.valueOf(httpMessageHeaders.getFirstHeader("Content-Length").getValue()));
		}else if(httpMessageHeaders.hasHeader("Transfer-Encoding") && "chunked".equals(httpMessageHeaders.getFirstHeader("Transfer-Encoding").getValue())) {
			HttpParserContext.setHasBody(true);
		}
	}



	/**
	 * 解析Body之前的文本构建HttpHeader，并保存到HttpMessageHeaders中
	 * @param httpText
	 * @return
	 */
	private HttpMessageHeaders doParseHttpMessageHeaders(String httpText) {
		HttpMessageHeaders httpMessageHeaders = new HttpMessageHeaders();
		String[] lines = httpText.split(CRLF);
		
		for(int i = 1 ; i < lines.length; i++) {
			String keyValue = lines[i];
			if ("".equals(keyValue)) {
				break;
			}
			String[] temp = keyValue.split(SPLITER);
			if(temp.length == 2) {
				httpMessageHeaders.addHeader(new HttpHeader(temp[0], temp[1].trim()));
			}
		}
		return httpMessageHeaders;
	}



	private String getHttpTextFromCOntext() throws UnsupportedEncodingException {
		byte[] bytes = HttpParserContext.getHttpMessageBytes();
		return new String(bytes, "utf-8");
	}
	
}
