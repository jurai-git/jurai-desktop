package com.jurai.ui.controls;

import com.jurai.data.AppState;
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
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public class SidebarNavItem extends HBox {
    private boolean active;
    private Rectangle dot;
    @Getter
    private SVGPath icon;
    @Getter
    private StackPane iconContainer;
    @Setter
    @Getter
    private Label label;
    private PropertyBindAnimation dotWarpTransition;
    private FillTransition dotColorTransition;
    @Getter
    private FillTransition iconColorTransition;
    private Consumer<Event> onAction;
    private boolean isIconColorPermanent;

    public SidebarNavItem(SVGPath icon, String text) {
        super();
        label = new Label(text);
        this.icon = icon;
        this.isIconColorPermanent = false;
        iconContainer = new StackPane(icon);

        if (AppState.get().isUseLightTheme()) {
            icon.setFill(Color.web("#848484"));
        } else {
            icon.setFill(Color.web("#c0c0c0"));
        }

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
        dotColorTransition.setToValue(Color.web("#539CD4"));

        iconColorTransition = new FillTransition(Duration.millis(500), icon);
        iconColorTransition.setInterpolator(new PowerEase(3, true));

        if (AppState.get().isUseLightTheme()) {
            iconColorTransition.setFromValue(Color.web("#848484"));
            iconColorTransition.setToValue(Color.web("#444444"));

            dotColorTransition.setFromValue(Color.web("#555555"));
        } else {
            iconColorTransition.setFromValue(Color.web("#949494"));
            iconColorTransition.setToValue(Color.web("#c0c0c0"));

            dotColorTransition.setFromValue(Color.web("#999999"));
        }
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
        this.isIconColorPermanent = true;
    }

    public void themeChanged() {
        if (isIconColorPermanent) return; // we can't change this icon's color if  it is permanent (the user set it)
        if (AppState.get().isUseLightTheme()) {
            iconColorTransition.setFromValue(Color.web("#848484"));
            iconColorTransition.setToValue(Color.web("#444444"));
            dotColorTransition.setFromValue(Color.web("#555555"));
            if (active) {
                icon.setFill(Color.web("#333333"));
            } else {
                icon.setFill(Color.web("#848484"));
                dot.setFill(Color.web("#555555"));
            }
        } else {
            iconColorTransition.setFromValue(Color.web("#949494"));
            iconColorTransition.setToValue(Color.web("#c0c0c0"));
            dotColorTransition.setFromValue(Color.web("#999999"));
            if (active) {
                icon.setFill(Color.web("#c0c0c0"));
            } else {
                icon.setFill(Color.web("#949494"));
                dot.setFill(Color.web("#999999"));
            }
        }
    }

    public void setDotVisible(boolean dotVisible) {
        dot.setVisible(dotVisible);
    }

    public boolean isDotVisible() {
        return dot.isVisible();
    }

}
