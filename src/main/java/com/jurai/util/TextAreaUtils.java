package com.jurai.util;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TextAreaUtils {

    /*
    This is VERY hacky, but its the only thing that somewhat worked. So I will be using it until I find something better.
     */
    public static int getVisualLineCount(TextArea textArea) {
        String text = textArea.getText();
        if (text.isEmpty()) return 1;

        Text helper = new Text();
        helper.setFont(textArea.getFont());

        double usableWidth = textArea.getWidth() - 20;
        if (textArea.lookup(".scroll-bar:vertical") != null) {
            usableWidth -= 15;
        }

        helper.setWrappingWidth(Math.max(50, usableWidth));
        helper.setText(text);

        Group tempGroup = new Group(helper);
        if (textArea.getScene() != null) {
            ((Pane) textArea.getScene().getRoot()).getChildren().add(tempGroup);
            tempGroup.autosize();
            ((Pane) textArea.getScene().getRoot()).getChildren().remove(tempGroup);
        }

        double lineHeight = helper.getFont().getSize() * 1.2;
        double totalHeight = helper.getBoundsInLocal().getHeight();

        return Math.max(1, (int) Math.ceil(totalHeight / lineHeight));
    }

}