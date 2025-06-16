package com.jurai.ui.controller;

import com.jurai.data.AppState;
import com.jurai.data.GlobalEvents;
import com.jurai.data.model.Advogado;
import com.jurai.ui.panes.DocumentsPane;
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
        AppState.get().activePaneProperty().addListener((obs, o, n) -> generateHeaderText(header));
        AppState.get().selectedDemandaProperty().addListener((obs, o, n) -> generateHeaderText(header));
        AppState.get().selectedRequerenteProperty().addListener((obs, o, n) -> generateHeaderText(header));
        AppState.get().quickQueryModeProperty().addListener((obs, o, n) -> generateHeaderText(header));
        AppState.get().globalSelectedDemandaProperty().addListener((obs, o, n) -> generateHeaderText(header));
        AppState.get().docPaneModeProperty().addListener((obs, o, n) -> generateHeaderText(header));

        AppState.get().useLightThemeProperty().addListener((obs, o, n) -> header.themeChanged());
        AppState.get().currentUserProperty().addListener((obs, o, n) -> {
            if (n != null) {
                header.updatePfp(AppState.get().getApiUrl() + "advogado/" + n.getId() + "/pfp");
            } else {
                header.loadFallback();
            }
        });

        GlobalEvents.get().onPfpChanged(() -> {
            Advogado currentUser = AppState.get().getCurrentUser();
            if (currentUser != null) {
                header.updatePfp(AppState.get().getApiUrl() + "advogado/" + currentUser.getId() + "/pfp");
            } else {
                header.loadFallback();
            }
        });
    }

    private void generateHeaderText(Header header) {
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
                if (AppState.get().getGlobalSelectedDemanda() != null) {
                    newUrl.append(" / " + AppState.get().getGlobalSelectedDemanda().getNome());
                }
                if (AppState.get().getDocPaneMode().equals(DocumentsPane.Mode.CHAT)) {
                    newUrl.append( " / Chat");
                }
                break;
            default:
                break;
        }
        header.getNavUrl().setUrl(newUrl.toString());
    }
}
