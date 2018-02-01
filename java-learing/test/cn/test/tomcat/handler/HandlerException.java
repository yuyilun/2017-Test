package cn.test.tomcat.handler;

public class HandlerException extends RuntimeException{
	private static final long serialVersionUID = -6973501856656292948L;

	public HandlerException() {
	        super();
    }

    public HandlerException(String message) {
        super(message);
    }

    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlerException(Throwable cause) {
        super(cause);
    }

    protected HandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
