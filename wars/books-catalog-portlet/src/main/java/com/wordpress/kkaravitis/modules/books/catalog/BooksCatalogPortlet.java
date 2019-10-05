package com.wordpress.kkaravitis.modules.books.catalog;

import com.wordpress.kkaravitis.modules.books.catalog.exception.ApplicationException;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Konstantinos Karavitis
 **/
public class BooksCatalogPortlet extends AbstractAngularPortlet {
    @Override
    protected AngularPortletConfig createAngularPortletConfiguration(String apiUrl, RenderRequest request, RenderResponse response) throws ApplicationException {
        return new AngularPortletConfig(apiUrl, getResourceBundle(request.getLocale()));
    }
}
