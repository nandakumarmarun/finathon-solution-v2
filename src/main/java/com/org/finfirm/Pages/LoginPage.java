package com.org.finfirm.Pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginPage extends WebPage {

    private String username;
    private String password;

    @SpringBean
    private AuthenticationManager authenticationManager;
    public LoginPage() {

        // Creating a login form
        Form<Void> loginForm = new Form<Void>("loginForm") {
            @Override
            protected void onSubmit() {
                // Create an authentication token
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                try {
                    // Authenticate the user using the AuthenticationManager
                    Authentication authentication = authenticationManager.authenticate(authRequest);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // Redirect to home page upon successful login
                    setResponsePage(IndexPage.class);
                } catch (Exception e) {
                    // Handle authentication failure
                    error("Invalid username or password");
                }
            }
        };

        // Add text fields for username and password
        loginForm.add(new TextField<String>("username", new PropertyModel<String>(this, "username")));
        loginForm.add(new PasswordTextField("password", new PropertyModel<String>(this, "password")));

        // Add the form to the page
        add(loginForm);
    }
}
