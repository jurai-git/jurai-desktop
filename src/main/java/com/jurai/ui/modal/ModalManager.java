package com.jurai.ui.modal;

import com.jurai.ui.animation.interpolator.PowerEase;
import com.jurai.ui.animation.interpolator.SmoothEase;
import com.jurai.util.Either;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModalManager {
    private static ModalManager instance;
    private StackPane root;
    private Node content;
    private Modal activeModal;
    private ScaleTransition scaleInTransition;
    private ScaleTransition scaleOutTransition;
    private FadeTransition fadeInTransition;
    private FadeTransition fadeOutTransition;
    private Map<String, Either<Supplier<Modal>, Modal>> modalFactories = new HashMap<>();

    private ModalManager(StackPane root, Node content) {
        this.root = root;
        this.content = content;
        initializeTransitions();
    }

    public static void initialize(StackPane root, Node content) {
        if (instance == null) {
            instance = new ModalManager(root, content);
        }
    }

    public static ModalManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ModalManager is not initialized. Call initialize() first.");
        }
        return instance;
    }

    public <T extends Modal<?>> void registerModalFactory(String key, Supplier<Modal> modalFactory, Class<T> classOfT) {

        if(classOfT.getAnnotation(LoadingStrategy.class).value() == LoadingStrategy.Strategy.EAGER) {
            // if the loading strategy is eager, we store an instance of the object directly
            modalFactories.put(key, Either.right(modalFactory.get()));
        } else {
            // if it is lazy, we just store the factory
            modalFactories.put(key, Either.left(modalFactory));
        }
    }

    private void initializeTransitions() {
        scaleInTransition = createScaleTransition(350, 0, 0, 1, 1, new PowerEase(3.2, true));
        scaleOutTransition = createScaleTransition(300, 1, 1, 0, 0, new SmoothEase());
        fadeInTransition = createFadeTransition(350, 0, 1, new PowerEase(3.2, true));
        fadeOutTransition = createFadeTransition(300, 1, 0, new SmoothEase());
    }

    private ScaleTransition createScaleTransition(int duration, double fromX, double fromY, double toX, double toY, Interpolator interpolator) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(duration));
        transition.setFromX(fromX);
        transition.setFromY(fromY);
        transition.setToX(toX);
        transition.setToY(toY);
        transition.setInterpolator(interpolator);
        return transition;
    }

    private FadeTransition createFadeTransition(int duration, double fromValue, double toValue, Interpolator interpolator) {
        FadeTransition transition = new FadeTransition(Duration.millis(duration));
        transition.setFromValue(fromValue);
        transition.setToValue(toValue);
        transition.setInterpolator(interpolator);
        return transition;
    }

    public void requestModal(String key) {
        if (activeModal != null) {
            activeModal.dispose();
        }
        Modal m = modalFactories.get(key).ifLPresentOrElse(Supplier::get, modal -> modal);
        root.getChildren().add(m.getContent());
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

    public void exitModal() {
        if (activeModal == null) return;
        scaleOutTransition.setOnFinished(e -> {
            root.getChildren().remove(activeModal.getContent());
            activeModal = null;
        });
        scaleOutTransition.playFromStart();
        fadeOutTransition.playFromStart();
        content.setEffect(null);
    }
}