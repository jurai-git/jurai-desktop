package com.jurai.ui.modal.notif;

import com.jurai.ui.LoadingStrategy;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.modal.Notification;
import com.jurai.ui.util.SpacerFactory;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

@LoadingStrategy(LoadingStrategy.Strategy.LAZY)
public class DefaultMessageNotification extends Notification<VBox> {
    private Label title;
    private Label message;
    private Button okButton;
    private VBox content;

    public DefaultMessageNotification(String message, NotificationType type) {
        initControls();
        this.message.setText(message);
        this.title.setText(type.getLabel());
        content.getStyleClass().add("modal");
        content.setCache(true);
        content.setCacheHint(CacheHint.SPEED);
        content.setCacheShape(true);
        layControls();
    }

    @Override
    protected void initControls() {
        okButton = new Button("OK");
        okButton.setOnAction(e -> this.dispose());
        okButton.setStyle("-fx-padding: 8px 1.5em;");
        content = new VBox();
        content.setStyle("-fx-background-radius: 12px; -fx-border-radius: 12px");
        message = new Label();
        message.setStyle("-fx-padding: 0 0 .5em 0");
        title = new Label();
        title.setStyle("-fx-padding: .5em 0 .5em 0; -fx-font-size: 1.5em;");
    }

    @Override
    protected void layControls() {
        message.setWrapText(true);
        VBox.setVgrow(message, Priority.ALWAYS);
        message.setTextAlignment(TextAlignment.LEFT);
        content.getChildren().addAll(
                title,
                message,
                SpacerFactory.createVBoxSpacer(Priority.ALWAYS),
                new HGroup().withChildren(SpacerFactory.createHBoxSpacer(Priority.ALWAYS), okButton)
        );
    }

    @Override
    public VBox getContent() {
        return content;
    }
}
