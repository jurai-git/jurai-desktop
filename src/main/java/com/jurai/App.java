package com.jurai;

import com.jurai.data.ApplicationData;
import com.jurai.data.ApplicationState;
import com.jurai.data.ApplicationStatePersistor;
import com.jurai.data.GlobalEvents;
import com.jurai.data.model.Theme;
import com.jurai.ui.PrimaryScene;
import com.jurai.ui.SecondaryScene;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controller.StageController;
import com.jurai.ui.controls.NavUrl;
import com.jurai.ui.manager.ThemeManager;
import com.jurai.ui.modal.ModalManager;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.SpacerFactory;
import com.jurai.util.EventLogger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class App extends Application {
    @Getter
    private static App currentInstance;
    @Getter
    private Stage primaryStage;
    @Getter
    private Stage secondaryStage;
    @Getter
    private PrimaryScene primaryScene;
    @Getter
    private SecondaryScene secondaryScene;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static List<Runnable> afterLoadTasks;

    public static void main(String[] args) {
        System.setProperty("prism.allowhidpi", "false");
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("glass.gtk.uiScale", "4.0");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        currentInstance = this;
        afterLoadTasks = new LinkedList<>();
        initialize();

        String css = getClass().getResource("/style/style.css").toExternalForm();
        ThemeManager themeManager = new ThemeManager();

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
        themeManager.addScene(primaryScene.getScene());
        stage.setScene(primaryScene.getScene());
        primaryStage.setOnCloseRequest(e -> onCloseRequest());

        secondaryStage = new Stage();
        secondaryStage.setTitle("JurAI - Login");
        secondaryStage.setWidth(screenSize.width * 0.3);
        secondaryStage.setHeight(screenSize.height * 0.75);
        secondaryStage.centerOnScreen();
        secondaryScene = new SecondaryScene();
        secondaryScene.getScene().getStylesheets().add(css);
        themeManager.addScene(secondaryScene.getScene());
        secondaryStage.setScene(secondaryScene.getScene());
        secondaryStage.setOnCloseRequest(e -> onCloseRequest());
        selfAttachControllers();

        afterLoadTasks.forEach(Runnable::run);

        primaryScene.getScene().widthProperty().addListener((observableValue, number, t1) -> {
            ApplicationState.getInstance().setViewportSmall(t1.doubleValue() < (double) ApplicationData.getScreenSize().width * 0.65);
        });

        switch(ApplicationState.getInstance().getStageType()) {
            case MAIN_STAGE:
                primaryStage.show();
                System.out.println(primaryScene.getModalRoot());
                ModalManager.getInstance().reinitialize(primaryScene.getModalRoot(), primaryScene.getContent());
                break;
            case SECONDARY_STAGE:
                secondaryStage.show();
                System.out.println(secondaryScene.getModalRoot());
                ModalManager.getInstance().reinitialize(secondaryScene.getModalRoot(), secondaryScene.getContent());
                break;
        }
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

    private void initialize() {
        ApplicationData.initialize();
        GlobalEvents.initialize();
        try {
            ApplicationStatePersistor.initialize();
            ApplicationStatePersistor.getInstance().load();
        } catch (IOException e) {
            EventLogger.logError("Failed to initialize ApplicationStatePersistor: " + e.getMessage());
        }
        ModalManager.initialize(null, null);
        HoverAnimator.initialize();
    }
}