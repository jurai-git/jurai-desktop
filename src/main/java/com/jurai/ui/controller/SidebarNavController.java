package com.jurai.ui.controller;

import com.jurai.data.AppState;
import com.jurai.ui.util.AccountMode;
import com.jurai.ui.util.Pane;
import com.jurai.ui.controls.SidebarNavItem;
import com.jurai.ui.menus.SidebarNav;
import com.jurai.util.UILogger;

public class SidebarNavController extends AbstractController<SidebarNav> {
    @Override
    protected void attachEvents(SidebarNav pane) {
        pane.getAccount().setOnAction(e -> AppState.get().setActivePane(Pane.AccountPane));
        pane.getQuickQuery().setOnAction(e -> AppState.get().setActivePane(Pane.QuickQueryPane));
        pane.getDocuments().setOnAction(e -> AppState.get().setActivePane(Pane.DocPane));
        pane.getDashboard().setOnAction(e -> AppState.get().setActivePane(Pane.DashboardPane));

        pane.getLogout().setOnAction(e -> {
            AppState.get().setCurrentUser(null);
            AppState.get().setAccountMode(AccountMode.LOGGING_IN);
        });

        pane.getSidebarToggleButton().setOnAction(newvalue -> {
            AppState.get().setSidebarExtended(newvalue);
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
        AppState.get().activePaneProperty().addListener((obs, o, n) -> {
            SidebarNavItem item = switch(n) {
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
                AppState.get().setActivePane(Pane.DashboardPane);
            }
        });

        AppState.get().sidebarExtendedProperty().addListener((obs, o, n) -> {
            pane.getSidebarToggleButton().setActive(n);
        });

        AppState.get().useLightThemeProperty().addListener((obs, o, n) -> {
            pane.getItems().forEach(SidebarNavItem::themeChanged);
            pane.getItems().forEach(SidebarNavItem::themeChanged);
        });
    }

}
