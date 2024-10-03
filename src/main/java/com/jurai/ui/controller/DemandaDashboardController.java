package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Demanda;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.DemandaService;
import com.jurai.data.service.RequerenteService;
import com.jurai.ui.menus.DemandaDashboardMenu;
import com.jurai.ui.modal.ModalManager;
import com.jurai.ui.modal.notif.DefaultMessageNotification;
import com.jurai.ui.modal.notif.NotificationType;
import com.jurai.util.EventLogger;
import com.jurai.util.UILogger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;

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
                ApplicationState.getInstance().setSelectedDemanda(null);
            } else {
                ApplicationState.getInstance().setSelectedDemanda(t1.getObject());
            }
        });
    }

    @Override
    protected void attachNotifiers(DemandaDashboardMenu pane) {
        ApplicationState.getInstance().addPropertyChangeListener(propertyChangeEvent -> {
            if("selectedRequerente".equals(propertyChangeEvent.getPropertyName())) {
                if(ApplicationState.getInstance().getSelectedRequerente() != null) {
                    loadDemandas(pane);
                } else {
                    unbindDemandaList(pane.getDemandaList().getListObjects());
                    pane.getAddDemanda().setDisable(true);
                    pane.getEditDeleteDemanda().setDisable(true);
		        }
            }
        });
        ApplicationState.getInstance().addPropertyChangeListener(propertyChangeEvent -> {
            if("selectedDemanda".equals(propertyChangeEvent.getPropertyName())) {
                if(propertyChangeEvent.getNewValue() != null) {
                    pane.getEditDeleteDemanda().setDisable(false);
                } else {
                    pane.getEditDeleteDemanda().setDisable(true);
                }
            }
        });
    }

    private void unbindDemandaList(ObservableList<Demanda> paneRequerentes) {
        paneRequerentes.clear();
    }

    private void bindDemandaList(ObservableList<Demanda> paneRequerentes) {
        Bindings.bindContent(paneRequerentes, ApplicationState.getInstance().getSelectedRequerente().demandas());
    }

    private void loadDemandas(DemandaDashboardMenu pane) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> pane.getDemandaList().setLoading(true));
                try {
                    DemandaService.getInstance().reloadDemandas();
                } catch (ResponseNotOkException e) {
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
                });
            }

            @Override
            protected void failed() {
                UILogger.logError(getException().getMessage());
                new DefaultMessageNotification("Erro ao carregar demandas!", NotificationType.ERROR).show();
            }
        };

        var thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
