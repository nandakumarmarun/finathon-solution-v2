package com.org.finfirm;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WicketApplication extends WebApplication{
    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init() {
        super.init();
        // Load Spring ApplicationContext
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // Integrate Spring with Wicket
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, context));
    }
}
