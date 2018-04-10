package cn.test.tomcat.test;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.test.tomcat.server.Server;
import cn.test.tomcat.server.ServerStatus;

public abstract class TestServerBase {
	
	private static Logger logger = LoggerFactory.getLogger(TestServerBase.class);
	 /**
     * �ڵ������߳�������Server������������ɹ����׳��쳣
     *
     * @param server
     */
	protected void startServer(Server server) {
		new Thread(() -> {
			try {
                server.start();
            } catch (IOException e) {
                //תΪRuntimeException�׳��������쳣��ʧ
                throw new RuntimeException(e);
            }
		}).start();
	}
	
	/**
     * �ȴ�Server����
     *
     * @param server
     */
	protected void waitServerStart(Server server) {
		
		while(server.getStatus().equals(ServerStatus.STOPED)) {
			 logger.info("�ȴ�server����");
			 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		
	}
	
	
}
