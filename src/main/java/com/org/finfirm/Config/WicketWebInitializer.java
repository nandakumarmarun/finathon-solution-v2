package com.org.finfirm.Config;

import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

public class WicketWebInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext sc) {
        // 1. Creates Spring context
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        // 2. Registers configuration classes
        context.register(AppConfig.class, DatabaseConfig.class);
        context.register(AppConfig.class, SecurityConfig.class);

        // 3. Sets up Spring context listener
        sc.addListener(new ContextLoaderListener(context));

        // 4. Sets up Wicket filter
        FilterRegistration.Dynamic wicketFilter = sc.addFilter("wicket-filter",
                "org.apache.wicket.protocol.http.WicketFilter");
        wicketFilter.setInitParameter("applicationClassName", WicketApplication.class.getName()); // Specify application class
        wicketFilter.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*"); // Set the filter path
        wicketFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
