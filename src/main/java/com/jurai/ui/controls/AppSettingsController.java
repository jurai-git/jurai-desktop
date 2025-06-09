package com.jurai.ui.controls;

import com.jurai.App;
import com.jurai.data.AppState;
import com.jurai.ui.controller.AbstractController;
import com.jurai.ui.menus.AppSettingsMenu;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Objects;

public class AppSettingsController extends AbstractController<AppSettingsMenu> {
    private PauseTransition currentSuccessTimeout;

    @Override
    protected void attachEvents(AppSettingsMenu pane) {
        pane.getSaveChanges().setOnAction(e -> {
            AppState.get().setUseAnimations(pane.getUseAnimations().isSelected());
            AppState.get().setUseLightTheme(pane.getUseLightTheme().isSelected());
            AppState.get().setApiUrl(pane.getApiUrl().getText());

            pane.getSaveChanges().setDisable(true);

            showTemporarySuccessMessage(pane.getSuccessLabel(), "Configurações atualizadas com sucesso!", 4000);
        });

        pane.getUseAnimations().setOnMouseClicked(e -> {
            pane.getSaveChanges().setDisable(checkForChanges(pane));
        });

        pane.getUseLightTheme().setOnMouseClicked(e -> {
            pane.getSaveChanges().setDisable(checkForChanges(pane));
        });

        pane.getApiUrl().setOnKeyTyped(e -> {
            pane.getSaveChanges().setDisable(checkForChanges(pane));
        });
    }

    @Override
    protected void attachNotifiers(AppSettingsMenu pane) {
        System.out.println("Is use lighttheme: " + AppState.get().isUseLightTheme());
        pane.getApiUrl().setText(AppState.get().getApiUrl());
        pane.getUseLightTheme().setSelected(AppState.get().isUseLightTheme());
        pane.getUseAnimations().setSelected(AppState.get().isUseAnimations());

        AppState.get().apiUrlProperty().addListener((obs, o, n) -> pane.getApiUrl().setText(n));
        AppState.get().useLightThemeProperty().addListener((obs, o, n) -> pane.getUseLightTheme().setSelected(n));
        AppState.get().useAnimationsProperty().addListener((obs, o, n) -> pane.getUseAnimations().setSelected(n));
    }

    private boolean checkForChanges(AppSettingsMenu pane) {
        return AppState.get().isUseLightTheme() == pane.getUseLightTheme().isSelected() &&
                AppState.get().isUseAnimations() == pane.getUseAnimations().isSelected() &&
                Objects.equals(AppState.get().getApiUrl(), pane.getApiUrl().getText());
    }

    private void showTemporarySuccessMessage(Label successLabel, String message, double durationMillis) {
        successLabel.setText(message);
        if (currentSuccessTimeout != null) {
            currentSuccessTimeout.stop();
        }
        currentSuccessTimeout = new PauseTransition(Duration.millis(durationMillis));
        currentSuccessTimeout.setOnFinished(e -> successLabel.setText(""));
        currentSuccessTimeout.playFromStart();
    }

}
