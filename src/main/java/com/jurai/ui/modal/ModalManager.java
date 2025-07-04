package com.jurai.ui.modal;

import com.jurai.ui.LoadingStrategy;
import com.jurai.ui.animation.interpolator.PowerEase;
import com.jurai.ui.animation.interpolator.SmoothEase;
import com.jurai.ui.panes.layout.ProportionPane;
import com.jurai.util.Either;
import com.jurai.util.UILogger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/*
 * n stands for notification and m stands for modal in the field names
 */

public class ModalManager {
    private static volatile ModalManager instance;
    private StackPane root;
    private Node content;
    private Modal activeModal;
    private Notification activeNotification;
    private FadeTransition nFadeInTransition, mFadeInTransition;
    private FadeTransition mFadeOutTransition, nFadeOutTransition, mBlurFadeInTransition, mBlurFadeOutTransition, nBlurFadeInTransition, nBlurFadeOutTransition;
    private Effect defaultBlur;
    private ChangeListener<Number> rootWidthListener, rootHeightListener;
    private Runnable rootDimensionsChangedAction;
    private final Map<String, Either<Supplier<Modal>, Modal>> modalFactories = new HashMap<>();
    private boolean isExitingNotification = false;
    private boolean isExitingModal = false;

    private ImageView mBlurredBackground, nBlurredBackground;

    private ModalManager(StackPane root, Node content) {
        this.root = root;
        this.content = content;
        System.out.println("Initialized ModalManager with values: " + root + "; " + content);
        rootDimensionsChangedAction = () -> {};
        rootWidthListener = (a, b, c) -> rootDimensionsChangedAction.run();
        rootHeightListener = (a, b, c) -> rootDimensionsChangedAction.run();
        if (root != null) {
            root.widthProperty().addListener(rootWidthListener);
        }
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
        if (root != null) {
            root.widthProperty().addListener(rootWidthListener);
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
        defaultBlur = new BoxBlur(16, 16, 2);

        mFadeOutTransition = createFadeTransition(300, 1, 0, new SmoothEase(1));
        nFadeInTransition = createFadeTransition(350, 0, 1, new PowerEase(3.2, true));
        nFadeOutTransition = createFadeTransition(300, 1, 0, new SmoothEase(1));

        mBlurFadeInTransition = createFadeTransition(300, 0, 1, new PowerEase(2, true));
        mBlurFadeOutTransition = createFadeTransition(300, 1, 0, new PowerEase(2, true));
        nBlurFadeInTransition = createFadeTransition(300, 0, 1, new PowerEase(2, true));
        nBlurFadeOutTransition = createFadeTransition(300, 1, 0, new PowerEase(2, true));

        mFadeInTransition = createFadeTransition(300, 0, 1, new SmoothEase(1));
    }

    private FadeTransition createFadeTransition(int duration, double fromValue, double toValue, Interpolator interpolator) {
        FadeTransition transition = new FadeTransition(Duration.millis(duration));
        transition.setFromValue(fromValue);
        transition.setToValue(toValue);
        transition.setInterpolator(interpolator);
        return transition;
    }

    private Image createBlurredSnapshot(Node content) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image snapshot = content.snapshot(params, null);
        ImageView imageView = new ImageView(snapshot);
        imageView.setEffect(new BoxBlur(16, 16, 2));
        return imageView.snapshot(params, null);
    }

