package com.jurai.ui.controls;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class NavUrl extends Label {
    public NavUrl() {
        super("/");
        setId("nav-url");
    }

    public void setUrl(String url) {
        setText(url);
    }

    public String getUrl() {
        return getText();
    }
}
