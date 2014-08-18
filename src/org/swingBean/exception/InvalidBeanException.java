package org.swingBean.exception;

public class InvalidBeanException extends RuntimeException {

	public InvalidBeanException() {
		super();
	}

	public InvalidBeanException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidBeanException(String message) {
		super(message);
	}

	public InvalidBeanException(Throwable cause) {
		super(cause);
	}

}