    public void requestModal(String key) {
        if (activeModal != null) {

            // stop transitions to avoid overlaps
            mFadeOutTransition.stop();
            mBlurFadeOutTransition.stop();

            // here we will exit instantly to avoid errors due to transition timing
            removeModalNodes();
            activeNotification = null;
            isExitingNotification = false;
        }


        Modal m = modalFactories.get(key).ifLPresentOrElse(Supplier::get, modal -> modal);

        Image blurredImage = createBlurredSnapshot(content);
        mBlurredBackground = new ImageView(blurredImage);
        root.getChildren().add(mBlurredBackground);
        mBlurFadeInTransition.setNode(mBlurredBackground);
        mBlurFadeOutTransition.setNode(mBlurredBackground);

        root.getChildren().add(m.getContent());
        m.getContent().maxWidthProperty().bind(root.widthProperty().multiply(0.7));
        m.getContent().maxHeightProperty().bind(root.heightProperty().multiply(0.8));
        activeModal = m;

        mFadeOutTransition.setNode(m.getContent());
        mFadeInTransition.setNode(m.getContent());

        mFadeInTransition.playFromStart();
        mBlurFadeInTransition.playFromStart();
        m.getContent().setScaleX(1);
        m.getContent().setScaleY(1);
        m.getContent().toFront();
        m.getContent().setVisible(true);
    }

    public void exitModal() {
        if (activeModal == null) return;
        if (isExitingModal) return;
        isExitingModal = true;

        mBlurFadeOutTransition.playFromStart();
        mFadeOutTransition.playFromStart();
        mFadeOutTransition.setOnFinished(e -> {
            if (isExitingModal) {
                removeModalNodes();
                isExitingModal = false;
                activeModal = null;
            }
        });
    }

    public void requestNotification(Notification notif) {
        if (activeNotification != null) {

            // stop transitions to avoid overlaps
            nFadeOutTransition.stop();
            nFadeInTransition.stop();
            nBlurFadeOutTransition.stop();
            nBlurFadeInTransition.stop();

            // here we will exit instantly to avoid errors due to transition timing
            if (!isExitingNotification) {
                if(activeModal != null) {
                    activeModal.getContent().setEffect(null);
                } else {
                    content.setEffect(null);
                }
            }

            removeNotificationNodes();
            activeNotification = null;
            isExitingNotification = false;
        }

        Image blurredImage = createBlurredSnapshot(root);
        nBlurredBackground = new ImageView(blurredImage);
        nBlurredBackground.setOpacity(0);
        nBlurredBackground.setOnMouseClicked(e -> exitNotification());
        root.getChildren().add(nBlurredBackground);
        nBlurFadeInTransition.setNode(nBlurredBackground);
        nBlurFadeOutTransition.setNode(nBlurredBackground);

        notif.getContent().setOpacity(0);
        root.getChildren().add(notif.getContent());

        notif.getContent().applyCss();
        notif.getContent().layout();
        rootDimensionsChangedAction = () -> {
            notif.getContent().setMaxWidth(Math.min(notif.getContent().prefWidth(-1), root.getWidth() * 0.7));
            notif.getContent().setMaxHeight(notif.getContent().prefHeight(notif.getContent().getMaxWidth()));
        };
        rootDimensionsChangedAction.run();
        activeNotification = notif;

        nFadeInTransition.setNode(notif.getContent());
        nFadeOutTransition.setNode(notif.getContent());

        nBlurFadeInTransition.playFromStart();
        nFadeInTransition.playFromStart();

        if (activeModal != null) {
            activeModal.getContent().setEffect(defaultBlur);
        }
    }

    public void exitNotification() {
        if (activeNotification == null) return;

        if (isExitingNotification) return;
        isExitingNotification = true;

        if(activeModal != null) {
            activeModal.getContent().setEffect(null);
        } else {
            content.setEffect(null);
        }

        nFadeOutTransition.playFromStart();
        nBlurFadeOutTransition.playFromStart();
        nFadeOutTransition.setOnFinished(actionEvent -> {
            if (isExitingNotification) {
                // we will only remove the notification if no one removed it while the transition was playing, to avoid concurrent modifications
                removeNotificationNodes();
                isExitingNotification = false;
                activeNotification = null;
            }
        });
    }

    private void removeNotificationNodes() {
        root.getChildren().removeAll(nBlurredBackground, activeNotification != null ? activeNotification.getContent() : null);
    }

    private void removeModalNodes() {
        root.getChildren().removeAll(mBlurredBackground, activeModal != null ? activeModal.getContent() : null);
    }
}