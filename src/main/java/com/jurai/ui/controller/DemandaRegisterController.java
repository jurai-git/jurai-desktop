package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Demanda;
import com.jurai.data.request.ResponseNotOkException;
import com.jurai.data.service.AdvogadoService;
import com.jurai.data.service.RequerenteService;
import com.jurai.ui.modal.DemandaRegisterModal;
import com.jurai.util.UILogger;

public class DemandaRegisterController extends AbstractController<DemandaRegisterModal> {
    private final RequerenteService requerenteService = RequerenteService.getInstance();

    @Override
    protected void attachEvents(DemandaRegisterModal pane) {
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
        });
    }

    @Override
    protected void attachNotifiers(DemandaRegisterModal pane) {

    }
}
