package cn.test.tomcat.protocol.http;

import cn.test.tomcat.protocol.Protocol;

public class HttpProtocol implements Protocol {
	
	private final String name = "http";
	private final String version;
	
	private HttpProtocol(String version) {
		this.version = version;
	}

	public static final HttpProtocol  VERSION11 = new HttpProtocol("1.1");
	public static final HttpProtocol  VERSION20 = new HttpProtocol("2.0");
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getVersion() {
		return this.version;
	}
	
	@Override
	public String toString() {
		return this.name + "/" + this.version;
	}
	
	

}
