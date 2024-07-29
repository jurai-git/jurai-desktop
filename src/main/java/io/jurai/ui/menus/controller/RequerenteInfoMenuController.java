package io.jurai.ui.menus.controller;

import io.jurai.data.ApplicationState;
import io.jurai.ui.controller.AbstractController;
import io.jurai.ui.menus.RequerenteInfoMenu;
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
