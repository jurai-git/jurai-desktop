package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.ui.panes.DashboardPane;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.Pane;

public class DashboardPaneController extends AbstractController<DashboardPane> {

    @Override
    public void initialize(DashboardPane pane) {
        super.initialize(pane);
        accountModeSwitched(ApplicationState.getAccountMode(), pane);
    }

    @Override
    protected void attachEvents(DashboardPane pane) {
        pane.getLoginHyperlink().setOnAction(e -> ApplicationState.setActivePane(Pane.AccountPane));
    }

    @Override
    protected void attachNotifiers(DashboardPane pane) {
        ApplicationState.addPropertyChangeListener(changeEvent -> {
            if("accountMode".equals(changeEvent.getPropertyName())) {
                accountModeSwitched((AccountMode) changeEvent.getNewValue(), pane);
            }
        });
    }

    private void accountModeSwitched(AccountMode newMode, DashboardPane pane) {
        pane.getView().getChildren().removeAll(pane.getView().getChildren());
        if(newMode == null) {
            pane.getView().getChildren().add(pane.getInactiveView());
            return;
        }
        switch (newMode) {
            case LOGGED_IN:
                pane.getView().getChildren().add(pane.getActiveView());
                break;
            default:
                pane.getView().getChildren().add(pane.getInactiveView());
        }
    }
}
