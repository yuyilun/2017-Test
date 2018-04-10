package cn.test.tomcat.protocol.http;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentTypeUtil {
	
	  private static final Logger logger = LoggerFactory.getLogger(ContentTypeUtil.class);
	  private static Properties properties;
	  
	  private ContentTypeUtil() {}
	  
	  static {
		  properties = new Properties();
		  try {
			properties.load(ContentTypeUtil.class.getClassLoader().getResourceAsStream("mime-mapping.properties"));
			logger.info("load mime-mapping.properties");
		} catch (IOException e) {
			logger.error("load mime-mapping.properties failed", e);
		}
	  }
	  
	  public static String getContentType(String filePrefix) {
		  return properties.getProperty(filePrefix,"application/octet-stream");
	  }
}
