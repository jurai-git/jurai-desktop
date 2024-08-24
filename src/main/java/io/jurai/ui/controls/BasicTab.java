package io.jurai.ui.controls;

import io.jurai.ui.animation.interpolator.PowerEase;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.function.Consumer;


public class BasicTab {
    private String name;
    private Node content;
    private StackPane tabHeader;
    private Label title;
    private Rectangle decoration;
    private boolean active;
    private FillTransition inColorTransition, outColorTransition;
    private FadeTransition inTextTransition, outTextTransition;
    private Consumer<Event> onAction;

    public BasicTab(String name, Node content) {
        this.name = name;
        this.content = content;
        initTabDisplay();
    }

    private void initTabDisplay() {
        tabHeader = new StackPane();
        title = new Label(name);
        title.setPadding(new Insets(4, 0, 4, 0));
        title.setAlignment(Pos.CENTER);

        decoration = new Rectangle(3, 24, Color.web("#539CD4"));

        decoration.widthProperty().bind(title.widthProperty().multiply(1.6));
        decoration.setHeight(3);

        decoration.setManaged(false);
        decoration.setLayoutX(0);
        decoration.layoutYProperty().bind(tabHeader.heightProperty());

        tabHeader.minWidthProperty().bind(decoration.widthProperty());
        tabHeader.minHeightProperty().bind(title.heightProperty().add(decoration.heightProperty()).add(4));
        tabHeader.getChildren().addAll(title, decoration);

        PowerEase ease = new PowerEase(3, true);
        inColorTransition = new FillTransition(Duration.millis(500), decoration);
        inColorTransition.setInterpolator(ease);
        inColorTransition.setToValue(Color.web("#539CD4"));

        outColorTransition = new FillTransition(Duration.millis(400), decoration);
        outColorTransition.setInterpolator(ease);
        outColorTransition.setToValue(Color.web("#555"));

        inTextTransition = new FadeTransition(Duration.millis(500), title);
        inTextTransition.setInterpolator(ease);
        inTextTransition.setToValue(1);

        outTextTransition = new FadeTransition(Duration.millis(400), title);
        outTextTransition.setInterpolator(ease);
        outTextTransition.setToValue(0.6);


    }

    private void setupActions() {
        tabHeader.setOnMouseClicked(onAction::accept);
    }

    public void setOnAction(Consumer<Event> onAction) {
        if(onAction == null) return;
        this.onAction = onAction;
        setupActions();
    }

    public void setActive(boolean active) {
        this.active = active;
        if(active) {
            inColorTransition.playFromStart();
            inTextTransition.playFromStart();
        } else {
            outColorTransition.playFromStart();
            outTextTransition.playFromStart();
        }
    }

    public boolean isActive() {
        return active;
    }

    public StackPane getTabHeader() {
        return tabHeader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getContent() {
        return content;
    }

    public void setContent(Node content) {
        this.content = content;
    }
}
