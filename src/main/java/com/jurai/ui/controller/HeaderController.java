package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.GlobalEvents;
import com.jurai.data.model.Advogado;
import com.jurai.ui.panes.Header;
import com.jurai.ui.panes.QuickQueryPane;
import com.jurai.ui.util.Pane;


public class HeaderController extends AbstractController<Header> {

    public HeaderController() {
    }

    @Override
    protected void attachEvents(Header header) {
        header.getPfp().setOnMouseClicked(e -> {
            ApplicationState.get().setActivePane(Pane.AccountPane);
        });
    }

    @Override
    protected void attachNotifiers(Header header) {
        ApplicationState.get().addPropertyChangeListener(e -> {
            if ("activePane".equals(e.getPropertyName()) ||
                    "selectedDemanda".equals(e.getPropertyName()) ||
                    "selectedRequerente".equals(e.getPropertyName()) ||
                    "quickQueryMode".equals(e.getPropertyName())) {
                StringBuilder newUrl = new StringBuilder();
                switch (ApplicationState.get().getActivePane()) {
                    case DashboardPane:
                        newUrl.append(" / Dashboard");
                        if (ApplicationState.get().getSelectedRequerente() != null) {
                            newUrl.append(" / " + ApplicationState.get().getSelectedRequerente().nomeProperty().get());
                            if (ApplicationState.get().getSelectedDemanda() != null) {
                                newUrl.append(" / " + ApplicationState.get().getSelectedDemanda().nomeProperty().get());
                            }
                        }
                        break;
                    case QuickQueryPane:
                        newUrl.append(" / Consulta Rápida");
                        if (ApplicationState.get().getQuickQueryMode() == null) {
                            break;
                        }
                        if (ApplicationState.get().getQuickQueryMode().equals(QuickQueryPane.Mode.EMENTA)) {
                            newUrl.append(" / Ementa");
                        } else {
                            newUrl.append(" / PDF");
                        }
                        break;
                    case AccountPane:
                        newUrl.append(" / Conta");
                        if (ApplicationState.get().getCurrentUser() != null) {
                            newUrl.append(" / " + ApplicationState.get().getCurrentUser().nomeProperty().get());
                        }
                        break;
                    case DocPane:
                        newUrl.append(" / Documentos e Análises");
                        break;
                    default:
                        break;
                }
                header.getNavUrl().setUrl(newUrl.toString());
            } else if ("useLightTheme".equals(e.getPropertyName())) {
                header.themeChanged();
            } else if ("currentUser".equals(e.getPropertyName())) {
                Advogado currentUser = ApplicationState.get().getCurrentUser();
                if (currentUser != null) {
                    header.updatePfp(ApplicationState.get().getApiUrl() + "advogado/" + (long) currentUser.getId() + "/pfp");
                } else {
                    header.loadFallback();
                }
            }
        });

        GlobalEvents.get().onPfpChanged(e -> {
            Advogado currentUser = ApplicationState.get().getCurrentUser();
            if (currentUser != null) {
                header.updatePfp(ApplicationState.get().getApiUrl() + "advogado/" + (long) currentUser.getId() + "/pfp");
            } else {
                header.loadFallback();
            }
        });

    }
}
