package com.jurai.ui.controls;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Model;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.animation.interpolator.PowerEase;
import javafx.animation.FillTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import lombok.Getter;

public class SimpleListItem<T extends Model> extends HBox {
    private Label nameLabel;
    private Rectangle dot;
    private boolean selected;
    private FillTransition dotColorTransition, textFillTransition;

    @Getter
    T object;

    public SimpleListItem(T object) {
        this.object = object;
        selected = false;
        initControls();
        layControls();
        initAnimations();
        attachNotifiers();
    }

    private void initControls() {
        getStyleClass().add("list-item");
        nameLabel = new Label(object.nomeProperty().get());
        nameLabel.textProperty().bind(object.nomeProperty());
        dot = new Rectangle();
        dot.getStyleClass().add("dot");
        dot.setFill(Color.web("#666"));
        dot.arcWidthProperty().bind(dot.widthProperty());
        dot.arcHeightProperty().bind(dot.widthProperty());
        dot.translateYProperty().bind(nameLabel.heightProperty().multiply(0.2));
        dot.heightProperty().bind(nameLabel.heightProperty().multiply(0.6));
        dot.widthProperty().bind(dot.heightProperty().multiply(0.2));
    }

    private void layControls() {
        VBox.setVgrow(this, Priority.NEVER);
        getChildren().addAll(dot, nameLabel);
    }

    private void initAnimations() {
        PowerEase interpolator = new PowerEase(2.5, true);
        dotColorTransition = new FillTransition(Duration.millis(280), dot);
        dotColorTransition.setFromValue(Color.web("#666"));
        dotColorTransition.setToValue(Color.web("#539CD4"));
        dotColorTransition.setInterpolator(interpolator);

        Shape textFillProperty = new Rectangle();
        textFillProperty.setFill(Color.web("#888"));
        nameLabel.textFillProperty().bind(textFillProperty.fillProperty());
        textFillTransition = new FillTransition(Duration.millis(280), textFillProperty);
        textFillTransition.setFromValue(Color.web("#888"));
        textFillTransition.setInterpolator(interpolator);

        if (ApplicationState.get().isUseLightTheme()) {
            textFillTransition.setToValue(Color.web("#0f0f0f"));
        } else {
            textFillTransition.setToValue(Color.web("#fcfcfc"));
        }

        HoverAnimator.animateAll(this, 0.3, 0.5);
    }

    private void attachNotifiers() {
        ApplicationState.get().addPropertyChangeListener(change -> {
            if ("useLightTheme".equals(change.getPropertyName())) {
                themeChanged((boolean) change.getNewValue());
            }
        });
    }

    public void themeChanged(boolean isLight) {
        if (isLight) {
            System.out.println("Loading light theme");
            textFillTransition.setToValue(Color.web("0f0f0f"));
            if (selected) {
                textFillTransition.playFromStart();
            }
        } else {
            textFillTransition.setToValue(Color.web("#fcfcfc"));
            System.out.println("Loading dark theme");
            if (selected) {
                textFillTransition.playFromStart();
            }
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            dotColorTransition.setRate(1);
            dotColorTransition.play();
            textFillTransition.setRate(1);
            textFillTransition.play();
            System.out.println("SimpleListItem of object " + object + "Was selected!");
        } else {
            dotColorTransition.setRate(-2.5);
            dotColorTransition.play();
            textFillTransition.setRate(-2);
            textFillTransition.play();
        }
    }

    public String getName() {
        return nameLabel.getText();
    }

}
