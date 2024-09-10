package com.jurai.ui.modal;

import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;

public class ModalHandler {
    private static StackPane root;
    private static Node content;
    private static Modal activeModal;

    public static void initialize(StackPane root, Node content) {
        ModalHandler.root = root;
        ModalHandler.content = content;
    }

    public static void requestModal(Modal m) {
        if(activeModal != null) {
            activeModal.dispose();
        }

        m.getContent().maxWidthProperty().bind(root.widthProperty().multiply(0.7));
        m.getContent().maxHeightProperty().bind(root.heightProperty().multiply(0.8));
        activeModal = m;
        root.setEffect(new GaussianBlur());
        root.getChildren().add(m.getContent());
    }

    public static void exitModal() {
        if(activeModal == null) return;
        root.getChildren().remove(activeModal.getContent());
        activeModal = null;
    }

    public static void resetModal() {
        root.getChildren().removeAll(root.getChildren());
        root.getChildren().add(content);
    }
}
