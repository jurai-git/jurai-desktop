package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.ui.panes.QuickQueryPane;

public class QuickQueryPaneController extends AbstractController<QuickQueryPane> {
    @Override
    protected void attachEvents(QuickQueryPane pane) {
        pane.getTabbedPane().setOnTabChanged(() -> {
            ApplicationState.get().setQuickQueryMode(
                    pane.getTabbedPane().getActiveTab().getName().equals("PDF") ?
                    QuickQueryPane.Mode.PDF :
                    QuickQueryPane.Mode.EMENTA
            );
        });
    }

    @Override
    protected void attachNotifiers(QuickQueryPane pane) {
    }
}
