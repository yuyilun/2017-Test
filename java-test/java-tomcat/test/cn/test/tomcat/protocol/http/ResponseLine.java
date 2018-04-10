package cn.test.tomcat.protocol.http;

public class ResponseLine implements StartLine{
	
	private final String httpVersion;
	private final int statusCode;
	private final String reason;
	
	public ResponseLine(String httpVersion, int statusCode, String reason) {
		this.httpVersion = httpVersion;
		this.statusCode = statusCode;
		this.reason = reason;
	}
	
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public String getReason() {
		return reason;
	}
	
	public String asString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(this.httpVersion).append(" ").append(this.statusCode)
			.append(" ").append(this.reason);
		return stringBuffer.toString();
	}
	
	@Override
	public String getHttpVersion() {
		return httpVersion;
	}
	
}
