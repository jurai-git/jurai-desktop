package com.jurai.ui.modal;

import com.jurai.ui.LoadingStrategy;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static dev.mgcvale.fluidfx.components.layout.Wrappers.wStyleClass;

@LoadingStrategy(LoadingStrategy.Strategy.EAGER)
public class ProfilePictureChangeModal extends Modal<VBox> {
    private VBox content;

    public ProfilePictureChangeModal() {
        super("profilePictureChangeModal");
    }

    @Override
    protected void initControls() {
        content = new VBox();
    }

    @Override
    protected void layControls() {
        content.getChildren().addAll(
                wStyleClass(new Label("Trocar sua foto de perfil"), "header"),
                wStyleClass(new Label("Escolha o que você "))
        );
    }

    @Override
    public VBox getContent() {
        return content;
    }
}
