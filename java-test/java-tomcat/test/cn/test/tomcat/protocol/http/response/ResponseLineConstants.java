package cn.test.tomcat.protocol.http.response;

import cn.test.tomcat.protocol.http.HttpProtocol;
import cn.test.tomcat.protocol.http.ResponseLine;

public interface ResponseLineConstants {
	ResponseLine RES_404 = new ResponseLine(HttpProtocol.VERSION11.toString(),
			HttpStatus.STATUS_404.getStatusCode(),HttpStatus.STATUS_404.getReason());
	
	ResponseLine RES_200 = new ResponseLine(HttpProtocol.VERSION11.toString(),
			HttpStatus.STATUS_200.getStatusCode(),HttpStatus.STATUS_200.getReason());
}
