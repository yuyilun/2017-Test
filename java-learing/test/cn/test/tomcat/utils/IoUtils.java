package cn.test.tomcat.utils;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoUtils {
	private static Logger logger = LoggerFactory.getLogger(IoUtils.class);
	
	public static void closeQuietly(Closeable closeable) {
		if(closeable != null) {
			try {
				closeable.close();
			}catch(IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
}
