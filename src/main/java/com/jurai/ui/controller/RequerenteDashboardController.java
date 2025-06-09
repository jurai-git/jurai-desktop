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
        pane.getAddRequerente().setOnAction(e -> {
            ModalManager.getInstance().requestModal("requerenteRegisterModal");
        });
        pane.getEditDeleteRequerente().setOnAction(e -> {
            ModalManager.getInstance().requestModal("requerenteEditingModal");
        });

        pane.getRequerentesList().addSelectedItemListener((observableValue, oldValue, newValue) -> {
            AppState.get().setSelectedRequerente(newValue == null ? null : newValue.getObject());
            if(newValue == null) {
                pane.getEditDeleteRequerente().setDisable(true);
            } else {
                pane.getEditDeleteRequerente().setDisable(false);
            }
        });
    }

    @Override
    protected void attachNotifiers(RequerenteDashboardMenu pane) {
        AppState.get().currentUserProperty().addListener((obs, o, n) -> {
            if (n != null) bindRequerenteList(pane.getRequerentesList());
        });
    }

    private void bindRequerenteList(SimpleList<Requerente> listPane) {
        Bindings.bindContent(listPane.getListObjects(), AppState.get().getCurrentUser().getRequerentes());
        AppState.get().getCurrentUser().getRequerentes().addListener((ListChangeListener<Requerente>) change -> {
            listPane.getSearchTextField().clear();
        });
    }
}
