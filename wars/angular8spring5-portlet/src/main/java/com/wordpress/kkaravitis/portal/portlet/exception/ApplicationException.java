package com.wordpress.kkaravitis.portal.portlet.exception;

/**
 * The application's error exception. Every application exception has a code
 * which is used for retrieving the message of the error that will be displayed
 * to the user from the localized messages.properties.
 * 
 * @author Konstantinos Karavitis
 **/
public class ApplicationException extends Exception {
	private static final long serialVersionUID = 1L;

	public ApplicationException(Integer code) {
		super();
		this.code = code;
	}

	public ApplicationException(Integer code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public ApplicationException(Throwable throwable) {
		super(throwable);
	}

	private Integer code;

	/**
	 * Returns the code of application exception
	 **/
	public Integer getCode() {
		return code;
	}

	/**
	 * Sets the code of application exception
	 **/
	public void setCode(Integer code) {
		this.code = code;
	}
}
