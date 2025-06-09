package com.jurai.ui.controller;

import com.jurai.data.AppState;
import com.jurai.ui.menus.DemandaAnalysisDashboardMenu;

public class DemandaAnalysisController extends AbstractController<DemandaAnalysisDashboardMenu> {
    @Override
    protected void attachEvents(DemandaAnalysisDashboardMenu pane) {

    }

    @Override
    protected void attachNotifiers(DemandaAnalysisDashboardMenu pane) {
        AppState.get().selectedDemandaProperty().addListener((obs, o, n) -> pane.setActive(n != null));
    }
}
