package io.jurai;

import io.jurai.data.ApplicationState;
import io.jurai.ui.MainScene;
import io.jurai.ui.util.SpacerFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private static Thread ctlThread;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initialize();

        stage.setHeight(800);
        stage.setWidth(1200);
        MainScene scene = new MainScene();
        scene.getScene().getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        stage.setScene(scene.getScene());
        stage.show();

        ApplicationState.addPropertyChangeListener(e -> {
            if(e.getPropertyName().equals("debugging")) {
                if((boolean) e.getNewValue()) {
                    stage.setOnCloseRequest(_ -> ctlThread.interrupt());
                } else {
                    stage.setOnCloseRequest(_ -> {});
                }
            }
        });

        //this is to trigger the above listener
        ApplicationState.setDebugging(ApplicationState.isDebugging());
    }

    public static void setCtlThread(Thread t) {
        ctlThread = t;
    }

    private void initialize() {
        SpacerFactory.initialize();
    }
}