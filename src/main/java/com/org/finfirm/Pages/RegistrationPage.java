package com.org.finfirm.Pages;

import com.org.finfirm.dto.UserDto;
import com.org.finfirm.services.UserServices;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistrationPage extends WebPage {


    @SpringBean
    private UserServices userServices;

    public RegistrationPage() {
        TextField<String> usernameField = new TextField<>("username", new Model<>());
        PasswordTextField passwordField = new PasswordTextField("password", new Model<>());

        Form<Void> registrationForm = new Form<Void>("registrationForm") {
            @Override
            protected void onSubmit() {
                String username = usernameField.getModelObject();
                String password = passwordField.getModelObject();
                // You can add password matching validation here
                UserDto userDto = new UserDto();
                userDto.setUserName(username);
                userDto.setPassword(password);

                // Call the saveUser method to save the user
                UserDto savedUser = userServices.saveUser(userDto);
                // You can add a success message here or redirect to another page
                info("User registered successfully: " + savedUser.getUserName());
                if(savedUser != null){
                    setResponsePage(LoginPage.class);
                }
            }
        };

        registrationForm.add(usernameField);
        registrationForm.add(passwordField);
        add(registrationForm);

        add(new Link<Void>("cancelBtn") {
            @Override
            public void onClick() {
                setResponsePage(IndexPage.class);
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
