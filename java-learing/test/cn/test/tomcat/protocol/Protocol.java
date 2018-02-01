package cn.test.tomcat.protocol;

public interface Protocol {
	
	/**
	 * 返回协议名
	 * @return
	 */
	String getName();
	
	/**
	 * 返回协议版本
	 * @return
	 */
	String getVersion();
	
	
}
