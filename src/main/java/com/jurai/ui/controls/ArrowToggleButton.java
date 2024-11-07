package com.jurai.ui.controls;

import com.jurai.ui.animation.interpolator.PowerEase;
import com.jurai.util.FileUtils;
import com.jurai.util.UILogger;
import javafx.animation.FillTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.io.IOException;
import java.util.function.Consumer;

public class ArrowToggleButton extends StackPane {
    private SVGPath arrowPath;
    private StackPane arrowContainer;
    private BooleanProperty active = new SimpleBooleanProperty(false);
    private Rectangle base;
    private Consumer<Boolean> onAction;

    private RotateTransition arrowRotateTransition;
    private ScaleTransition hoverInScaleTransition, hoverOutScaleTransition, clickInScaleTransition, clickOutScaleTransition;

    public ArrowToggleButton() {
        initComponents();
        layComponents();
        initTransitions();
        deployActions();
    }

    private void initComponents() {
        initSvg();
        arrowContainer = new StackPane(arrowPath);
        base = new Rectangle();
        getStyleClass().add("arrow-toggle-button");
    }

    private void layComponents() {
        base.setManaged(false);
        base.widthProperty().bind(widthProperty());
        base.heightProperty().bind(heightProperty());
        base.arcHeightProperty().bind(base.heightProperty().multiply(0.5));
        base.arcWidthProperty().bind(base.arcHeightProperty());
        base.getStyleClass().add("base");
        base.setLayoutX(0);
        base.setLayoutY(0);

        arrowPath.setScaleX(0.8);
        arrowPath.setScaleY(0.8);

        getChildren().addAll(base, arrowContainer);
    }

    private void initTransitions() {
        PowerEase rotateInterpolator = new PowerEase(3, true);
        PowerEase scaleInterpolator = new PowerEase(2.5, true);

        arrowRotateTransition = new RotateTransition(Duration.millis(350), arrowPath);
        arrowRotateTransition.setInterpolator(rotateInterpolator);
        arrowRotateTransition.setToAngle(270);

        hoverInScaleTransition = new ScaleTransition(Duration.millis(300), this);
        hoverInScaleTransition.setInterpolator(scaleInterpolator);
        hoverInScaleTransition.setToX(1.07);
        hoverInScaleTransition.setToY(1.07);

        hoverOutScaleTransition = new ScaleTransition(Duration.millis(300), this);
        hoverOutScaleTransition.setInterpolator(scaleInterpolator);
        hoverOutScaleTransition.setToX(1);
        hoverOutScaleTransition.setToY(1);

        clickInScaleTransition = new ScaleTransition(Duration.millis(240), this);
        clickInScaleTransition.setInterpolator(scaleInterpolator);
        clickInScaleTransition.setToX(0.98);
        clickInScaleTransition.setToY(0.98);

        clickOutScaleTransition = new ScaleTransition(Duration.millis(300), this);
        clickOutScaleTransition.setInterpolator(scaleInterpolator);
        clickOutScaleTransition.setToX(1.07);
        clickOutScaleTransition.setToY(1.07);
    }

    private void deployActions() {
        setOnMouseClicked(e -> {
            active.set(!active.get());
            updateOrientation();
            onAction.accept(active.get());
        });

        setOnMouseEntered(e -> {
            hoverInScaleTransition.playFromStart();
        });
        setOnMouseExited(e -> {
            hoverOutScaleTransition.playFromStart();
        });
        setOnMousePressed(e -> clickInScaleTransition.playFromStart());
        setOnMouseReleased(e -> clickOutScaleTransition.playFromStart());
    }

    public void setOnAction(Consumer<Boolean> onAction) {
        this.onAction = onAction;
    }

    public void addActiveListener(ChangeListener<? super Boolean> e) {
        active.addListener(e);
    }

    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean active) {
        this.active.set(active);
        updateOrientation();
    }

    public void updateOrientation() {
        if(active.get()) {
            arrowRotateTransition.setToAngle(270);
            arrowRotateTransition.playFromStart();
        } else {
            arrowRotateTransition.setToAngle(90);
            arrowRotateTransition.playFromStart();
        }
    }

    private void initSvg() {
        try {
            arrowPath = new SVGPath();
            arrowPath.setContent(FileUtils.getResourceContent("/paths/arrow.path"));
            arrowPath.setRotate(90);
            arrowPath.getStyleClass().add("arrow");
        } catch(IOException e) {
            e.printStackTrace();
            UILogger.logError("unable to load sidebar icon paths");
            UILogger.logWarning("Proceding without sidebar icons");
        }
    }

}
