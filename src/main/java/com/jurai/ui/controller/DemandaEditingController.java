package com.jurai.ui.controller;

import com.jurai.data.model.Demanda;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.DemandaService;
import com.jurai.data.service.RequerenteService;
import com.jurai.ui.modal.DemandaEditingModal;
import com.jurai.ui.modal.popup.Notification;

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
            } catch (ResponseNotOkException ex) {
                new Notification("Erro ao atualizar!").show();
            }
        });

    }

    @Override
    protected void attachNotifiers(DemandaEditingModal pane) {

    }
}
