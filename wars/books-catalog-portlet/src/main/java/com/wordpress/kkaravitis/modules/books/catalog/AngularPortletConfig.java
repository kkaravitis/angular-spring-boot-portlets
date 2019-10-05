package com.wordpress.kkaravitis.modules.books.catalog;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Konstantinos Karavitis
 **/
public class AngularPortletConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	private String apiUrl;
	private Map<String, String> messages;
	
	public AngularPortletConfig() {}
	
	public AngularPortletConfig(String apiUrl, ResourceBundle resourceBundle) {
		this.apiUrl = apiUrl;
		
		this.messages = new HashMap<>();
		Enumeration<String> keys = resourceBundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			messages.put(key, resourceBundle.getString(key));
		}
	}
	
	public String getApiUrl() {
		return apiUrl;
	}
	
	public Map<String, String> getMessages() {
		return messages;
	}
	
}
