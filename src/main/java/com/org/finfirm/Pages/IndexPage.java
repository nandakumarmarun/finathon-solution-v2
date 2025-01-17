package com.org.finfirm.Pages;

import com.org.finfirm.Pages.LoginPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

public class IndexPage extends WebPage {
    public IndexPage() {
        add(new Label("welcomeMessage", "Welcome to the application!"));

        // Adding a button that redirects to the login page
        add(new Link<Void>("loginButton") {
            @Override
            public void onClick() {
                setResponsePage(LoginPage.class);
            }
        });
    }
}
