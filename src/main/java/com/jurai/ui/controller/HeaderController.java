package com.jurai.ui.controller;

import com.jurai.data.AppState;
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
            AppState.get().setActivePane(Pane.AccountPane);
        });
    }

    @Override
    protected void attachNotifiers(Header header) {
        AppState.get().listen(e -> {
            if ("activePane".equals(e.getPropertyName()) ||
                    "selectedDemanda".equals(e.getPropertyName()) ||
                    "selectedRequerente".equals(e.getPropertyName()) ||
                    "quickQueryMode".equals(e.getPropertyName())) {
                StringBuilder newUrl = new StringBuilder();
                switch (AppState.get().getActivePane()) {
                    case DashboardPane:
                        newUrl.append(" / Dashboard");
                        if (AppState.get().getSelectedRequerente() != null) {
                            newUrl.append(" / " + AppState.get().getSelectedRequerente().nomeProperty().get());
                            if (AppState.get().getSelectedDemanda() != null) {
                                newUrl.append(" / " + AppState.get().getSelectedDemanda().nomeProperty().get());
                            }
                        }
                        break;
                    case QuickQueryPane:
                        newUrl.append(" / Consulta Rápida");
                        if (AppState.get().getQuickQueryMode() == null) {
                            break;
                        }
                        if (AppState.get().getQuickQueryMode().equals(QuickQueryPane.Mode.EMENTA)) {
                            newUrl.append(" / Ementa");
                        } else {
                            newUrl.append(" / PDF");
                        }
                        break;
                    case AccountPane:
                        newUrl.append(" / Conta");
                        if (AppState.get().getCurrentUser() != null) {
                            newUrl.append(" / " + AppState.get().getCurrentUser().nomeProperty().get());
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
                Advogado currentUser = AppState.get().getCurrentUser();
                if (currentUser != null) {
                    header.updatePfp(AppState.get().getApiUrl() + "advogado/" + (long) currentUser.getId() + "/pfp");
                } else {
                    header.loadFallback();
                }
            }
        });

        GlobalEvents.get().onPfpChanged(e -> {
            Advogado currentUser = AppState.get().getCurrentUser();
            if (currentUser != null) {
                header.updatePfp(AppState.get().getApiUrl() + "advogado/" + (long) currentUser.getId() + "/pfp");
            } else {
                header.loadFallback();
            }
        });

    }
}
