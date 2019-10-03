package com.wordpress.kkaravitis.portal.portlet;

import com.wordpress.kkaravitis.portal.portlet.exception.ApplicationException;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Konstantinos Karavitis
 **/
public class CrudExamplePortlet extends AbstractAngularPortlet {
    @Override
    protected AngularPortletConfig createAngularPortletConfiguration(String apiUrl, RenderRequest request, RenderResponse response) throws ApplicationException {
        return new AngularPortletConfig(apiUrl, getResourceBundle(request.getLocale()));
    }
}
