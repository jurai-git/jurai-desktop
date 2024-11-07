package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.ui.panes.Navbar;
import com.jurai.ui.panes.QuickQueryPane;


public class NavbarController extends AbstractController<Navbar> {

    public NavbarController() {
    }

    @Override
    protected void attachEvents(Navbar navbar) {

    }

    @Override
    protected void attachNotifiers(Navbar navbar) {
        ApplicationState.getInstance().addPropertyChangeListener(e -> {
            if ("activePane".equals(e.getPropertyName()) ||
                    "selectedDemanda".equals(e.getPropertyName()) ||
                    "selectedRequerente".equals(e.getPropertyName()) ||
                    "quickQueryMode".equals(e.getPropertyName())) {
                StringBuilder newUrl = new StringBuilder();
                switch (ApplicationState.getInstance().getActivePane()) {
                    case DashboardPane:
                        newUrl.append(" / Dashboard");
                        if (ApplicationState.getInstance().getSelectedRequerente() != null) {
                            newUrl.append(" / " + ApplicationState.getInstance().getSelectedRequerente().nomeProperty().get());
                            if (ApplicationState.getInstance().getSelectedDemanda() != null) {
                                newUrl.append(" / " + ApplicationState.getInstance().getSelectedDemanda().nomeProperty().get());
                            }
                        }
                        break;
                    case QuickQueryPane:
                        newUrl.append(" / Consulta Rápida");
                        if (ApplicationState.getInstance().getQuickQueryMode() == null) {
                            break;
                        }
                        if (ApplicationState.getInstance().getQuickQueryMode().equals(QuickQueryPane.Mode.EMENTA)) {
                            newUrl.append(" / Ementa");
                        } else {
                            newUrl.append(" / PDF");
                        }
                        break;
                    case AccountPane:
                        newUrl.append(" / Conta");
                        if (ApplicationState.getInstance().getCurrentUser() != null) {
                            newUrl.append(" / " + ApplicationState.getInstance().getCurrentUser().nomeProperty().get());
                        }
                        break;
                    case DocPane:
                        newUrl.append(" / Documentos e Análises");
                        break;
                    default:
                        break;
                }
                navbar.getNavUrl().setUrl(newUrl.toString());
            }
        });
    }
}
