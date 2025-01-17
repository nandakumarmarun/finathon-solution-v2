package com.org.finfirm.Config;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.startup.Tomcat;
import javax.servlet.ServletContext;

public class EmbeddedTomcatStarter {
    public static void main(String[] args) throws Exception {
        // Create Tomcat instance
        Tomcat tomcat = new Tomcat();

        // Set the base directory for Tomcat
        tomcat.setBaseDir("tomcat");

        // Set the port (default is 8080)
        tomcat.setPort(8081);

        // Add context
        Context context = tomcat.addContext("/", null);

        // Get ServletContext from the Context
        ServletContext servletContext = context.getServletContext();

        // Add a lifecycle listener to configure the context before it is initialized
        context.addLifecycleListener((LifecycleListener) event -> {
            if ("before_start".equals(event.getType())) {
                // Initialize your application before context starts
                try {
                    new WicketWebInitializer().onStartup(context.getServletContext());
                } catch (Exception e) {
                    throw new RuntimeException("Failed to initialize the web application", e);
                }
            }
        });


        // Start Tomcat
        tomcat.start();

        // Keep the server running
        tomcat.getServer().await();
    }
}
