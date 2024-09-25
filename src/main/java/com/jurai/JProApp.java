package com.jurai;

import com.jpro.webapi.JProApplication;
import javafx.application.Platform;
import javafx.stage.Stage;

public class JProApp extends JProApplication {
    @Override
    public void start(Stage primaryStage) throws Exception {
        new App().start(primaryStage);
    }
}