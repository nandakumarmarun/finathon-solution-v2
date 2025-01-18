package com.org.finfirm;

import com.org.finfirm.Config.WicketWebInitializer;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Files;

public class EmbeddedTomcatStarter {
    public static void main(String[] args) throws Exception {

        String webappDirLocation = "src/main/webapp/";
        String webappMount = "/";
        int port = 8081;

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);

        // Create temp directory for Tomcat
        File tomcatBaseDir = Files.createTempDirectory("tomcat-base-dir").toFile();
        tomcatBaseDir.deleteOnExit();
        tomcat.setBaseDir(tomcatBaseDir.getAbsolutePath());

        // Add webapp
        StandardContext ctx = (StandardContext) tomcat.addWebapp(webappMount,
                new File(webappDirLocation).getAbsolutePath());
        ctx.setParentClassLoader(EmbeddedTomcatStarter.class.getClassLoader());

        // Add compiled classes to Tomcat's resource root
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);



        ctx.addLifecycleListener((LifecycleListener) event -> {
            if ("before_start".equals(event.getType())) {
                // Initialize your application before context starts
                try {
                    new WicketWebInitializer().onStartup(ctx.getServletContext());
                } catch (Exception e) {
                    throw new RuntimeException("Failed to initialize the web application", e);
                }
            }
        });

        // Start Tomcat
        tomcat.start();
        System.out.println("Server started on port " + port);
        System.out.println("Base URL: http://localhost:" + port + webappMount);

        tomcat.getServer().await();

    }
}
