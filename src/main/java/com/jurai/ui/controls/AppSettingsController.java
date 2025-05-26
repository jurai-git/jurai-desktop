package com.jurai.ui.controls;

import com.jurai.data.ApplicationState;
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
            ApplicationState.getInstance().setUseAnimations(pane.getUseAnimations().isSelected());
            ApplicationState.getInstance().setUseLightTheme(pane.getUseLightTheme().isSelected());
            ApplicationState.getInstance().setApiUrl(pane.getApiUrl().getText());

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
        System.out.println("Is use lighttheme: " + ApplicationState.getInstance().isUseLightTheme());
        pane.getApiUrl().setText(ApplicationState.getInstance().getApiUrl());
        pane.getUseLightTheme().setSelected(ApplicationState.getInstance().isUseLightTheme());
        pane.getUseAnimations().setSelected(ApplicationState.getInstance().isUseAnimations());

        ApplicationState.getInstance().addPropertyChangeListener(l -> {
            if ("apiUrl".equals(l.getPropertyName())) {
                pane.getApiUrl().setText((String) l.getNewValue());
            } else if ("useLightTheme".equals(l.getPropertyName())) {
                pane.getUseLightTheme().setSelected((boolean) l.getNewValue());
            } else if ("useAnimations".equals(l.getPropertyName())) {
                pane.getUseAnimations().setSelected((boolean) l.getNewValue());
            }
        });
    }

    private boolean checkForChanges(AppSettingsMenu pane) {
        return ApplicationState.getInstance().isUseLightTheme() == pane.getUseLightTheme().isSelected() &&
                ApplicationState.getInstance().isUseAnimations() == pane.getUseAnimations().isSelected() &&
                Objects.equals(ApplicationState.getInstance().getApiUrl(), pane.getApiUrl().getText());
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
