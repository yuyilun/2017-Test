package cn.test.tomcat.protocol.http.header;

import java.util.List;
import java.util.Set;


public interface IMessageHeaders {
	
	
	/**
	 * ��ȡ����ΪheaderName��HttpHeader�б�
	 * @param headerName
	 * @return
	 */
	List<HttpHeader> getHeaders(String headerName);
	
	/**
	 * ��ȡ��һ������ΪheaderName��HttpHeader
	 * @param headerName
	 * @return
	 */
	HttpHeader getFirstHeader(String headerName);
	
	
	/**
	 * ��ȡ���е�HttpHeader
	 * @return
	 */
	List<HttpHeader> getAllHeaders();
	
	/**
	 * ���HttpHeader��������
	 * @param httpHeader
	 */
	void addHeader(HttpHeader httpHeader);
	
	/**
     * �Ӽ������Ƴ�HttpHeader
     *
     * @param httpHeader
     */
    void removeHeader(HttpHeader httpHeader);

    /**
     * �Ӽ������Ƴ�����ΪheaderName������HttpHeader
     *
     * @param headerName
     */
    void removeHeaders(String headerName);
    
    /** 
     * �жϼ������Ƿ��������ΪheaderName��HttpHeader
     * @param headerName
     * @return
     */
    boolean hasHeader(String headerName);
    
    /**
     * ��������HttpHeader�����֣���ȥ��
     * @return
     */
    Set<String> getHeaderNames();
    
    /**
     * ��Headers�����еļ�ֵ��ת��ΪHTTPЭ���й涨���ַ�����ʽ��
     * key:value CRLF
     * ... 
     * @return
     */
    String asString();
    
    
    
    
    
}
