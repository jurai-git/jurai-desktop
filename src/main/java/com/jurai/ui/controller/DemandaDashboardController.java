package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Demanda;
import com.jurai.data.service.RequerenteService;
import com.jurai.ui.menus.DemandaDashboardMenu;
import com.jurai.ui.modal.ModalManager;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;

public class DemandaDashboardController extends AbstractController<DemandaDashboardMenu> {

    @Override
    protected void attachEvents(DemandaDashboardMenu pane) {
        pane.getAddDemanda().setOnAction(e -> {
            ModalManager.getInstance().requestModal("demandaRegisterModal");
        });
        pane.getEditDeleteDemanda().setOnAction(e -> {
            ModalManager.getInstance().requestModal("demandaEditingModal");
        });

        pane.getDemandaList().addSelectedItemListener((observableValue, demandaSimpleListItem, t1) -> {
            if(t1 != null) {
                pane.getEditDeleteDemanda().setDisable(false);
            } else {
                pane.getEditDeleteDemanda().setDisable(true);
            }
        });
    }

    @Override
    protected void attachNotifiers(DemandaDashboardMenu pane) {
        ApplicationState.addPropertyChangeListener(propertyChangeEvent -> {
            if("selectedRequerente".equals(propertyChangeEvent.getPropertyName())) {
                if(ApplicationState.getSelectedRequerente() != null) {
                    new RequerenteService().reloadDemandas();
                    bindDemandaList(pane.getDemandaList().getListObjects());
                    pane.getAddDemanda().setDisable(false);
                } else {
                    unbindDemandaList(pane.getDemandaList().getListObjects());
                    pane.getAddDemanda().setDisable(true);
		        }
            }
        });
    }

    private void unbindDemandaList(ObservableList<Demanda> paneRequerentes) {
        paneRequerentes.clear();
    }

    private void bindDemandaList(ObservableList<Demanda> paneRequerentes) {
        Bindings.bindContent(paneRequerentes, ApplicationState.getSelectedRequerente().demandas());
    }
}
