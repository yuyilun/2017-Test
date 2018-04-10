package cn.test.tomcat.protocol.http.body;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class HttpBody {
	
	private String contentType;
	private String encoding;
	private byte[] content;
	private InputStream inputStream;
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public HttpBody(InputStream inputStream) {

		this.inputStream = inputStream;
	}

	public HttpBody(byte[] content) {
		this.content = content;
	}

	public HttpBody(String contentType, String encoding, byte[] content) {
		this.contentType = contentType;
		this.encoding = encoding;
		this.content = content;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * ��ȡHttpBody���� �޷���String��ʾ�����ݣ�����ͼƬ���ļ�
	 *
	 * @return
	 */
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getContentType() {
		return this.contentType;
	}

	public String getEncoding() {
		return this.encoding;
	}
	

	/**
	 * ��ȡ��ȡHttpBody���� ������String��ʾ�����ݣ�����json�ַ���
	 *
	 * @return
	 */
	public String getBodyAsString() throws UnsupportedEncodingException {
		return new String(this.content, this.encoding);
	}

}
