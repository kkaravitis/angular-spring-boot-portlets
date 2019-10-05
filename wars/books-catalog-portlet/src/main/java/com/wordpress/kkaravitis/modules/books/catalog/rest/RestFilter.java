package com.wordpress.kkaravitis.modules.books.catalog.rest;

import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
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
            PortletRequest portletRequest = (PortletRequest) request.getAttribute("javax.portlet.request");
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
            return portalRequest.getParameter("rest_method");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new RestServiceRequest(request), response);
    }

    @Override
    public void destroy() {
    }
}
