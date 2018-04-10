package cn.test.tomcat.server;

import java.io.IOException;
import java.util.Set;

import cn.test.tomcat.connector.Connector;

public interface Server {
	/**
	 * ����������
	 * @throws IOException 
	 * @throws Exception 
	 */
	void start() throws IOException;
	
	/**
	 * �رշ�����
	 */
	void stop();
	
	/**
	 * ��ȡ������״̬
	 * @return
	 */
	ServerStatus getStatus();
	
	/**
	 * ��ȡ�����������Connector�б�
	 * @return
	 */
	Set<Connector> getConnectors();

}
