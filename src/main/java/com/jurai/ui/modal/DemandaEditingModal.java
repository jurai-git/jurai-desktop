package com.jurai.ui.modal;

import com.jurai.data.model.Demanda;
import com.jurai.ui.LoadingStrategy;
import com.jurai.ui.util.SpacerFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;

@LoadingStrategy(LoadingStrategy.Strategy.LAZY)
public class DemandaEditingModal extends DemandaModal {
    private final Demanda object;
    private Button delete;

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

    public Demanda getObject() {
        return object;
    }

    @Override
    protected void initButtons() {
        save = new Button("Salvar");
        delete = new Button("Deletar");
        delete.getStyleClass().add("red-button");
    }

    @Override
    protected void layControls() {
        super.layControls();
        tab1Actions.getChildren().setAll(
                tab1Cancel,
                SpacerFactory.hSpacer(Priority.ALWAYS),
                delete,
                SpacerFactory.hSpacer(Priority.ALWAYS),
                next
        );
    }

    public Button getDelete() {
        return delete;
    }
}
