package cn.test.tomcat.test;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.test.tomcat.protocol.http.RequestLine;
import cn.test.tomcat.protocol.http.parser.DefaultHttpRequestLineParser;
import cn.test.tomcat.protocol.http.parser.HttpParserContext;

public class TestDefaultHttpRequestLineParser {
	
	private static final Logger logger = LoggerFactory.getLogger(TestDefaultHttpRequestLineParser.class);
	
	@Test
    public void test() {
		
		HttpParserContext parserContext = new HttpParserContext(); 
		parserContext.setHttpMessageBytes("GET /hello.txt HTTP/1.1\r\n".getBytes());
		
		DefaultHttpRequestLineParser defaultRequestLineParser = new DefaultHttpRequestLineParser();
		RequestLine result = defaultRequestLineParser.parse();
        String method = result.getMethod();
        assertEquals("GET", method);
        final URI requestURI = result.getRequestURI();
        assertEquals(URI.create("/hello.txt"), requestURI);
        logger.info(requestURI.getQuery());
        logger.info(requestURI.getFragment());
        assertEquals("HTTP/1.1", result.getHttpVersion());
        assertEquals(requestURI.getQuery(), parserContext.getRequestQueryString());
    }

    @Test
    public void testQuery() {
    	 HttpParserContext parserContext = new HttpParserContext();
         parserContext.setHttpMessageBytes("GET /test?a=123&a1=1&b=456 HTTP/1.1\r\n".getBytes());

         DefaultHttpRequestLineParser defaultRequestLineParser
                 = new DefaultHttpRequestLineParser();
         RequestLine result = defaultRequestLineParser.parse();
         String method = result.getMethod();
         assertEquals("GET", method);
         final URI requestURI = result.getRequestURI();
         assertEquals(URI.create("/test?a=123&a1=1&b=456"), requestURI);
         logger.info(requestURI.getQuery());
         assertEquals("HTTP/1.1", result.getHttpVersion());
    }
	
	
			
			
			
	
	
}
