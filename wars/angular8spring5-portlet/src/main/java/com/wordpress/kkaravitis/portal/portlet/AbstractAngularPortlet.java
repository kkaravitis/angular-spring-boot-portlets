package com.wordpress.kkaravitis.portal.portlet;

import java.io.IOException;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;
import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.util.PortalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wordpress.kkaravitis.portal.portlet.exception.ApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>
 * Abstract base class for developing an Angular 8 portlet.
 * </p>
 * <p>
 * Every implementation of this class must implement the
 * {@link #createAngularPortletConfiguration(String, RenderRequest, RenderResponse)}
 * method by returning a pojo with properties that will be used for angular
 * application initialization.
 * </p>
 * 
 * @author Konstantinos Karavitis
 **/
public abstract class AbstractAngularPortlet extends GenericPortlet {
	public static final String SYSTEM_ERROR_KEY = "application.error.fatal";
	public static final String VIEW_TEMPLATE_PORTLET_INIT_PARAM = "view-template";
	public static final String PORTLET_XML_APPLICATION_CONTEXT = "portletContext.xml";
	public static final String ANGULAR_CONFIGURATION_PROPERTY = "config";
	public static final String REST_SERVLET_URL_PATTERN_PATH = "/rest";
	public static final String REST_SERVICE_PATH_PARAM = "rest_path";
	
	private String viewTemplate;
	private ClassPathXmlApplicationContext context;
	private Logger logger;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * Initializes the portlet.
	 **/
	@Override
	public void init() throws PortletException {
		logger = LoggerFactory.getLogger(this.getClass());
		viewTemplate = getPortletConfig().getInitParameter(VIEW_TEMPLATE_PORTLET_INIT_PARAM);
		context = new ClassPathXmlApplicationContext(PORTLET_XML_APPLICATION_CONTEXT);
		super.init();
	}
	
	/**
	 * On portlet render phase the angular portlet
	 * configuration is recreated because a new resource url must be created
	 * from the new render response and be used as the base api url.
	 **/
	@Override
	public void doView(RenderRequest request, RenderResponse response) throws IOException, PortletException {
		try {
			ResourceURL resourceURL = response.createResourceURL();
			String apiUrl = resourceURL.toString();
			AngularPortletConfig config = createAngularPortletConfiguration(apiUrl, request, response);
			String jsonConfig = objectMapper.writeValueAsString(config);
			request.setAttribute(ANGULAR_CONFIGURATION_PROPERTY, jsonConfig);
			getPortletContext().getRequestDispatcher(viewTemplate).include(request, response);
		} catch (Exception exception) {
			throw new PortletException(exception);
		}
	}
	
	/**
	 * Every AJAX request (supported methods: GET and POST) from angular app is
	 * handled on this method first. At this phase the portlet resolves the uri
	 * that the resource request must be dispatched by reading the "rest_path"
	 * resource request parameter.
	 **/
	@Override
	public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
		HttpServletRequest portalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
		
		String dispatchUrl = String.format("%s%s", REST_SERVLET_URL_PATTERN_PATH,
				portalRequest.getParameter(REST_SERVICE_PATH_PARAM));
		String restServiceHttpMethod = portalRequest.getParameter("rest_method");
		getPortletContext().getRequestDispatcher(dispatchUrl).forward(request, response);
	}
	
	/**
	 * Returns an {@link AngularPortletConfig} implementation for
	 * initializing the angular application.
	 **/
	protected abstract AngularPortletConfig createAngularPortletConfiguration(String apiUrl, RenderRequest request, RenderResponse response) throws ApplicationException;

	/**
	 * Returns the view template (view.jsp) of the portlet.
	 **/
	protected String getViewTemplate() {
		return viewTemplate;
	}

	/**
	 * Returns the Spring application context of the portlet app. 
	 **/
	protected ClassPathXmlApplicationContext getContext() {
		return context;
	}
	
	/**
	 * Returns the logger
	 **/
	protected Logger getLogger() {
		return logger;
	}
}