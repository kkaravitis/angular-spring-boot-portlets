package com.wordpress.kkaravitis.portal.portlet.rest;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.model.User;
import com.wordpress.kkaravitis.portal.portlet.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Konstantinos Karavitis
 **/
public class SecureRequestHandlerInterceptor extends HandlerInterceptorAdapter {
	Logger logger = LoggerFactory.getLogger(SecureRequestHandlerInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = (User) request.getAttribute(WebKeys.USER);
		if (user == null) {
			response.setStatus(403);
			ErrorResponse error = new ErrorResponse();
			error.setErrorCode(403);
			error.setMessage("Unauthorized request");
			response.getWriter().write(new ObjectMapper().writeValueAsString(error));
			return false;
		}
		return true;
	}
}
