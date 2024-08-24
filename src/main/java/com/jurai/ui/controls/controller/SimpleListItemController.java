package com.jurai.ui.controls.controller;

import com.jurai.data.ApplicationState;
import com.jurai.ui.controller.AbstractController;
import com.jurai.ui.controls.SimpleListItem;

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
