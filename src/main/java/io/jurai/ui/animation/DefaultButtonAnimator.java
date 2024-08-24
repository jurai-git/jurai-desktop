package io.jurai.ui.animation;

import io.jurai.ui.animation.interpolator.PowerEase;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class DefaultButtonAnimator {
    public static void animate(Button button) {

        ScaleTransition activeIn = new ScaleTransition(Duration.millis(280), button);
        ScaleTransition activeOut = new ScaleTransition(Duration.millis(280), button);
        PowerEase activeEase = new PowerEase(4, true);
        activeIn.setInterpolator(activeEase);
        activeOut.setInterpolator(activeEase);
        activeIn.setToX(1);
        activeIn.setToY(1);
        activeOut.setToX(1.08);
        activeOut.setToY(1.08);

        ScaleTransition hoverIn = new ScaleTransition(Duration.millis(280), button);
        ScaleTransition hoverOut = new ScaleTransition(Duration.millis(280), button);
        PowerEase hoverEase = new PowerEase(3, true);
        hoverIn.setInterpolator(hoverEase);
        hoverOut.setInterpolator(hoverEase);
        hoverIn.setToX(1.08);
        hoverIn.setToY(1.08);
        hoverOut.setToX(1);
        hoverOut.setToY(1);

        button.setOnMouseEntered(e -> {
            hoverIn.playFromStart();
        });
        button.setOnMouseExited(e -> {
            hoverOut.playFromStart();
        });
        button.setOnMousePressed(e -> {
            activeIn.playFromStart();
        });
        button.setOnMouseReleased(e -> {
            activeOut.playFromStart();
        });
    }
}
