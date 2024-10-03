package com.jurai.ui.controller;

import com.jurai.data.model.Demanda;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.RequerenteService;
import com.jurai.ui.modal.DemandaRegisterModal;
import com.jurai.ui.modal.notif.DefaultMessageNotification;
import com.jurai.ui.modal.notif.NotificationType;

public class DemandaRegisterController extends AbstractController<DemandaRegisterModal> {
    private final RequerenteService requerenteService = RequerenteService.getInstance();

    @Override
    protected void attachEvents(DemandaRegisterModal pane) {
        pane.getCancels().forEach(button -> button.setOnAction(e -> pane.dispose()));
        pane.getNext().setOnAction(e -> pane.getContent().setActiveTab(pane.getTab2()));
        pane.getPrevious().setOnAction(e -> pane.getContent().setActiveTab(pane.getTab1()));
        pane.getSave().setOnAction(e -> {
            try {
                requerenteService.addDemanda(new Demanda(
                        pane.getForo(),
                        pane.getCompetencia(),
                        pane.getClasse(),
                        pane.getAssuntoPrincipal(),
                        pane.getPedidoLiminar(),
                        pane.getSegredoJustica(),
                        Double.parseDouble(pane.getValorAcao()),
                        pane.getDispensaLegal(),
                        pane.getJusticaGratiuta(),
                        pane.getGuiaCustas(),
                        pane.getResumo(),
                        pane.getStatus(),
                        pane.getIdentificacao()
                ));
                pane.dispose();
            } catch (NullPointerException ignored) {
                new DefaultMessageNotification(
                        "Parece que você deixou algum campo vazio!",
                        NotificationType.ERROR
                ).show();
            } catch (ResponseNotOkException ignored) {
                new DefaultMessageNotification(
                        "Ocorreu um erro com a conexão com o servidor. Cheque a sua conexão com a internet",
                        NotificationType.ERROR
                ).show();
            }
        });
    }

    @Override
    protected void attachNotifiers(DemandaRegisterModal pane) {

    }
}
