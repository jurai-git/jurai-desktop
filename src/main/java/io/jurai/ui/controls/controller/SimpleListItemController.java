package io.jurai.ui.controls.controller;

import io.jurai.data.ApplicationState;
import io.jurai.ui.controller.AbstractController;
import io.jurai.ui.controls.SimpleListItem;

public class SimpleListItemController extends AbstractController<SimpleListItem<?>> {


    @Override
    protected void attachEvents(SimpleListItem<?> pane) {
        pane.setOnMouseClicked(e -> {
            ApplicationState.setSelectedRequerente(pane);
        });
    }

    @Override
    protected void attachNotifiers(SimpleListItem<?> pane) {
        ApplicationState.addPropertyChangeListener(e -> {
            if("selectedRequerente".equals(e.getPropertyName())) {
                if(ApplicationState.getSelectedRequerente().equals(pane)) {
                    pane.setSelected(true);
                } else {
                    pane.setSelected(false);
                }
            }
        });
    }
}
