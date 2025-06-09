package com.jurai.ui.controller;

import com.jurai.data.AppState;
import com.jurai.data.model.Demanda;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.DemandaService;
import com.jurai.ui.menus.DemandaDashboardMenu;
import com.jurai.ui.modal.ModalManager;
import com.jurai.ui.modal.notif.DefaultMessageNotification;
import com.jurai.ui.modal.notif.NotificationType;
import com.jurai.util.UILogger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class DemandaDashboardController extends AbstractController<DemandaDashboardMenu> {

    @Override
    protected void attachEvents(DemandaDashboardMenu pane) {
        pane.getAddDemanda().setOnAction(e -> {
            ModalManager.getInstance().requestModal("demandaRegisterModal");
        });
        pane.getEditDeleteDemanda().setOnAction(e -> {
            ModalManager.getInstance().requestModal("demandaEditingModal");
        });
        pane.getDemandaList().addSelectedItemListener((observableValue, demandaSimpleListItem, t1) -> {
            if (t1 == null) {
                AppState.get().setSelectedDemanda(null);
            } else {
                AppState.get().setSelectedDemanda(t1.getObject());
            }
        });
    }

    @Override
    protected void attachNotifiers(DemandaDashboardMenu pane) {
        AppState.get().listen(propertyChangeEvent -> {
            if("selectedRequerente".equals(propertyChangeEvent.getPropertyName())) {
                if(AppState.get().getSelectedRequerente() != null) {
                    loadDemandas(pane);
                } else {
                    unbindDemandaList(pane.getDemandaList().getListObjects());
                    pane.getAddDemanda().setDisable(true);
                    pane.getEditDeleteDemanda().setDisable(true);
		        }
            }
        });
        AppState.get().listen(propertyChangeEvent -> {
            if("selectedDemanda".equals(propertyChangeEvent.getPropertyName())) {
                if(propertyChangeEvent.getNewValue() != null) {
                    pane.getEditDeleteDemanda().setDisable(false);
                } else {
                    pane.getEditDeleteDemanda().setDisable(true);
                }
            }
        });

        AppState.get().listen(propertyChangeEvent -> {
            if ("currentAccount".equals(propertyChangeEvent.getPropertyName())) {
                AppState.get().setSelectedDemanda(null);
            }
        });
    }

    private void unbindDemandaList(ObservableList<Demanda> paneRequerentes) {
        paneRequerentes.clear();
    }

    private void bindDemandaList(ObservableList<Demanda> paneRequerentes) {
        Bindings.bindContent(paneRequerentes, AppState.get().getSelectedRequerente().demandas());
    }

    private void loadDemandas(DemandaDashboardMenu pane) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> pane.getDemandaList().setLoading(true));
                try {
                    DemandaService.getInstance().reloadDemandas();
                } catch (ResponseNotOkException e) {
                    e.printStackTrace();
                    throw new Exception("Error loading demandas: " + e.getMessage());
                }
                Platform.runLater(() -> pane.getDemandaList().setLoading(false));
                return null;
            }

            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    bindDemandaList(pane.getDemandaList().getListObjects());
                    pane.getAddDemanda().setDisable(false);
                    Platform.runLater(() -> pane.getDemandaList().setLoading(false));
                });
            }

            @Override
            protected void failed() {
                AppState.get().setSelectedRequerente(null);
                UILogger.logError(getException().getMessage());
                new DefaultMessageNotification("Erro ao carregar demandas!", NotificationType.ERROR).show();

                Platform.runLater(() -> pane.getDemandaList().setLoading(false));
            }
        };

        var thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
