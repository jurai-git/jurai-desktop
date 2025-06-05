package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Requerente;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.RequerenteService;
import com.jurai.ui.modal.RequerenteEditingModal;
import com.jurai.ui.modal.notif.DefaultMessageNotification;
import com.jurai.ui.modal.notif.LoadingModal;
import com.jurai.ui.modal.notif.NotificationType;
import com.jurai.util.UILogger;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class RequerenteEditingModalController extends AbstractController<RequerenteEditingModal> {
    private final RequerenteService requerenteService = RequerenteService.getInstance();

    @Override
    protected void attachEvents(RequerenteEditingModal pane) {
        pane.getPersonalInfoNext().setOnAction(e -> {
            pane.setActiveTab(pane.getGeneralInfo());
        });
        pane.getGeneralInfoPrevious().setOnAction(e -> {
            pane.setActiveTab(pane.getPersonalInfo());
        });
        pane.getGeneralInfoNext().setOnAction(e -> {
            pane.setActiveTab(pane.getAddressInfo());
        });
        pane.getAddressInfoPrevious().setOnAction(e -> {
            pane.setActiveTab(pane.getGeneralInfo());
        });
        pane.getCancel().forEach(button -> button.setOnAction(e -> {
            pane.dispose();
        }));
        pane.getDelete().setOnAction(e -> {
            try {
                requerenteService.delete(pane.getObject());
                ApplicationState.get().setSelectedRequerente(null);
                pane.dispose();
            } catch (ResponseNotOkException ex) {
                ex.printStackTrace();
            }
        });

        pane.getCreate().setOnAction(e -> {
            Requerente r = pane.getObject();
            r.setCpfCnpj(pane.getCpfCnpj());
            r.setNome(pane.getNome());
            r.setNomeSocial(pane.getNomeSocial());
            r.setGenero(pane.getGenero());
            r.setIdoso(pane.isIdoso());
            r.setRg(pane.getRg());
            r.setOrgaoEmissor(pane.getOrgaoEmissor());
            r.setEstadoCivil(pane.getEstadoCivil());
            r.setNacionalidade(pane.getNacionalidade());
            r.setProfissao(pane.getProfissao());
            r.setCep(pane.getCep());
            r.setLogradouro(pane.getLogradouro());
            r.setEstado(pane.getEstado());
            r.setEmail(pane.getEmail());
            r.setNumImovel(pane.getNumero());
            r.setComplemento(pane.getComplemento());
            r.setBairro(pane.getBairro());
            r.setCidade(pane.getCidade());

            LoadingModal loadingModal = new LoadingModal();
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    Platform.runLater(() -> {
                        System.out.println("Showing loading modal...");
                        loadingModal.play();
                        loadingModal.show();
                    });

                    try {
                        requerenteService.update(r);
                    } catch (ResponseNotOkException e) {
                        e.printStackTrace();
                        throw new Exception("Error updating requerente: " + e.getMessage());
                    }
                    return null;
                }

                @Override
                protected void succeeded() {
                    Platform.runLater(() -> {
                        loadingModal.stop();
                        loadingModal.dispose();
                        pane.clear();
                        pane.dispose();
                    });
                }

                @Override
                protected void failed() {
                    UILogger.logError(getException().getMessage());
                    Platform.runLater(() -> {
                        loadingModal.stop();
                        loadingModal.dispose();
                        new DefaultMessageNotification("Erro ao atualizar requerente: " + getException().getMessage(), NotificationType.ERROR).show();
                    });
                }
            };

            var thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        });

    }

    @Override
    protected void attachNotifiers(RequerenteEditingModal pane) {

    }
}
