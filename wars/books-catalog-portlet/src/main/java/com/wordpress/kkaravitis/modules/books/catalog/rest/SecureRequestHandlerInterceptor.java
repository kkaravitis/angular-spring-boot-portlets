package com.wordpress.kkaravitis.modules.books.catalog.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.model.User;
import com.wordpress.kkaravitis.modules.books.catalog.Constants;
import com.wordpress.kkaravitis.modules.books.catalog.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Konstantinos Karavitis
 **/
public class SecureRequestHandlerInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	MessageSource messageSource;

	Logger logger = LoggerFactory.getLogger(SecureRequestHandlerInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = (User) request.getAttribute(WebKeys.USER);
		if (user == null) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			ErrorResponse error = new ErrorResponse();
			error.setErrorCode(HttpStatus.UNAUTHORIZED.value());
			error.setMessage(messageSource.getMessage(Constants.APPLICATION_ERROR_UNAUTHORIZED, null, request.getLocale()));
			response.getWriter().write(new ObjectMapper().writeValueAsString(error));
			return false;
		}

		return true;
	}
}
