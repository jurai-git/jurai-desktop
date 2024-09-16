package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Demanda;
import com.jurai.data.model.Requerente;
import com.jurai.data.service.RequerenteService;
import com.jurai.ui.menus.DemandaDashboardMenu;
import com.jurai.ui.modal.ModalManager;
import com.sun.source.tree.LambdaExpressionTree;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;

public class DemandaDashboardController extends AbstractController<DemandaDashboardMenu> {

    @Override
    protected void attachEvents(DemandaDashboardMenu pane) {
        pane.getAddDemanda().setOnAction(e -> {
            ModalManager.getInstance().requestModal("demandaRegisterModal");
        });
    }

    @Override
    protected void attachNotifiers(DemandaDashboardMenu pane) {
        ApplicationState.addPropertyChangeListener(propertyChangeEvent -> {
            if("selectedRequerente".equals(propertyChangeEvent.getPropertyName())) {
                if(ApplicationState.getSelectedRequerente() != null) {
                    new RequerenteService().reloadDemandas();
                    bindDemandaList(pane.getDemandaList().getListObjects());
                }
            }
        });
    }

    private void bindDemandaList(ObservableList<Demanda> paneRequerentes) {
        Bindings.bindContent(paneRequerentes, ApplicationState.getSelectedRequerente().demandas());
    }
}
