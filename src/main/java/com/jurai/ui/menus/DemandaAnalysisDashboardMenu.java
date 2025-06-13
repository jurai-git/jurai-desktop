package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.CircleGraph;
import dev.mgcvale.fluidfx.components.groups.HGroup;
import dev.mgcvale.fluidfx.components.groups.VGroup;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import static dev.mgcvale.fluidfx.components.layout.Wrappers.wVgrow;

public class DemandaAnalysisDashboardMenu extends AbstractMenu<StackPane> {
    private StackPane content, inactiveContent;
    private VBox activeContent, demandaInfo;
    private HBox centerContent;
    private CircleGraph circleGraph;
    private Label circleGraphLabel, status, assuntoPrincipal, nome, inactiveLabel;
    private Hyperlink argumentosLink;
    private Button moreDetails, redoAnalysis;
    private boolean isActive = false;

    @Override
    protected void initControls() {
        content = new StackPane();
        inactiveContent = new StackPane();
        activeContent = new VBox();
        centerContent = new HBox();
        centerContent.getStyleClass().add("spaced");
        circleGraph = new CircleGraph(0.242, "Chance de provimento");
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
                wVgrow(nome),
                wVgrow(status),
                wVgrow(assuntoPrincipal),
                wVgrow(argumentosLink)
        );

        HBox.setHgrow(circleGraph, Priority.ALWAYS);
        VBox.setVgrow(circleGraph, Priority.ALWAYS);
        centerContent.getChildren().addAll(
                new VGroup().wHgrow(Priority.ALWAYS).wChildren(
                        circleGraphLabel,
                        circleGraph
                ).wStyleClass("small-content-box").wStyle("-fx-padding: .5em 0 0 0"),
                demandaInfo
        );

        VBox.setVgrow(centerContent, Priority.ALWAYS);
        activeContent.getChildren().addAll(
                centerContent,
                new HGroup().wVgrow(Priority.NEVER).spaceAround().wChildren(
                        moreDetails,
                        redoAnalysis
                ).wStyleClass("buttons-row")
        );

        inactiveContent.getChildren().add(inactiveLabel);
        layInactiveContent();
    }


    public void setActive(boolean active) {
        if (isActive != active) {
            isActive = active;
            if (active) {
                layActiveContent();
            } else {
                layInactiveContent();
            }
        }
    }

    private void layActiveContent() {
        content.getChildren().setAll(activeContent);
    }

    private void layInactiveContent() {
        content.getChildren().setAll(inactiveLabel);
    }

    @Override
    public StackPane getContent() {
        return content;
    }
}
