package com.jurai.ui.modal;

import com.jurai.data.ApplicationData;
import com.jurai.data.ApplicationState;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.BasicTab;
import com.jurai.ui.controls.BasicTabbedPane;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.TextFieldSet;
import com.jurai.ui.util.ControlWrapper;
import com.jurai.ui.util.SpacerFactory;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public abstract class DemandaModal extends Modal<BasicTabbedPane> {
    protected TextFieldSet identificacao, foro, status, competencia,
            classe, assuntoPrincipal,
            valorAcao, resumo;

    protected CheckBox pedidoLiminar, segredoJustica, dispensaLegal, justicaGratiuta, guiaCustas;

    private BasicTabbedPane content;
    private BasicTab tab1, tab2;
    private VBox tab1Content, tab2Content;
    private VBox tab1Form, tab2Form;
    protected HBox tab1Actions, tab2Actions;
    protected Button tab1Cancel, tab2Cancel, next, previous, save;

    public DemandaModal(String name) {
        super(name);
        System.out.println("creating generic demandaModal");
    }

    @Override
    protected void initControls() {
        initButtons();
        tab2Cancel = new Button("Cancelar");
        HoverAnimator.animateAll(tab2Cancel, 1, 1);
        tab1Cancel = new Button("Cancelar");
        HoverAnimator.animateAll(tab1Cancel, 1, 1);
        next = new Button("Próximo");
        HoverAnimator.animateAll(next, 1, 1);
        previous = new Button("Anterior");
        HoverAnimator.animateAll(previous, 1, 1);
        identificacao = new TextFieldSet("Identificação");
        foro = new TextFieldSet("Foro");
        status = new TextFieldSet("Status");
        competencia = new TextFieldSet("Competência");
        classe = new TextFieldSet("Classe");
        assuntoPrincipal = new TextFieldSet("Assunto Principal");
        pedidoLiminar = new CheckBox("Pedido Liminar");
        segredoJustica = new CheckBox("Segredo de Justiça");
        valorAcao = new TextFieldSet("Valor da Ação");
        dispensaLegal = new CheckBox("Dispensa Legal");
        justicaGratiuta = new CheckBox("Justiça Gratuita");
        guiaCustas = new CheckBox("Guia de Custas");
        resumo = new TextFieldSet("Resumo");

        // tab1
        tab1Content = new VBox();
        tab1Actions = new HBox();
        tab1Form = new VBox();
        tab1Form.getStyleClass().add("fields");
        tab1 = new BasicTab("Parte 1", tab1Content);

         // tab 2
        tab2Content = new VBox();
        tab2Actions = new HBox();
        tab2Form = new VBox();
        tab2Form.getStyleClass().add("fields");
        tab2 = new BasicTab("Parte 2", tab2Content);

        content = new BasicTabbedPane();
        content.addTab(tab1);
        content.addTab(tab2);
        content.setActiveTab(tab1);
        content.getStyleClass().addAll("form", "no-decoration");
    }

    protected abstract void initButtons();

    @Override
    protected void layControls() {
        // First page
        VBox.setVgrow(identificacao, Priority.ALWAYS);
        identificacao.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(foro, Priority.ALWAYS);
        foro.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(status, Priority.ALWAYS);
        status.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(competencia, Priority.ALWAYS);
        competencia.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(classe, Priority.ALWAYS);
        classe.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(assuntoPrincipal, Priority.ALWAYS);
        assuntoPrincipal.maxWidthProperty().bind(content.widthProperty().multiply(0.8));

        tab1Form.getChildren().addAll(
            SpacerFactory.createVBoxSpacer(ApplicationData.defaultIconSizeProperty().get()),
            identificacao,
            foro,
            status,
            competencia,
            classe,
            SpacerFactory.createVBoxSpacer(ApplicationData.defaultIconSizeProperty().get())
        );

        tab1Cancel.getStyleClass().add("red-button");
        tab1Actions.getChildren().addAll(
            tab1Cancel,
            SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
            next
        );

        VBox.setVgrow(tab1Actions, Priority.NEVER);
        VBox.setVgrow(tab1Form, Priority.ALWAYS);
        tab1Content.getChildren().addAll(tab1Form, tab1Actions);

        // Second page
        assuntoPrincipal.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(assuntoPrincipal, Priority.ALWAYS);
        VBox.setVgrow(valorAcao, Priority.ALWAYS);
        valorAcao.maxWidthProperty().bind(content.widthProperty().multiply(0.8));
        VBox.setVgrow(resumo, Priority.ALWAYS);
        resumo.maxWidthProperty().bind(content.widthProperty().multiply(0.8));

        tab2Form.getChildren().addAll(
            SpacerFactory.createVBoxSpacer(ApplicationData.defaultIconSizeProperty().get() * 0.8),
            assuntoPrincipal,
            valorAcao,
            new HGroup().withMargin().withVgrow(Priority.ALWAYS).withChildren(
                        pedidoLiminar,
                        segredoJustica,
                        dispensaLegal
            ),
            new HGroup().withMargin().withVgrow(Priority.ALWAYS).withChildren(
                    justicaGratiuta,
                    guiaCustas
            ),
            resumo,
            SpacerFactory.createVBoxSpacer(ApplicationData.defaultIconSizeProperty().get() * 0.8)
        );

        tab2Cancel.getStyleClass().add("red-button");
        tab2Actions.getChildren().addAll(
            tab2Cancel,
            SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
            previous,
            SpacerFactory.createHBoxSpacer(12),
            save
        );

        VBox.setVgrow(tab2Actions, Priority.NEVER);
        VBox.setVgrow(tab2Form, Priority.ALWAYS);
        tab2Content.getChildren().addAll(tab2Form, tab2Actions);
    }

    public List<Button> getCancels() {
        return List.of(tab1Cancel, tab2Cancel);
    }

    public Button getNext() {
        return next;
    }

    public Button getPrevious() {
        return previous;
    }

    public Button getSave() {
        return save;
    }

    public BasicTab getTab1() {
        return tab1;
    }

    public BasicTab getTab2() {
        return tab2;
    }

    public String getResumo() {
        return resumo.getText();
    }

    public boolean getGuiaCustas() {
        return guiaCustas.isSelected();
    }

    public boolean getJusticaGratiuta() {
        return justicaGratiuta.isSelected();
    }

    public boolean getDispensaLegal() {
        return dispensaLegal.isSelected();
    }

    public String getValorAcao() {
        return valorAcao.getText();
    }

    public boolean getSegredoJustica() {
        return segredoJustica.isSelected();
    }

    public boolean getPedidoLiminar() {
        return pedidoLiminar.isSelected();
    }

    public String getAssuntoPrincipal() {
        return assuntoPrincipal.getText();
    }

    public String getClasse() {
        return classe.getText();
    }

    public String getCompetencia() {
        return competencia.getText();
    }

    public String getStatus() {
        return status.getText();
    }

    public String getForo() {
        return foro.getText();
    }

    public String getIdentificacao() {
        return identificacao.getText();
    }

    @Override
    public BasicTabbedPane getContent() {
        return content;
    }
}
