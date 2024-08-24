package com.jurai.ui.menus.controller;

import com.jurai.data.ApplicationState;
import com.jurai.ui.controller.AbstractController;
import com.jurai.ui.menus.RequerenteInfoMenu;
import javafx.scene.control.Label;

public class RequerenteInfoMenuController extends AbstractController<RequerenteInfoMenu> {

    @Override
    protected void attachEvents(RequerenteInfoMenu pane) {

    }

    @Override
    protected void attachNotifiers(RequerenteInfoMenu pane) {
        ApplicationState.addPropertyChangeListener(e -> {
            if("selectedRqeurente".equals(e.getPropertyName())) {

            }
        });
    }

    private void requerenteUpdated(RequerenteInfoMenu pane) {
        pane.getContent().getChildren().removeAll();

        if(ApplicationState.getSelectedRequerente() == null) {
            pane.getContent().getChildren().add(new Label("Nenhum requerente selecionado"));
            return;
        }

        // code to fill up info with requrente

    }
}
