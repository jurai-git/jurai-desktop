package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.ui.menus.DemandaAnalysisDashboardMenu;

public class DemandaAnalysisController extends AbstractController<DemandaAnalysisDashboardMenu> {
    @Override
    protected void attachEvents(DemandaAnalysisDashboardMenu pane) {

    }

    @Override
    protected void attachNotifiers(DemandaAnalysisDashboardMenu pane) {
        ApplicationState.get().addPropertyChangeListener(e -> {
            if ("selectedDemanda".equals(e.getPropertyName())) {
                System.out.println("Changed selected demanda");
                pane.setActive(ApplicationState.get().getSelectedDemanda() != null);
            }
        });
    }
}
