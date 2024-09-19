package com.jurai;

import com.jurai.data.ApplicationData;
import com.jurai.data.ApplicationState;
import com.jurai.ui.PrimaryScene;
import com.jurai.ui.SecondaryScene;
import com.jurai.ui.controller.StageController;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.SpacerFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;

public class App extends Application {
    private static Thread ctlThread;
    private static App currentInstance;
    private Stage primaryStage;
    private Stage secondaryStage;
    private PrimaryScene primaryScene;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private long lastFrameTime = 0;
    private int frameCount = 0;
    private double fps = 0;
    private double elapsedTime = 0;

    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initialize();
        currentInstance = this;
        var css = getClass().getResource("/style/style.css").toExternalForm();
        selfAttachControllers();

        // primary stage
        this.primaryStage = stage;
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

        // secondary stage
        secondaryStage = new Stage();
        secondaryStage.setWidth(screenSize.width * 0.3);
        secondaryStage.setHeight(screenSize.height * 0.75);
        secondaryStage.centerOnScreen();
        SecondaryScene secondaryScene = new SecondaryScene();
        secondaryScene.getScene().getStylesheets().add(css);
        secondaryStage.setScene(secondaryScene.getScene());
        ApplicationState.getInstance().setAccountMode(AccountMode.LOGGING_IN);
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

    public static void setCtlThread(Thread t) {
        ctlThread = t;
    }

    private void initialize() {
        SpacerFactory.initialize();
        ApplicationState.initialize();
        ApplicationData.initialize();
    }
}