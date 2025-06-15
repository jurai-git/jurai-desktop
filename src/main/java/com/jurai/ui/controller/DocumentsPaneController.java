package com.jurai.ui.controller;

import com.jurai.data.AppState;
import com.jurai.data.GlobalEvents;
import com.jurai.data.model.Demanda;
import com.jurai.data.model.internal_state.AsyncState;
import com.jurai.data.request.InternalErrorCodes;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.DemandaService;
import com.jurai.ui.menus.DocumentChooser;
import com.jurai.ui.panes.DocumentsPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import java.util.List;

import static dev.mgcvale.fluidfx.components.layout.Wrappers.wStyleClasses;

public class DocumentsPaneController extends AbstractController<DocumentsPane> {
    private final DemandaService demandaService = DemandaService.getInstance();

    @Override
    protected void attachEvents(DocumentsPane pane) {
        DocumentChooser docChooser = pane.getDocumentChooser();

        docChooser.getDocList().addSelectedItemListener((obs, newVal, oldVal) -> {
            Demanda selected = docChooser.getDocList().getSelectedItem() == null ? null : docChooser.getDocList().getSelectedItem().getObject();
            AppState.get().setGlobalSelectedDemanda(selected);
        });
    }

    @Override
    protected void attachNotifiers(DocumentsPane pane) {
        DocumentChooser docChooser = pane.getDocumentChooser();

        AppState.get().docPaneModeProperty().addListener((obs, o, n) -> paneSwitched(pane));
        AppState.get().currentUserProperty().addListener((obs, o, n) -> fetchDemandas(pane));
        AppState.get().allDemandasProperty().addListener((obs, o, n) -> allDemandasChanged(pane, docChooser));
        AppState.get().globalSelectedDemandaProperty().addListener((obs, o, n) -> docChooser.updateContent(n));


        GlobalEvents.get().onGlobalDemandasEdited(() -> {
            docChooser.updateContent(AppState.get().getGlobalSelectedDemanda());
        });

        GlobalEvents.get().onGlobalDemandasChanged(() -> {
            fetchDemandas(pane);
        });

    }

    private void fetchDemandas(DocumentsPane pane) {
        if (AppState.get().getCurrentUser() != null) {
            AppState.get().setAllDemandas(new AsyncState<>(null, true, null));

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        List<Demanda> demandas = demandaService.loadSystemWideDemandas();
                        Platform.runLater(() ->  AppState.get().setAllDemandas(new AsyncState<>(FXCollections.observableArrayList(demandas), false, null)));
                    } catch (ResponseNotOkException e) {
                        Platform.runLater(() -> AppState.get().setAllDemandas(new AsyncState<>(null, false, e)));
                        throw e;
                    }
                    return null;
                }
            };
            new Thread(task).start();
        } else {
            AppState.get().setAllDemandas(new AsyncState<>(FXCollections.emptyObservableList(), false, null));
        }
    }

    private void paneSwitched(DocumentsPane pane) {
        pane.setPane(switch (AppState.get().getDocPaneMode()) {
            case CHOOSER -> pane.getDocumentChooser().getContent();
            case CHAT -> pane.getDocumentChat();
        });
    }

    private void allDemandasChanged(DocumentsPane pane, DocumentChooser docChooser) {
        AsyncState<ObservableList<Demanda>> allDemandas = AppState.get().getAllDemandas();

        if (allDemandas.isLoading()) {
            docChooser.getDocList().setLoading(true);
            return;
        }

        docChooser.getDocList().setLoading(false);

        if (allDemandas.getError() != null) {
            String errorMsg = "Ocorreu um erro desconhecido ao carregar suas demandas!";
            if (allDemandas.getError() instanceof ResponseNotOkException ex) {
                errorMsg = switch(ex.getCode()) {
                    case 400 -> "Ocorreu um erro ao carregar suas demandas. Verifique se o seu aplicativo está atualizado.";
                    case 401 -> "Ocorreu um problema ao te autenticar. Tente sair e fazer login novamente.";
                    case 403 -> "Você não tem acesso a este recurso no momento.";
                    case 500 -> "Ocorreu um erro da nossa parte ao carregar suas demandas. Tente novamente mais tarde.";
                    case InternalErrorCodes.NETWORK_ERROR -> "Ocorreu um erro de conexão. Verifique sua conexão com a internet.";
                    default -> "Ocorreu um erro desconhecido ao carregar suas demandas! Código do erro: " + ex.getCode();
                };
            }

            docChooser.getDocList().setError(wStyleClasses(new Label(errorMsg), "subheader"));
            return;
        }

        // no errors
        docChooser.getDocList().clear();
        for (Demanda demanda : allDemandas.getData()) {
            docChooser.getDocList().createListItem(demanda);
        }
    }
}
