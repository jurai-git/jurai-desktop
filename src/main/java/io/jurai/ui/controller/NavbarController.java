package io.jurai.ui.controller;

import io.jurai.data.ApplicationState;
import io.jurai.data.model.Pane;
import io.jurai.ui.menus.Navbar;


public class NavbarController extends AbstractController<Navbar> {

    public NavbarController() {
    }

    @Override
    protected void attachEvents(Navbar navbar) {
        navbar.getExitBtn().setOnAction(e -> {
            ApplicationState.setLoggedIn(false);
            ApplicationState.setActivePane(Pane.HomePane);
            navbar.getAccountControl().getPopOver().hide();
        });
        navbar.getRegisterBtn().setOnAction(e -> {
            ApplicationState.setActivePane(Pane.AccountPane);
            navbar.getAccountControl().getPopOver().hide();
        });
        navbar.getLoginBtn().setOnAction(e -> {
            ApplicationState.setActivePane(Pane.AccountPane);
            navbar.getAccountControl().getPopOver().hide();
        });
        navbar.getAccountBtn().setOnAction(e -> {
            ApplicationState.setActivePane(Pane.AccountPane);
            navbar.getAccountControl().getPopOver().hide();
        });
        navbar.getDashboardBtn().setOnAction(e -> {
            ApplicationState.setActivePane(Pane.DashboardPane);
            navbar.getAccountControl().getPopOver().hide();
        });
        navbar.getInicioBtn().setOnMouseClicked(e -> ApplicationState.setActivePane(Pane.HomePane));
        navbar.getPlanosBtn().setOnMouseClicked(e -> ApplicationState.setActivePane(Pane.PlanPane));
        navbar.getConsultaRapidaBtn().setOnMouseClicked(e -> ApplicationState.setActivePane(Pane.QuickQueryPane));
    }

    @Override
    protected void attachNotifiers(Navbar navbar) {

    }
}
