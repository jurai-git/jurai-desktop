package com.jurai.ui.modal.notif;

import com.jurai.ui.LoadingStrategy;
import com.jurai.ui.controls.LoadingCircle;
import com.jurai.ui.modal.Modal;
import com.jurai.ui.modal.Notification;
import javafx.scene.layout.StackPane;

@LoadingStrategy(LoadingStrategy.Strategy.EAGER)
public class LoadingModal extends Notification<StackPane> {
    private LoadingCircle loadingCircle;

    @Override
    protected void initControls() {
        loadingCircle = new LoadingCircle();
        this.isTransparent = true;
    }

    @Override
    protected void layControls() {
    }

    @Override
    public StackPane getContent() {
        return loadingCircle;
    }

    public void play() {
        loadingCircle.play();
    }

    public void stop() {
        loadingCircle.stop();
    }
}
