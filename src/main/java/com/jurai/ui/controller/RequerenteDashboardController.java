package com.jurai.ui.controller;

import com.jurai.ui.menus.RequerenteDashboardMenu;

public class RequerenteDashboardController extends AbstractController<RequerenteDashboardMenu>  {
    @Override
    protected void attachEvents(RequerenteDashboardMenu pane) {
        pane.getAddRequerente().setOnAction(e -> {
            pane.getRequerenteRegisterModal().show();
        });
    }

    @Override
    protected void attachNotifiers(RequerenteDashboardMenu pane) {

    }
}
