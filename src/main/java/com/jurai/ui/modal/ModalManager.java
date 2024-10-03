package com.jurai.ui.modal;

import com.jurai.ui.LoadingStrategy;
import com.jurai.ui.animation.*;
import com.jurai.ui.animation.interpolator.PowerEase;
import com.jurai.ui.animation.interpolator.SmoothEase;
import com.jurai.util.Either;
import com.jurai.util.UILogger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModalManager {
    private static volatile ModalManager instance;
    private StackPane root;
    private Node content;
    private Modal activeModal;
    private Modal activeNotification;
    private ScaleTransition mScaleOutTransition, nScaleOutTransition;
    private FadeTransition mFadeInTransition, nFadeInTransition;
    private FadeTransition mFadeOutTransition, nFadeOutTransition;
    private Effect defaultBlur;
    private final Map<String, Either<Supplier<Modal>, Modal>> modalFactories = new HashMap<>();

    private ModalManager(StackPane root, Node content) {
        this.root = root;
        this.content = content;
        System.out.println("Initialized ModalManager with values: " + root + "; " + content);
        initializeTransitions();
    }



    public static void initialize(StackPane root, Node content) {
        if (instance == null) {
            synchronized (ModalManager.class) {
                if(instance == null) {
                    instance = new ModalManager(root, content);
                }
            }
        }
    }

    public void reinitialize(StackPane root, Node content) {
        this.root = root;
        this.content = content;
        UILogger.log("Reinitialized ModalManager");
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
        defaultBlur = new BoxBlur(16, 16, 2);

        mScaleOutTransition = createScaleTransition(new SmoothEase(1));
        mFadeInTransition = createFadeTransition(350, 0, 1, new PowerEase(3.2, true));
        mFadeOutTransition = createFadeTransition(300, 1, 0, new SmoothEase(1));
        nScaleOutTransition = createScaleTransition(new SmoothEase(1));
        nFadeInTransition = createFadeTransition(350, 0, 1, new PowerEase(3.2, true));
        nFadeOutTransition = createFadeTransition(300, 1, 0, new SmoothEase(1));
    }

    private BlurTransition createBlurTransition(Effect fromBlur, Effect toBlur, Interpolator interpolator, Node content) {
        return new BlurTransition(
                200,
                toBlur,
                fromBlur,
                interpolator,
                content
        );
    }

    private ScaleTransition createScaleTransition(Interpolator interpolator) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(300));
        transition.setFromX(1);
        transition.setFromY(1);
        transition.setToX(0);
        transition.setToY(0);
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

        mScaleOutTransition.setNode(m.getContent());
        mFadeInTransition.setNode(m.getContent());
        mFadeOutTransition.setNode(m.getContent());

        mFadeInTransition.playFromStart();
        content.setEffect(defaultBlur);
        m.getContent().setScaleX(1);
        m.getContent().setScaleY(1);
    }

    public void requestNotification(Notification notif) {
        if (activeNotification != null) {
            activeNotification.dispose();
        }

        root.getChildren().add(notif.getContent());
        notif.getContent().maxWidthProperty().bind(root.widthProperty().multiply(0.5));
        notif.getContent().maxHeightProperty().bind(root.heightProperty().multiply(0.3));
        activeNotification = notif;

        nScaleOutTransition.setNode(notif.getContent());
        nFadeInTransition.setNode(notif.getContent());
        nFadeOutTransition.setNode(notif.getContent());

        nFadeInTransition.playFromStart();

        if (activeModal != null) {
            activeModal.getContent().setEffect(defaultBlur);
        }
    }

    public void exitModal() {
        if (activeModal == null) return;
        mScaleOutTransition.setOnFinished(e -> {
            root.getChildren().remove(activeModal.getContent());
            activeModal = null;
        });
        content.setEffect(null);
        mScaleOutTransition.playFromStart();
        mFadeOutTransition.playFromStart();
        content.setEffect(null);
    }

    public void exitNotification() {
        if (activeNotification == null) return;

        if(activeModal != null) {
            activeModal.getContent().setEffect(null);
        } else {
            content.setEffect(null);
        }
        nFadeOutTransition.playFromStart();
        nScaleOutTransition.playFromStart();

    }
}