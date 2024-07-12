package io.jurai.ui.controller;

import io.jurai.data.ApplicationState;
import io.jurai.ui.panes.AccountPane;

public class AccountPaneController extends AbstractController<AccountPane> {

    public AccountPaneController() {}


    @Override
    protected void attachEvents(AccountPane accountPane) {
    }

    @Override
    protected void attachNotifiers(AccountPane accountPane) {
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
            accountPane.getView().getChildren().add(accountPane.getLoginMenu().getContent());
        } else {
            accountPane.getView().getChildren().clear();
        }
    }
}
