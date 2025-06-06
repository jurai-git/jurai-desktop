package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Requerente;
import com.jurai.data.request.InternalErrorCodes;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.RequerenteService;
import com.jurai.ui.modal.RequerenteEditingModal;
import com.jurai.ui.modal.notif.ConfirmationNotification;
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
            new ConfirmationNotification<String>("Você tem certeza que deseja deletar este requerente?", NotificationType.CONFIRMATION)
                .setOnYes(ev -> {
                    try {
                        requerenteService.delete(pane.getObject());
                        ApplicationState.get().setSelectedRequerente(null);
                        pane.dispose();
                        return null;
                    } catch (ResponseNotOkException ex) {
                        return switch(ex.getCode()) {
                            case 401 -> "Ocorreu um erro ao realizar sua autenticação. Tente sair e entrar novamente";
                            case 500 -> "Ocorreu um erro na nossa parte ao remover o requerente. Tente novamente mais tarde";
                            case InternalErrorCodes.NETWORK_ERROR -> "Ocorreu um erro de conexão. Verifique sua conexão com a internet e tente novamente";
                            default -> "Ocorreu um erro desconhecido. Código: " + ex.getCode();
                        };
                    }
                })
                .setAfterDispose(msg -> {
                    if (msg != null) {
                        new DefaultMessageNotification(msg, NotificationType.ERROR).show();
                    }
                })
                .show();
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
