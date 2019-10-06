package com.wordpress.kkaravitis.modules.books.catalog.model;

import java.io.Serializable;

/**
 * Error response on resource request (ajax) error.
 * 
 * @author Konstantinos Karavitis
 **/
public class ErrorResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer errorCode;
	private String message;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorResponse{" +
				"errorCode=" + errorCode +
				", message='" + message + '\'' +
				'}';
	}
}
