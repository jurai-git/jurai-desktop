package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.Pane;
import com.jurai.ui.controls.SidebarNavItem;
import com.jurai.ui.menus.SidebarNav;
import com.jurai.util.UILogger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SidebarNavController extends AbstractController<SidebarNav> {
    @Override
    protected void attachEvents(SidebarNav pane) {
        pane.getAccount().setOnAction(e -> ApplicationState.getInstance().setActivePane(Pane.AccountPane));
        pane.getQuickQuery().setOnAction(e -> ApplicationState.getInstance().setActivePane(Pane.QuickQueryPane));
        pane.getDocuments().setOnAction(e -> ApplicationState.getInstance().setActivePane(Pane.DocPane));
        pane.getDashboard().setOnAction(e -> ApplicationState.getInstance().setActivePane(Pane.DashboardPane));

        pane.getLogout().setOnAction(e -> {
            ApplicationState.getInstance().setCurrentUser(null);
            ApplicationState.getInstance().setAccountMode(AccountMode.LOGGING_IN);
        });

        pane.getSidebarToggleButton().setOnAction(newvalue -> {
            ApplicationState.getInstance().setSidebarExtended(newvalue);
        });
    }

    private void deactivateAll(SidebarNav pane) {
        pane.getAccount().setActive(false);
        pane.getDashboard().setActive(false);
        pane.getDocuments().setActive(false);
        pane.getQuickQuery().setActive(false);
    }

    @Override
    protected void attachNotifiers(SidebarNav pane) {
        ApplicationState.getInstance().addPropertyChangeListener(propertyChangeEvent -> {
            if(!"activePane".equals(propertyChangeEvent.getPropertyName())) {
                return;
            }
            SidebarNavItem item = switch((Pane) propertyChangeEvent.getNewValue()) {
                case AccountPane -> pane.getAccount();
                case DashboardPane -> pane.getDashboard();
                case QuickQueryPane -> pane.getQuickQuery();
                case DocPane -> pane.getDocuments();
                default -> null;
            };

            if(item != null) {
                deactivateAll(pane);
                item.setActive(true);

            } else {
                UILogger.logError("Changed to invalid active pane!");
                UILogger.logWarning("Falling back to DashboardPane");
                ApplicationState.getInstance().setActivePane(Pane.DashboardPane);
            }
        });

        ApplicationState.getInstance().addPropertyChangeListener(propertyChangeEvent1 -> {
            if ("sidebarExtended".equals(propertyChangeEvent1.getPropertyName())) {
                pane.getSidebarToggleButton().setActive((Boolean) propertyChangeEvent1.getNewValue());
            }
        });
    }
}
