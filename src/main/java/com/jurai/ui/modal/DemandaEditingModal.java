package com.jurai.ui.modal;

import com.jurai.data.model.Demanda;
import javafx.scene.control.Button;

@LoadingStrategy(LoadingStrategy.Strategy.LAZY)
public class DemandaEditingModal extends DemandaModal {
    private final Demanda object;

    public DemandaEditingModal(Demanda d) {
        super("demandaEditingModal");
        this.object = d;
        fillRequerenteInfo();
    }

    private void fillRequerenteInfo() {
        identificacao.setText(object.nomeProperty().get());
        foro.setText(object.getForo());
        status.setText(object.getStatusDemanda());
        competencia.setText(object.getCompetencia());
        classe.setText(object.getClasse());
        assuntoPrincipal.setText(object.getAssuntoPrincipal());
        pedidoLiminar.setSelected(object.isPedidoLiminar());
        segredoJustica.setSelected(object.isSegJustica());
        valorAcao.setText(String.format("%.2f", object.getValorAcao()));
        dispensaLegal.setSelected(object.isDispensaLegal());
        justicaGratiuta.setSelected(object.isJusticaGratuita());
        guiaCustas.setSelected(object.isGuiaCustas());
        resumo.setText(object.getResumo());
    }

    @Override
    protected void initButtons() {
        save = new Button("Salvar");
    }
}
