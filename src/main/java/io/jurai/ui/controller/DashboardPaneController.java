package io.jurai.ui.controller;

import io.jurai.App;
import io.jurai.data.ApplicationState;
import io.jurai.ui.panes.DashboardPane;
import io.jurai.ui.util.AccountMode;
import io.jurai.ui.util.Pane;

public class DashboardPaneController extends AbstractController<DashboardPane> {

    @Override
    public void initialize(DashboardPane pane) {
        super.initialize(pane);
        accountModeSwitched(ApplicationState.getAccountMode(), pane);
    }

    @Override
    protected void attachEvents(DashboardPane pane) {
        pane.getLoginHyperlink().setOnAction(_ -> ApplicationState.setActivePane(Pane.AccountPane));
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
        switch (newMode) {
            case LOGGED_IN:
                pane.getView().getChildren().add(pane.getActiveView());
                break;
            case null, default:
                pane.getView().getChildren().add(pane.getInactiveView());
        }
    }
}
