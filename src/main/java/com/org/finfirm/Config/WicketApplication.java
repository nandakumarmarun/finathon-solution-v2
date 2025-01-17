package com.org.finfirm.Config;

import com.org.finfirm.Pages.IndexPage;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;


@Component
public class WicketApplication extends WebApplication{
    @Override
    public Class<? extends Page> getHomePage() {
        return IndexPage.class;
    }

    @Override
    public void init() {
        super.init();
        // Log to verify Spring context initialization
        System.out.println("Spring context is being initialized.");

        // Ensure Spring context is loaded
        WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        // Adding Spring Component Injector to Wicket
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
    }
}
