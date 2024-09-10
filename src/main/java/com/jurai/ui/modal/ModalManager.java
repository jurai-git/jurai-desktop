package com.jurai.ui.modal;

import com.jurai.ui.animation.interpolator.PowerEase;
import com.jurai.ui.animation.interpolator.SmoothEase;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ModalManager {
    private static StackPane root;
    private static Node content;
    private static Modal activeModal;
    private static ScaleTransition scaleInTransition;
    private static ScaleTransition scaleOutTransition;
    private static FadeTransition fadeInTransition;
    private static FadeTransition fadeOutTransition;

    public static void initialize(StackPane root, Node content) {
        ModalManager.root = root;
        ModalManager.content = content;

        scaleInTransition = new ScaleTransition(Duration.millis(350));
        scaleInTransition.setFromX(0);
        scaleInTransition.setFromY(0);
        scaleInTransition.setToX(1);
        scaleInTransition.setToY(1);
        scaleInTransition.setInterpolator(new PowerEase(3.2, true));

        scaleOutTransition = new ScaleTransition(Duration.millis(300));
        scaleOutTransition.setFromX(1);
        scaleOutTransition.setFromY(1);
        scaleOutTransition.setToX(0);
        scaleOutTransition.setToY(0);
        scaleOutTransition.setInterpolator(new SmoothEase());

        fadeInTransition = new FadeTransition(Duration.millis(350));
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);
        fadeInTransition.setInterpolator(new PowerEase(3.2, true));

        fadeOutTransition = new FadeTransition(Duration.millis(300));
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
        fadeOutTransition.setInterpolator(new SmoothEase());
    }

    public static void registerModal(Modal m) {
        root.getChildren().add(m.getContent());
        m.getContent().setScaleX(0);
    }

    public static void requestModal(Modal m) {
        if(activeModal != null) {
            activeModal.dispose();
        }
        m.getContent().maxWidthProperty().bind(root.widthProperty().multiply(0.7));
        m.getContent().maxHeightProperty().bind(root.heightProperty().multiply(0.8));
        activeModal = m;

        scaleInTransition.setNode(m.getContent());
        scaleOutTransition.setNode(m.getContent());
        scaleInTransition.playFromStart();

        fadeInTransition.setNode(m.getContent());
        fadeOutTransition.setNode(m.getContent());
        fadeInTransition.playFromStart();
        content.setEffect(new GaussianBlur(14));
    }

    public static void exitModal() {
        if(activeModal == null) return;
        scaleOutTransition.playFromStart();
        fadeOutTransition.playFromStart();
        content.setEffect(null);
    }
}
