package io.jurai.ui.controller;

import io.jurai.App;
import io.jurai.data.ApplicationState;
import io.jurai.data.model.Pane;
import io.jurai.data.notifier.StateNotifier;
import io.jurai.ui.menus.Navbar;

import java.util.function.Consumer;

public class NavbarController {
    private Navbar navbar;

    public NavbarController(Navbar navbar) {
        this.navbar = navbar;
        attachEvents();
    }

    private void attachEvents() {
        navbar.getCadastroEntrarBtn().setOnMouseClicked(e -> ApplicationState.setActivePane(Pane.AccountPane));
        navbar.getInicioBtn().setOnMouseClicked(e -> ApplicationState.setActivePane(Pane.HomePane));
        navbar.getPlanosBtn().setOnMouseClicked(e -> ApplicationState.setActivePane(Pane.PlanPane));
        navbar.getConsultaRapidaBtn().setOnMouseClicked(e -> ApplicationState.setActivePane(Pane.QuickQueryPane));
    }
}
