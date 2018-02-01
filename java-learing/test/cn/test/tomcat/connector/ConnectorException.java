package cn.test.tomcat.connector;
/**
 * 自定义异常
 * @author xyd-yuyilun
 *
 */
public class ConnectorException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4079219207018742352L;

	public ConnectorException() {
		
	}
	
	public ConnectorException(String message) {
        super(message);
	} 
	
	public ConnectorException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public ConnectorException(Throwable cause) {
        super(cause);
    }
	
	public ConnectorException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	
	
}
