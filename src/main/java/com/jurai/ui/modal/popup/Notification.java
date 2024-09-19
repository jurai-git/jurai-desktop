package com.jurai.ui.modal.popup;

import com.jurai.data.ApplicationData;
import com.jurai.data.ApplicationState;
import com.jurai.util.FileUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.awt.*;

public class Notification extends Popup {

    public Notification (String label) {
        super();
        VBox contentPane = generateContentPane(label);
        setTitle("Notificação");
        setHeaderText(null);
        var screenSize = ApplicationData.getScreenSize();
        getDialogPane().setMinSize(screenSize.width * 0.2, screenSize.height * 0.2);
        getDialogPane().getStylesheets().addAll("root-pane", "notification");

        getDialogPane().setContent(contentPane);
        initStyle(StageStyle.UNDECORATED);
        getDialogPane().getStylesheets().addAll(ApplicationState.getInstance().getCurrentStage().getScene().getStylesheets());
        setContent(contentPane);
        getDialogPane().getButtonTypes().setAll(ButtonType.OK);
        initModality(Modality.APPLICATION_MODAL);

        getDialogPane().setStyle("""
                -fx-background-color: #202024;
                -fx-backgorund-radius: 12px;
                -fx-border-color: #323237;
                -fx-border-width: 2px;
                -fx-border-radius: 12px;
                """);
        System.out.println(getDialogPane().getStyleClass());
    }

    private VBox generateContentPane(String labelText) {
        Label label = new Label(labelText);
        VBox.setVgrow(label, Priority.ALWAYS);
        VBox contentPane = new VBox(label);
        contentPane.setBackground(Background.fill(Color.web("#202024")));
        return contentPane;
    }
}
