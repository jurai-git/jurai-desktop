package com.jurai.ui.controller;

import com.jurai.data.AppState;
import com.jurai.ui.menus.DemandaAnalysisDashboardMenu;

public class DemandaAnalysisController extends AbstractController<DemandaAnalysisDashboardMenu> {
    @Override
    protected void attachEvents(DemandaAnalysisDashboardMenu pane) {

    }

    @Override
    protected void attachNotifiers(DemandaAnalysisDashboardMenu pane) {
        AppState.get().listen(e -> {
            if ("selectedDemanda".equals(e.getPropertyName())) {
                System.out.println("Changed selected demanda");
                pane.setActive(AppState.get().getSelectedDemanda() != null);
            }
        });
    }
}
