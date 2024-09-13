package com.jurai.ui.animation;

import com.jurai.ui.animation.interpolator.PowerEase;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class HoverAnimator {

    public static void animateHover(Node node, double strengthX, double strengthY) {
        ScaleTransition hoverIn = new ScaleTransition(Duration.millis(300), node);
        ScaleTransition hoverOut = new ScaleTransition(Duration.millis(300), node);
        PowerEase hoverEase = new PowerEase(3.2, true);
        hoverIn.setInterpolator(hoverEase);
        hoverOut.setInterpolator(hoverEase);
        hoverIn.setToX(1 + 0.05 * strengthX);
        hoverIn.setToY(1 + 0.05* strengthY);
        hoverOut.setToX(1);
        hoverOut.setToY(1);

        node.setOnMouseEntered(e -> {
            hoverIn.playFromStart();
        });
        node.setOnMouseExited(e -> {
            hoverOut.playFromStart();
        });
    }

    public static void animateAll(Node node, double strengthX, double strengthY) {
        animateHover(node, strengthX, strengthY);

        ScaleTransition activeIn = new ScaleTransition(Duration.millis(250), node);
        ScaleTransition activeOut = new ScaleTransition(Duration.millis(250), node);
        PowerEase activeEase = new PowerEase(5, true);
        activeIn.setInterpolator(activeEase);
        activeOut.setInterpolator(activeEase);
        activeIn.setToX(1);
        activeIn.setToY(1);
        activeOut.setToX(1. + 0.05 * strengthX);
        activeOut.setToY(1 + 0.05 * strengthY);

        node.setOnMousePressed(e -> {
            activeIn.playFromStart();
        });
        node.setOnMouseReleased(e -> {
            activeOut.playFromStart();
        });
    }
}
