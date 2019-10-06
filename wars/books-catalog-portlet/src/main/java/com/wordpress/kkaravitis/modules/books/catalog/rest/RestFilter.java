package com.wordpress.kkaravitis.modules.books.catalog.rest;

import com.liferay.portal.kernel.util.PortalUtil;
import com.wordpress.kkaravitis.modules.books.catalog.Constants;
import org.springframework.http.HttpStatus;

import javax.portlet.PortletRequest;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author Konstantinos Karavitis
 **/
public class RestFilter implements Filter {
    private static class RestServiceRequest extends HttpServletRequestWrapper {
        private HttpServletRequest portalRequest;

        public RestServiceRequest(ServletRequest request) {
            super(HttpServletRequest.class.cast(request));
            initPortalRequest(request);
        }

        private void initPortalRequest(ServletRequest request) {
            PortletRequest portletRequest = (PortletRequest) request.getAttribute(Constants.JAVAX_PORTLET_REQUEST);
            portalRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(portletRequest));
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return portalRequest.getParameterMap();
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return portalRequest.getParameterNames();
        }

        @Override
        public String getParameter(String name) {
            return portalRequest.getParameter(name);
        }

        @Override
        public String[] getParameterValues(String name) {
            return portalRequest.getParameterValues(name);
        }

        @Override
        public String getMethod() {
            return portalRequest.getParameter(Constants.REST_METHOD);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            RestServiceRequest restServiceRequest = new RestServiceRequest(request);
            chain.doFilter(restServiceRequest, response);
        } catch (Exception e) {
            ((HttpServletResponse)response).setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        }
    }

    @Override
    public void destroy() {
    }
}
