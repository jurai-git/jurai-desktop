package com.jurai.ui.controls;

import com.jurai.data.ApplicationData;
import com.jurai.ui.animation.interpolator.SmoothEase;
import com.jurai.util.FileUtils;
import com.jurai.util.UILogger;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

public class LoadingCircle extends StackPane {
    private final SVGPath svgCircle;
    private final RotateTransition rotateAnimation;

    public LoadingCircle() {
        svgCircle = new SVGPath();
        svgCircle.setFill(Color.web("#bbbbbf"));
        svgCircle.minWidth(ApplicationData.getDefaultIconSize() * 1.5);
        svgCircle.maxWidth(ApplicationData.getDefaultIconSize() * 3);
        svgCircle.prefWidth(ApplicationData.getDefaultIconSize() * 2);
        svgCircle.minHeight(ApplicationData.getDefaultIconSize() * 1.5);
        svgCircle.maxHeight(ApplicationData.getDefaultIconSize() * 3);
        svgCircle.prefHeight(ApplicationData.getDefaultIconSize() * 2);
        try {
            svgCircle.setContent(FileUtils.getResourceContent("/paths/loading.path"));
        } catch (Exception e) {
            UILogger.logWarning("Unable to load loading circle path (paths/loading.path). Proceeding with empty path.");
            svgCircle.setContent("");
        }
        getChildren().setAll(svgCircle);
        setAlignment(Pos.CENTER);

        rotateAnimation = new RotateTransition(Duration.millis(1000), svgCircle);
        rotateAnimation.setFromAngle(0);
        rotateAnimation.setToAngle(360);
        rotateAnimation.setCycleCount(RotateTransition.INDEFINITE);
        rotateAnimation.setInterpolator(Interpolator.LINEAR);
    }

    public void play() {
        rotateAnimation.play();
    }

    public void stop() {
        rotateAnimation.stop();
    }

}
