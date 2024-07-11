package io.jurai.ui.controller;

import io.jurai.data.ApplicationState;
import io.jurai.ui.panes.AccountPane;

public class AccountPaneController {

    public AccountPaneController() {}

    public void initialize(AccountPane accountPane) {
        attachEvents(accountPane);
    }

    private void attachEvents(AccountPane accountPane) {
    }

    private void attachNotifiers(AccountPane accountPane) {
        ApplicationState.addPropertyChangeListener(event -> {
            if("loggedIn".equals(event.getPropertyName())) {
                loggedInStateChanged((Boolean) event.getNewValue(), accountPane);
            }
        });
    }

    public void loggedInStateChanged(Boolean newState, AccountPane accountPane) {
        if(!newState) {
            //remove login view and add account dashboard view
            accountPane.getView().getChildren().clear();
            accountPane.getView().getChildren().add(accountPane.getLoginMenu().getView());
        } else {
            accountPane.getView().getChildren().clear();
        }
    }

}
