package com.org.finfirm.Config;

import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WicketWebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext sc) {
        // Create Spring context
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        // Register your configuration classes
        context.register(AppConfig.class, SecurityConfig.class, WebConfig.class,DatabaseConfig.class);

        // Create and register Spring ContextLoaderListener
        sc.addListener(new ContextLoaderListener(context));

        // Create and register Spring Security Filter
        FilterRegistration.Dynamic securityFilter = sc.addFilter("springSecurityFilterChain",
                DelegatingFilterProxy.class);
        securityFilter.addMappingForUrlPatterns(null, false, "/*");

        // Create and register Spring MVC DispatcherServlet for /api/* endpoints
        ServletRegistration.Dynamic dispatcher = sc.addServlet("dispatcher",
                new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/api/*");

        // Create and register Wicket Filter
        FilterRegistration.Dynamic wicketFilter = sc.addFilter("wicketFilter",
                WicketFilter.class);
        wicketFilter.setInitParameter("applicationClassName",
                WicketApplication.class.getName());
        wicketFilter.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
        wicketFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
