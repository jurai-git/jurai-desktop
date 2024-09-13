package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Demanda;
import com.jurai.data.model.Requerente;
import com.jurai.ui.menus.DemandaDashboardMenu;
import com.sun.source.tree.LambdaExpressionTree;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;

public class DemandaDashboardController extends AbstractController<DemandaDashboardMenu> {

    @Override
    protected void attachEvents(DemandaDashboardMenu pane) {

    }

    @Override
    protected void attachNotifiers(DemandaDashboardMenu pane) {
        ApplicationState.addPropertyChangeListener(propertyChangeEvent -> {
            if("selectedRequerente".equals(propertyChangeEvent.getPropertyName())) {
                bindDemandaList(pane.getDemandaList().getListObjects());
            }
        });
    }

    private void bindDemandaList(ObservableList<Demanda> paneRequerentes) {
        Bindings.bindContent(paneRequerentes, ApplicationState.getSelectedRequerente().demandas());
    }
}
