package com.jurai;

import com.jurai.data.ApplicationData;
import com.jurai.data.ApplicationState;
import com.jurai.data.ApplicationStatePersistor;
import com.jurai.ui.PrimaryScene;
import com.jurai.ui.SecondaryScene;
import com.jurai.ui.controller.StageController;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.SpacerFactory;
import com.jurai.util.EventLogger;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class App extends Application {
    private static App currentInstance;
    private Stage primaryStage;
    private Stage secondaryStage;
    private PrimaryScene primaryScene;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static List<Runnable> afterLoadTasks;

    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        currentInstance = this;
        afterLoadTasks = new LinkedList<>();
        initialize();
        var css = getClass().getResource("/style/style.css").toExternalForm();

        // primary stage
        this.primaryStage = stage;
        stage.setTitle("JurAI");
        stage.setMaxHeight(screenSize.height);
        stage.setMaxWidth(screenSize.width);
        stage.setMinWidth(screenSize.width * 0.3);
        stage.setMinHeight(screenSize.height * 0.5);
        stage.setWidth(screenSize.width);
        stage.setHeight(screenSize.height);
        stage.centerOnScreen();
        primaryScene = new PrimaryScene();
        primaryScene.getScene().getStylesheets().add(css);
        stage.setScene(primaryScene.getScene());
        selfAttachControllers();
        primaryStage.setOnCloseRequest(e -> onCloseRequest());

        secondaryStage = new Stage();
        secondaryStage.setTitle("JurAI - Login");
        secondaryStage.setWidth(screenSize.width * 0.3);
        secondaryStage.setHeight(screenSize.height * 0.75);
        secondaryStage.centerOnScreen();
        SecondaryScene secondaryScene = new SecondaryScene();
        secondaryScene.getScene().getStylesheets().add(css);
        secondaryStage.setScene(secondaryScene.getScene());
        secondaryStage.setOnCloseRequest(e -> onCloseRequest());

        switch(ApplicationState.getInstance().getStageType()) {
            case MAIN_STAGE:
                primaryStage.show();
                break;
            case SECONDARY_STAGE:
                secondaryStage.show();
                break;
        }

        afterLoadTasks.forEach(Runnable::run);
    }

    private void onCloseRequest() {
        try {
            ApplicationStatePersistor.getInstance().save();
        } catch (IOException e) {
            EventLogger.logError("Unable to save application state to JSON: " + e.getMessage());
        }
    }

    public void addAfterLoadTask(Runnable r) {
        afterLoadTasks.add(r);
    }

    private void selfAttachControllers() {
        StageController stageController = new StageController();
        stageController.initialize(this);
    }

    public static App getCurrentInstance() {
        return currentInstance;
    }

    public PrimaryScene getPrimaryScene() {
        return primaryScene;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage getSecondaryStage() {
        return secondaryStage;
    }

    private void initialize() {
        SpacerFactory.initialize();
        try {
            ApplicationStatePersistor.initialize();
            ApplicationStatePersistor.getInstance().load();
        } catch (IOException e) {
            EventLogger.logError("Failed to initialize ApplicationStatePersistor: " + e.getMessage());
        }
        ApplicationData.initialize();
    }
}