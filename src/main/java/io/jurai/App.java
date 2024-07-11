package io.jurai;

import io.jurai.data.ApplicationState;
import io.jurai.ui.MainScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setHeight(800);
        stage.setWidth(1200);
        MainScene scene = new MainScene();
        scene.getScene().getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        stage.setScene(scene.getScene());
        ApplicationState.initialize();
        stage.show();
    }
}