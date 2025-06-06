package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Demanda;
import com.jurai.data.model.Requerente;
import com.jurai.data.request.InternalErrorCodes;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.DemandaService;
import com.jurai.data.service.RequerenteService;
import com.jurai.ui.modal.DemandaEditingModal;
import com.jurai.ui.modal.ModalManager;
import com.jurai.ui.modal.notif.ConfirmationNotification;
import com.jurai.ui.modal.notif.DefaultMessageNotification;
import com.jurai.ui.modal.notif.NotificationType;

public class DemandaEditingController extends AbstractController<DemandaEditingModal> {
    private final DemandaService demandaService = DemandaService.getInstance();

    @Override
    protected void attachEvents(DemandaEditingModal pane) {
        pane.getCancels().forEach(button -> button.setOnAction(e -> {
            pane.dispose();
        }));
        pane.getNext().setOnAction(e -> {
            pane.getContent().setActiveTab(pane.getTab2());
        });
        pane.getPrevious().setOnAction(e -> {
            pane.getContent().setActiveTab(pane.getTab1());
        });

        pane.getDelete().setOnAction(e -> {
            new ConfirmationNotification<String>("Você tem certeza que deseja apagar esta demanda?", NotificationType.CONFIRMATION)
                .setOnYes(ev -> {
                    try {
                        demandaService.delete(pane.getObject());
                        return null;
                    } catch (ResponseNotOkException ex) {
                        return switch (ex.getCode()) {
                            case 401 -> "Ocorreu um erro ao realizar sua autenticação. Tente sair e entrar novamente";
                            case 500 -> "Ocorreu um erro na nossa parte ao remover a demanda. Tente novamente mais tarde";
                            case InternalErrorCodes.NETWORK_ERROR -> "Ocorreu um erro de conexão. Verifique sua conexão com a internet e tente novamente";
                            default -> "Ocorreu um erro desconhecido. Código: " + ex.getCode();
                        };
                    }
                })
                .setAfterDispose(msg -> {
                    if (msg != null) {
                        new DefaultMessageNotification(msg, NotificationType.ERROR).show();
                        return;
                    }

                    ModalManager.getInstance().exitModal();
                    Requerente req = ApplicationState.get().getSelectedRequerente();
                    if (req != null) {
                        req.demandas().remove(pane.getObject());
                    }
                })
                .show();
        });


        pane.getSave().setOnAction(e -> {
            try {
                Demanda d = pane.getObject();
                d.setClasse(pane.getClasse());
                d.setCompetencia(pane.getCompetencia());
                d.setNome(pane.getIdentificacao());
                d.setForo(pane.getForo());
                d.setAssuntoPrincipal(pane.getAssuntoPrincipal());
                d.setDispensaLegal(pane.getDispensaLegal());
                d.setCompetencia(pane.getCompetencia());
                d.setGuiaCustas(pane.getGuiaCustas());
                d.setJusticaGratuita(pane.getJusticaGratiuta());
                d.setPedidoLiminar(pane.getPedidoLiminar());
                d.setResumo(pane.getResumo());
                d.setSegJustica(pane.getSegredoJustica());
                d.setStatusDemanda(pane.getStatus());
                d.setValorAcao(Double.parseDouble(pane.getValorAcao()));
                demandaService.update(d);
                pane.dispose();
            } catch (ResponseNotOkException ex) {
                new DefaultMessageNotification("Erro ao atualizar!", NotificationType.ERROR).show();
            }
        });

    }

    @Override
    protected void attachNotifiers(DemandaEditingModal pane) {

    }
}
