package com.jurai.ui.controls;

import com.jurai.ui.animation.PropertyBindPair;
import com.jurai.ui.animation.PropertyBindAnimation;
import com.jurai.ui.animation.interpolator.PowerEase;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.util.function.Consumer;

public class SidebarNavItem extends HBox {
    private boolean active;
    private Rectangle dot;
    private SVGPath icon;
    private StackPane iconContainer;
    private Label label;
    private PropertyBindAnimation dotWarpTransition;
    private FillTransition dotColorTransition;
    private FillTransition iconColorTransition;
    private Consumer<Event> onAction;

    public SidebarNavItem(SVGPath icon, String text) {
        super();
        label = new Label(text);
        this.icon = icon;
        iconContainer = new StackPane(icon);

        icon.setFill(Color.rgb(153, 153, 153));

        dot = new Rectangle(8, 8 * 1.5, Color.rgb(153, 153, 153));
        dot.arcHeightProperty().bind(dot.widthProperty());
        dot.arcWidthProperty().bind(dot.widthProperty());

        label.textFillProperty().bind(icon.fillProperty());

        initializeAnimations();
        layControls();
    }

    private void initializeAnimations() {

        dotWarpTransition = new PropertyBindAnimation(
                new PropertyBindPair<>(dot.heightProperty(), dot.widthProperty().multiply(1.5)),
                new PropertyBindPair<>(dot.heightProperty(), dot.widthProperty().multiply(3.5)),
                new PowerEase(4, true),
                500
        );

        dotColorTransition = new FillTransition(Duration.millis(500), dot);
        dotColorTransition.setInterpolator(Interpolator.EASE_BOTH);
        dotColorTransition.setFromValue(Color.web("#999999"));
        dotColorTransition.setToValue(Color.web("#539CD4"));

        iconColorTransition = new FillTransition(Duration.millis(500), icon);
        iconColorTransition.setInterpolator(new PowerEase(3, true));
        iconColorTransition.setFromValue(Color.web("#949494"));
        iconColorTransition.setToValue(Color.web("#c0c0c0"));
    }

    private void layControls() {
        dot.setManaged(false);
        dot.setX(0);
        // center dot vertically
        dot.yProperty().bind(heightProperty().multiply(0.5).subtract(dot.heightProperty().multiply(0.5)));
        // make dot translate half its width to the left, so that it aligns in the center of the edge of the container
        dot.translateXProperty().bind(dot.widthProperty().multiply(-0.5));
        getChildren().add(dot);

        HBox.setHgrow(iconContainer, Priority.NEVER);
        getChildren().add(iconContainer);

        HBox.setHgrow(label, Priority.ALWAYS);
        getChildren().add(label);
    }

    public void setIconsOnly(boolean iconsOnly) {
        try {
            if (iconsOnly)
                getChildren().remove(label);
            else
                getChildren().add(label);
        } catch (IllegalArgumentException ignored) {}
    }

    private void setupActions() {
        this.setOnMouseClicked(onAction::accept);
        label.setOnMouseClicked(onAction::accept);
        dot.setOnMouseClicked(onAction::accept);
        iconContainer.setOnMouseClicked(onAction::accept);
    }

    private void activationChanged() {
        dotWarpTransition.playFrom(!active);
        iconColorTransition.setRate(active ? 1 : -1);
        ((PowerEase) iconColorTransition.getInterpolator()).setReverse(active);
        dotColorTransition.setRate(active ? 1 : -1);
        dotColorTransition.play();
        iconColorTransition.play();
    }

    public void setOnAction(Consumer<Event> onAction) {
        if(onAction == null) return;
        this.onAction = onAction;
        setupActions();
    }

    public DoubleProperty dotWidthProperty() {
        return dot.widthProperty();
    }

    public StackPane getIconContainer() {
        return iconContainer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        if(active != this.active) {
            this.active = active;
            activationChanged();
        }
    }

    public void setDotColor(Color c) {
        dotColorTransition.setFromValue(c);
        dotColorTransition.setToValue(c);
        dotColorTransition.play();
    }

    public void setIconColor(Color c) {
        iconColorTransition.setFromValue(c);
        iconColorTransition.setToValue(c);
        iconColorTransition.play();
    }

    public void setDotVisible(boolean dotVisible) {
        dot.setVisible(dotVisible);
    }

    public boolean isDotVisible() {
        return dot.isVisible();
    }

    public SVGPath getIcon() {
        return icon;
    }

    public void setIcon(SVGPath icon) {
        this.icon = icon;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
