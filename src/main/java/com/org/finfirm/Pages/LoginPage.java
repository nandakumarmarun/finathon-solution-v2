package com.org.finfirm.Pages;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginPage extends WebPage {

    private String username;
    private String password;

    @SpringBean
    private AuthenticationManager authenticationManager;
    public LoginPage() {

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        // Creating a login form
        // Creating a login form
        Form<Void> loginForm = new Form<Void>("loginForm") {
            @Override
            protected void onSubmit() {
                if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                    error("Username and password must be provided");
                    return;
                }

                // Create an authentication token
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                try {
                    // Authenticate the user using the AuthenticationManager
                    Authentication authentication = authenticationManager.authenticate(authRequest);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (BadCredentialsException e) {
                    // Handle invalid username/password
                    error("Invalid username or password");
                } catch (Exception e) {
                    // Handle other exceptions
                    error("An error occurred during login");
                }
            }
        };

        // Add text fields for username and password
        loginForm.add(new TextField<String>("username", new PropertyModel<String>(this, "username")));
        loginForm.add(new PasswordTextField("password", new PropertyModel<String>(this, "password")));

        // Add the form to the page
        add(loginForm);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        // Include Bootstrap CSS from a CDN
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"));
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"));
    }
}
