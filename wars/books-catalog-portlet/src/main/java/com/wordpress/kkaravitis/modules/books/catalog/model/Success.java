package com.wordpress.kkaravitis.modules.books.catalog.model;

import java.io.Serializable;

/**
 * @author Konstantinos Karavitis
 **/
public class Success implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String message;

	public Success(String messageKey) {
		super();
		this.message = messageKey;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
