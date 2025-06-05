package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.Pane;
import com.jurai.ui.controls.SidebarNavItem;
import com.jurai.ui.menus.SidebarNav;
import com.jurai.util.UILogger;

public class SidebarNavController extends AbstractController<SidebarNav> {
    @Override
    protected void attachEvents(SidebarNav pane) {
        pane.getAccount().setOnAction(e -> ApplicationState.get().setActivePane(Pane.AccountPane));
        pane.getQuickQuery().setOnAction(e -> ApplicationState.get().setActivePane(Pane.QuickQueryPane));
        pane.getDocuments().setOnAction(e -> ApplicationState.get().setActivePane(Pane.DocPane));
        pane.getDashboard().setOnAction(e -> ApplicationState.get().setActivePane(Pane.DashboardPane));

        pane.getLogout().setOnAction(e -> {
            ApplicationState.get().setCurrentUser(null);
            ApplicationState.get().setAccountMode(AccountMode.LOGGING_IN);
        });

        pane.getSidebarToggleButton().setOnAction(newvalue -> {
            ApplicationState.get().setSidebarExtended(newvalue);
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
        ApplicationState.get().addPropertyChangeListener(propertyChangeEvent -> {
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
                ApplicationState.get().setActivePane(Pane.DashboardPane);
            }
        });

        ApplicationState.get().addPropertyChangeListener(propertyChangeEvent1 -> {
            if ("sidebarExtended".equals(propertyChangeEvent1.getPropertyName())) {
                pane.getSidebarToggleButton().setActive((Boolean) propertyChangeEvent1.getNewValue());
            }
        });

        ApplicationState.get().addPropertyChangeListener(change -> {
            if ("useLightTheme".equals(change.getPropertyName())) {
                pane.getItems().forEach(SidebarNavItem::themeChanged);
                pane.getItems().forEach(SidebarNavItem::themeChanged);
            }
        });

    }

}
