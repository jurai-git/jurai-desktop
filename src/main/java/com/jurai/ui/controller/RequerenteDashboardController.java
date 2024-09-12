package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Requerente;
import com.jurai.ui.menus.RequerenteDashboardMenu;
import com.jurai.ui.modal.ModalManager;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;

public class RequerenteDashboardController extends AbstractController<RequerenteDashboardMenu>  {
    private ListChangeListener requerenteListListener;

    @Override
    protected void attachEvents(RequerenteDashboardMenu pane) {
        pane.getAddRequerente().setOnAction(e -> {
            ModalManager.getInstance().requestModal("requerenteRegisterModal");
        });
    }

    @Override
    protected void attachNotifiers(RequerenteDashboardMenu pane) {
        ApplicationState.addPropertyChangeListener(propertyChangeEvent -> {
            if("currentUser".equals(propertyChangeEvent.getPropertyName())) {
                if(ApplicationState.getCurrentUser() != null) {
                    bindRequerenteList(pane);
                }
            }
        });
    }

    private void bindRequerenteList(RequerenteDashboardMenu pane) {
        Bindings.bindContent(pane.getRequerentesList().getListObjects(), ApplicationState.getCurrentUser().getRequerentes());
    }
}
