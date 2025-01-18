package com.org.finfirm.Pages;

import com.org.finfirm.Pages.LoginPage;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
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

        add(new Link<Void>("regButton") {
            @Override
            public void onClick() {
                setResponsePage(RegistrationPage.class);
            }
        });
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        // Include Bootstrap CSS from a CDN
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"));
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"));
    }
}
