package cn.test.tomcat.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * ���ӽӿ�
 * @author xyd-yuyilun
 *
 */
public interface Connection {
	/**
	 *  д��byte[]�����е���������
	 * @param bytes
	 * @throws IOException
	 */
	void write(byte[] bytes) throws IOException;
	
	/**
	 *  д��byte[]������[offset,length-1]������
	 * @param bytes
	 * @param offset
	 * @param length
	 * @throws IOException
	 */
    void write(byte[] bytes,int offset,int length) throws IOException;
    
    /**
     * ��ȡ��������byte[]����
     * @param bytes
     * @return
     * @throws IOException
     */
    int read(byte[] bytes) throws IOException;
    
    /**
     * ��ȡ����д��byte[offset,length-1]
     * @param bytes
     * @param offset
     * @param length
     * @return
     * @throws IOException
     */
    int read(byte[] bytes,int offset,int length) throws IOException;
    
    /**
     *  ��ȡSocket�е�������
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
    
    /**
     * ��ȡSocket�е������
     * @return
     * @throws IOException
     */
    OutputStream getOutputStream() throws IOException;
    
    
}
