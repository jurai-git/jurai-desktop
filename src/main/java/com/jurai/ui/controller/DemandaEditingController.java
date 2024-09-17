package com.jurai.ui.controller;

import com.jurai.ui.modal.DemandaEditingModal;

public class DemandaEditingController extends AbstractController<DemandaEditingModal> {

    @Override
    protected void attachEvents(DemandaEditingModal pane) {
        pane.getCancels().forEach(button -> button.setOnAction(e -> {
            pane.dispose();
        }));
        pane.getNext().setOnAction(e -> {
            pane.getContent().setActiveTab(pane.getTab2());
        });
        pane.getPrevious().setOnAction(e -> {
            pane.getContent().setActiveTab(pane.getTab1());
        });

    }

    @Override
    protected void attachNotifiers(DemandaEditingModal pane) {

    }
}
