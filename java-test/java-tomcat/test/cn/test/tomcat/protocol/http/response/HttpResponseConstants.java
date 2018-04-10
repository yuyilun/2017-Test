package cn.test.tomcat.protocol.http.response;

import cn.test.tomcat.protocol.http.HttpResponseMessage;
import cn.test.tomcat.protocol.http.header.HttpMessageHeaders;

public interface HttpResponseConstants {
	HttpResponseMessage HTTP_404 = HttpResponseMessageBuilder.builder()
			.withResponseLine(ResponseLineConstants.RES_404)
			.withMessageHeaders(HttpMessageHeaders.newBuilder().addHeader("Content-Type", "text/html").build())
			.build();
}
