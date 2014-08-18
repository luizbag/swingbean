package org.swingBean.exception;

public class ComponentCreationException extends RuntimeException {

	public ComponentCreationException() {
		super();
	}

	public ComponentCreationException(String message) {
		super(message);
	}

	public ComponentCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComponentCreationException(Throwable cause) {
		super(cause);
	}

}
