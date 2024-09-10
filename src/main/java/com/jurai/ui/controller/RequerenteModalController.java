package com.jurai.ui.controller;

import com.jurai.ui.modal.RequerenteRegisterModal;

public class RequerenteModalController extends AbstractController<RequerenteRegisterModal> {
    @Override
    protected void attachEvents(RequerenteRegisterModal pane) {
        pane.getPersonalInfoNext().setOnAction(e -> {
            pane.setActiveTab(pane.getGeneralInfo());
        });
        pane.getGeneralInfoPrevious().setOnAction(e -> {
            pane.setActiveTab(pane.getPersonalInfo());
        });
        pane.getCancel().setOnAction(e -> {
            pane.dispose();
        });

    }

    @Override
    protected void attachNotifiers(RequerenteRegisterModal pane) {

    }
}
