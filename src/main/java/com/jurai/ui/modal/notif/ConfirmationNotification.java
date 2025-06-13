package com.jurai.ui.modal.notif;

import com.jurai.ui.LoadingStrategy;
import com.jurai.ui.modal.Notification;
import com.jurai.ui.util.SpacerFactory;
import dev.mgcvale.fluidfx.components.groups.HGroup;
import javafx.event.ActionEvent;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.function.Consumer;
import java.util.function.Function;

@LoadingStrategy(LoadingStrategy.Strategy.LAZY)
public class ConfirmationNotification<T> extends Notification<VBox> {
    private Label title;
    private Label message;
    private Button yesButton, noButton;
    private VBox content;
    private Consumer<T> afterDispose;

    public ConfirmationNotification(String message, NotificationType type) {
        initControls();
        this.message.setText(message);
        this.title.setText(type.getLabel());
        content.getStyleClass().add("modal");
        content.setCache(true);
        content.setCacheHint(CacheHint.SPEED);
        content.setCacheShape(true);
        afterDispose = (t) -> {};
        layControls();
    }

    @Override
    protected void initControls() {
        yesButton = new Button("Sim");
        yesButton.setOnAction(e -> this.dispose());
        yesButton.setStyle("-fx-padding: 8px 1.5em;");
        yesButton.getStyleClass().addAll("button-blue");

        noButton = new Button("Não");
        noButton.setOnAction(e -> this.dispose());
        yesButton.setStyle("-fx-padding: 8px 1.5em");

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
                SpacerFactory.vSpacer(Priority.ALWAYS),
                new HGroup().wChildren(SpacerFactory.hSpacer(Priority.ALWAYS), noButton, SpacerFactory.hSpacer(12), yesButton)
        );
    }

    public ConfirmationNotification<T> setOnYes(Function<ActionEvent, T> handler) {
        yesButton.setOnAction(e -> {
            T ret = handler.apply(e);
            dispose();
            this.afterDispose.accept(ret);
        });
        return this;
    }

    public ConfirmationNotification<T> setOnNo(Function<ActionEvent, T> handler) {
        yesButton.setOnAction(e -> {
            T ret = handler.apply(e);
            dispose();
            this.afterDispose.accept(ret);
        });
        return this;
    }

    public ConfirmationNotification<T> setAfterDispose(Consumer<T> afterDispose) {
        this.afterDispose = afterDispose;
        return this;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public VBox getContent() {
        return content;
    }
}
