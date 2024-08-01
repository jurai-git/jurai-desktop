package io.jurai.ui.menus.controller;

import io.jurai.data.ApplicationState;
import io.jurai.ui.util.Pane;
import io.jurai.ui.controller.AbstractController;
import io.jurai.ui.controls.SidebarNavItem;
import io.jurai.ui.menus.SidebarNav;
import io.jurai.util.UILogger;

public class SidebarNavController extends AbstractController<SidebarNav> {
    @Override
    protected void attachEvents(SidebarNav pane) {
        pane.getAccount().setOnAction(e -> ApplicationState.setActivePane(Pane.AccountPane));
        pane.getQuickQuery().setOnAction(e -> ApplicationState.setActivePane(Pane.QuickQueryPane));
        pane.getDocuments().setOnAction(e -> ApplicationState.setActivePane(Pane.DocPane));
        pane.getDashboard().setOnAction(e -> ApplicationState.setActivePane(Pane.DashboardPane));
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
            SidebarNavItem item = switch(propertyChangeEvent.getNewValue()) {
                case Pane.AccountPane -> pane.getAccount();
                case Pane.DashboardPane -> pane.getDashboard();
                case Pane.QuickQueryPane -> pane.getQuickQuery();
                case Pane.DocPane -> pane.getDocuments();
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
