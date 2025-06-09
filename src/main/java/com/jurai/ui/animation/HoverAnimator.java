package com.jurai.ui.animation;

import com.jurai.data.AppState;
import com.jurai.ui.animation.interpolator.PowerEase;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class HoverAnimator {
    private static boolean enabled = true;
    private static boolean initialized = false;

    public static void disable() {
        enabled = false;
    }

    public static void enable() {
        enabled = true;
    }

    public static void initialize() {
        if (initialized) return;
        initialized = true;
        AppState.get().useAnimationsProperty().addListener((obs, o, n) -> {
            if (n) enable(); else disable();
        });

        if (!AppState.get().isUseAnimations()) {
            disable();
        }
    }

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
            if (enabled) hoverIn.playFromStart();
        });
        node.setOnMouseExited(e -> {
            if (enabled) hoverOut.playFromStart();
        });
    }

    public static void animateHover(double strengthX, double strengthY, Node... es) {
        for (var btn : es) {
            animateHover(btn, strengthX, strengthY);
        }
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
            if (enabled) activeIn.playFromStart();
        });
        node.setOnMouseReleased(e -> {
            if (enabled) activeOut.playFromStart();
        });

        // animateHover already adds it into the set, so we don't need to do that
    }

    public static void animateAll(double strengthX, double strengthY, Node... es) {
        for(var node : es) {
            animateAll(node, strengthX, strengthY);
        }
    }
}
