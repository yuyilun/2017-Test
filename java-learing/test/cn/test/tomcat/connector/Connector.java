package cn.test.tomcat.connector;

import cn.test.tomcat.server.LifeCycle;

public interface Connector extends LifeCycle{
	/**
	 * ��ȡ�����Ķ˿�
	 * @return
	 */
	int getPort();
	
	/**
	 * ��ȡ������IP��������
	 * @return
	 */
	String getHost();
}
