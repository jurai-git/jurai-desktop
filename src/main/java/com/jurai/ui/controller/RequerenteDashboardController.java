package com.jurai.ui.controller;

import com.jurai.ui.menus.RequerenteDashboardMenu;
import com.jurai.ui.modal.ModalManager;

public class RequerenteDashboardController extends AbstractController<RequerenteDashboardMenu>  {
    @Override
    protected void attachEvents(RequerenteDashboardMenu pane) {
        pane.getAddRequerente().setOnAction(e -> {
            ModalManager.getInstance().requestModal("requerenteRegisterModal");
        });
    }

    @Override
    protected void attachNotifiers(RequerenteDashboardMenu pane) {

    }
}
