package org.swingBean.exception;

public class ComponentExecutionException extends RuntimeException {

	public ComponentExecutionException() {
	}

	public ComponentExecutionException(String message) {
		super(message);
	}

	public ComponentExecutionException(Throwable cause) {
		super(cause);
	}

	public ComponentExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

}
