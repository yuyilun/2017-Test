package cn.test.tomcat.protocol.http.parser;
/**
 * body µÃÂbean
 * @author xyd-yuyilun
 *
 */
public class BodyInfo {
	
	private String contentType;
	private String encoding;
	private boolean hasBody;
	private int contentLength;
	
	public BodyInfo() {}

	public BodyInfo(String contentType, String encoding, boolean hasBody, int contentLength) {
		this.contentType = contentType;
		this.encoding = encoding;
		this.hasBody = hasBody;
		this.contentLength = contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isHasBody() {
		return hasBody;
	}

	public void setHasBody(boolean hasBody) {
		this.hasBody = hasBody;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	
	
	
}
