package com.jurai.ui.controller;

import com.jurai.data.AppState;
import com.jurai.ui.menus.DemandaAnalysisDashboardMenu;
import com.jurai.ui.util.Pane;

public class DemandaAnalysisController extends AbstractController<DemandaAnalysisDashboardMenu> {
    @Override
    protected void attachEvents(DemandaAnalysisDashboardMenu pane) {
        pane.getArgumentosLink().setOnAction(e -> {
            AppState.get().setGlobalSelectedDemanda(AppState.get().getSelectedDemanda());
            AppState.get().setActivePane(Pane.DocPane);
        });
        pane.getMoreDetails().setOnAction(e -> {
            AppState.get().setGlobalSelectedDemanda(AppState.get().getSelectedDemanda());
            AppState.get().setActivePane(Pane.DocPane);
        });
        pane.getRedoAnalysis().setOnAction(e -> {
            AppState.get().setActivePane(Pane.DocPane);
        });
    }

    @Override
    protected void attachNotifiers(DemandaAnalysisDashboardMenu pane) {
        AppState.get().selectedDemandaProperty().addListener((obs, o, n) -> pane.setDemanda(n));
        AppState.get().selectedRequerenteProperty().addListener((obs, o, n) -> {
            System.out.println("CHANGED REQUERENTE");
            pane.setDemanda(null);
            if (n == null) {
                AppState.get().selectedDemandaProperty().set(null);
            }
            if (AppState.get().getSelectedDemanda() == null) {
                pane.setDemanda(null);
            }
        });
    }
}
