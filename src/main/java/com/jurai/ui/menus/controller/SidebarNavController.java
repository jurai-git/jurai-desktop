package com.jurai.ui.menus.controller;

import com.jurai.data.ApplicationState;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.Pane;
import com.jurai.ui.controller.AbstractController;
import com.jurai.ui.controls.SidebarNavItem;
import com.jurai.ui.menus.SidebarNav;
import com.jurai.util.UILogger;

public class SidebarNavController extends AbstractController<SidebarNav> {
    @Override
    protected void attachEvents(SidebarNav pane) {
        pane.getAccount().setOnAction(e -> ApplicationState.setActivePane(Pane.AccountPane));
        pane.getQuickQuery().setOnAction(e -> ApplicationState.setActivePane(Pane.QuickQueryPane));
        pane.getDocuments().setOnAction(e -> ApplicationState.setActivePane(Pane.DocPane));
        pane.getDashboard().setOnAction(e -> ApplicationState.setActivePane(Pane.DashboardPane));

        pane.getLogout().setOnAction(e -> {
            ApplicationState.setCurrentUser(null);
            ApplicationState.setAccountMode(AccountMode.LOGGING_IN);
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
        ApplicationState.addPropertyChangeListener(propertyChangeEvent -> {
            if(!"activePane".equals(propertyChangeEvent.getPropertyName())) {
                return;
            }
            System.out.println(propertyChangeEvent.getNewValue().toString());
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
                ApplicationState.setActivePane(Pane.DashboardPane);
            }
        });
    }
}