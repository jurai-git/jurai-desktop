package com.jurai.ui.controller;

import com.jurai.data.AppState;
import com.jurai.data.model.Requerente;
import com.jurai.ui.controls.SimpleList;
import com.jurai.ui.menus.RequerenteDashboardMenu;
import com.jurai.ui.modal.ModalManager;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;

public class RequerenteDashboardController extends AbstractController<RequerenteDashboardMenu>  {

    @Override
    protected void attachEvents(RequerenteDashboardMenu pane) {
    }

    @Override
    protected void attachNotifiers(RequerenteDashboardMenu pane) {
        /*
        AppState.get().currentUserProperty().addListener((obs, o, n) -> {
            if (n != null) bindRequerenteList(pane.getRequerentesList());
        });*/
    }

    private void bindRequerenteList(SimpleList<Requerente> listPane) {
        Bindings.bindContent(listPane.getListObjects(), AppState.get().getCurrentUser().getRequerentes());
        AppState.get().getCurrentUser().getRequerentes().addListener((ListChangeListener<Requerente>) change -> {
            listPane.getSearchTextField().clear();
        });
    }
}
