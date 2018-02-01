package cn.test.tomcat.listener;

public class EventException extends RuntimeException {
    /**
	 */
	private static final long serialVersionUID = 5953481833265421436L;

	public EventException() {
        super();
    }

    public EventException(String message) {
        super(message);
    }

    public EventException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventException(Throwable cause) {
        super(cause);
    }

    protected EventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}