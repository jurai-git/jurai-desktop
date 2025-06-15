package com.jurai.ui.controller;

import com.jurai.data.AppState;
import com.jurai.data.model.DemandaAnalysis;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AIService;
import com.jurai.ui.menus.EmentaQuickQueryTab;
import com.jurai.ui.modal.ModalManager;
import com.jurai.ui.modal.notif.DefaultMessageNotification;
import com.jurai.ui.modal.notif.NotificationType;

public class EmentaTabController extends AbstractController<EmentaQuickQueryTab> {
    private AIService aiService;

    @Override
    public void initialize(EmentaQuickQueryTab pane) {
        super.initialize(pane);
        this.aiService = AIService.getInstance();
    }

    @Override
    protected void attachEvents(EmentaQuickQueryTab pane) {
        pane.getSend().setOnAction(e -> {
            System.out.println("aperto");
            String ementa = pane.getEmenta().getText();
            if (ementa.isEmpty()) {
                ModalManager.getInstance().requestNotification(
                        new DefaultMessageNotification("Ementa não pode ser vazia", NotificationType.ERROR)
                );
                return;
            }

            DemandaAnalysis analysis;
            try {
                analysis = aiService.analyzeDemanda(ementa);
                System.out.println("analise: " + analysis);
                pane.getAnalysisResultsMenu().layActiveContent(analysis);
                pane.getViewArguments().setDisable(false);
            } catch (ResponseNotOkException ex) {
                ModalManager.getInstance().requestNotification(
                        new DefaultMessageNotification("Erro ao analisar demanda! Código do erro: " + ex.getCode(), NotificationType.ERROR)
                );
            }
        });

        pane.getClear().setOnAction(e -> clear(pane));
    }

    @Override
    protected void attachNotifiers(EmentaQuickQueryTab pane) {
        AppState.get().currentUserProperty().addListener((obs, o, n) -> {
            clear(pane);
        });
    }

    private void clear(EmentaQuickQueryTab pane) {
        pane.getViewArguments().setDisable(true);
        pane.getEmenta().clear();
        pane.getAnalysisResultsMenu().layInactiveContent();
    }
}
