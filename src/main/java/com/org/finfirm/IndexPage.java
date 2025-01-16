package com.org.finfirm;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class IndexPage extends WebPage {
    public IndexPage() {
        add(new Label("message", "Welcome to Wicket with Index"));
    }
}
