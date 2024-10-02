package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.CircleGraph;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.VGroup;
import com.jurai.ui.util.ControlWrapper;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class DemandaAnalysisDashboardMenu extends AbstractMenu<VBox> {
    private VBox content, demandaInfo;
    private HBox centerContent;
    private CircleGraph circleGraph;
    private Label circleGraphLabel, status, assuntoPrincipal, nome, inactiveLabel;
    private Hyperlink argumentosLink;
    private Button moreDetails, redoAnalysis;


    @Override
    protected void initControls() {
        content = new VBox();
        centerContent = new HBox();
        centerContent.getStyleClass().add("spaced");
        circleGraph = new CircleGraph(0.242, "Chance de provimento");
        //circleGraph.minWidthProperty().bind(circleGraph.heightProperty().multiply(0.6));
        circleGraphLabel = new Label("Chance de provimento");
        circleGraphLabel.setAlignment(Pos.CENTER);
        circleGraphLabel.setTextAlignment(TextAlignment.CENTER);
        circleGraphLabel.setMaxWidth(Double.MAX_VALUE);
        moreDetails = new Button("Mais detalhes");
        redoAnalysis = new Button("Refazer análise");
        HoverAnimator.animateAll(1, 1, moreDetails, redoAnalysis);

        demandaInfo = new VBox();
        demandaInfo.getStyleClass().addAll("info-list", "content-box");
        inactiveLabel = new Label("Não há nenhuma demanda selecionada! Selecione uma para ver suas informações.");
        inactiveLabel.setTextAlignment(TextAlignment.CENTER);
        inactiveLabel.setWrapText(true);
        status = new Label("Em andamento");
        status.setWrapText(true);
        assuntoPrincipal = new Label("Apelação Cível");
        assuntoPrincipal.setWrapText(true);
        argumentosLink = new Hyperlink("Visualizar argumentos");
        argumentosLink.setWrapText(true);
        nome = new Label("Demanda \"Processo 1\"");
        nome.setWrapText(true);
        nome.setTextAlignment(TextAlignment.CENTER);
        HoverAnimator.animateHover(argumentosLink, 0.6, 0.6);

    }

    @Override
    protected void layControls() {
        demandaInfo.getChildren().addAll(
                new ControlWrapper(nome).withVgrow(Priority.ALWAYS),
                new ControlWrapper(status).withVgrow(Priority.ALWAYS),
                new ControlWrapper(assuntoPrincipal).withVgrow(Priority.ALWAYS),
                new ControlWrapper(argumentosLink).withVgrow(Priority.ALWAYS)
        );

        HBox.setHgrow(circleGraph, Priority.ALWAYS);
        VBox.setVgrow(circleGraph, Priority.ALWAYS);
        centerContent.getChildren().addAll(
                new VGroup().withHgrow(Priority.ALWAYS).withChildren(
                        circleGraphLabel,
                        circleGraph
                ).withStyleClass("small-content-box").withStyle("-fx-padding: .5em 0 0 0"),
                demandaInfo
        );

        VBox.setVgrow(centerContent, Priority.ALWAYS);
        content.getChildren().addAll(
                centerContent,
                new HGroup().withVgrow(Priority.NEVER).withMargin().withChildren(
                        moreDetails,
                        redoAnalysis
                ).withStyleClass("buttons-row")
        );
    }

    private void layActiveContent() {
        content.getChildren().setAll(
                centerContent,
                new HGroup().withVgrow(Priority.NEVER).withMargin().withChildren(
                        moreDetails,
                        redoAnalysis
                ).withStyleClass("buttons-row")
        );
    }

    private void layInactiveContent() {
        content.getChildren().setAll(inactiveLabel);
    }

    @Override
    public VBox getContent() {
        return content;
    }
}
