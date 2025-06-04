package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Advogado;
import com.jurai.ui.panes.Header;
import com.jurai.ui.panes.QuickQueryPane;
import com.jurai.ui.util.Pane;
import com.jurai.util.EventLogger;


public class HeaderController extends AbstractController<Header> {

    public HeaderController() {
    }

    @Override
    protected void attachEvents(Header header) {
        header.getPfp().setOnMouseClicked(e -> {
            ApplicationState.getInstance().setActivePane(Pane.AccountPane);
        });
    }

    @Override
    protected void attachNotifiers(Header header) {
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
                header.getNavUrl().setUrl(newUrl.toString());
            } else if ("useLightTheme".equals(e.getPropertyName())) {
                header.themeChanged();
            } else if ("currentUser".equals(e.getPropertyName())) {
                Advogado currentUser = ApplicationState.getInstance().getCurrentUser();
                if (currentUser != null) {
                    header.updatePfp(ApplicationState.getInstance().getApiUrl() + "advogado/" + (long) currentUser.getId() + "/pfp");
                } else {
                    header.loadFallback();
                }
            }
        });


        ApplicationState.getInstance().addPropertyChangeListener(e -> {
            if ("currentUser".equals(e.getPropertyName())) {

            }
        });
    }
}
