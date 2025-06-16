package com.jurai.ui.menus;

import com.jurai.data.model.Demanda;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.CircleGraph;
import com.jurai.util.StringRandom;
import dev.mgcvale.fluidfx.components.groups.HGroup;
import dev.mgcvale.fluidfx.components.groups.VGroup;
import dev.mgcvale.fluidfx.components.layout.Spacers;
import dev.mgcvale.fluidfx.components.util.Ref;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import lombok.Getter;

import static dev.mgcvale.fluidfx.components.layout.Wrappers.wVgrow;

public class DemandaAnalysisDashboardMenu extends AbstractMenu<StackPane> {
    private StackPane content, inactiveContent;
    private VBox activeContent, demandaInfo;
    private HBox centerContent;
    private CircleGraph circleGraph;
    private Label circleGraphLabel, status, assuntoPrincipal, nome, inactiveLabel;
    private Hyperlink argumentosLink;
    @Getter
    private Button moreDetails, redoAnalysis;

    @Getter
    private Demanda demanda;

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
        argumentosLink = new Hyperlink("Mais informações");
        argumentosLink.setWrapText(true);
        nome = new Label("Demanda \"Processo 1\"");
        nome.setWrapText(true);
        nome.setTextAlignment(TextAlignment.CENTER);
        HoverAnimator.animateHover(argumentosLink, 0.6, 0.6);

    }

    @Override
    protected void layControls() {
        demandaInfo.getChildren().addAll(
            Spacers.vSpacer(),
                wVgrow(nome),
                Spacers.vSpacer(),
                wVgrow(status),
                Spacers.vSpacer(),
                wVgrow(assuntoPrincipal),
                Spacers.vSpacer(),
                wVgrow(argumentosLink),
            Spacers.vSpacer()
        );
        HBox.setHgrow(demandaInfo, Priority.SOMETIMES);

        HBox.setHgrow(circleGraph, Priority.ALWAYS);
        VBox.setVgrow(circleGraph, Priority.SOMETIMES);

        dev.mgcvale.fluidfx.components.util.Ref<VGroup> circleGroup = new Ref<>(null);
        centerContent.getChildren().addAll(
                new VGroup().grabInstance(circleGroup).wHgrow(Priority.NEVER).wChildren(
                        circleGraphLabel,
                        circleGraph
                ).wStyleClass("small-content-box").wStyle("-fx-padding: .5em 0 0 0").inPrefWidth(circleGroup.ref.heightProperty().add(48)),
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

    public void setDemanda(Demanda d) {
        if (d == null) {
            layInactiveContent();
        } else {
            nome.setText("Demanda \"" + d.getNome() + "\"");
            status.setText(d.getStatusDemanda());
            assuntoPrincipal.setText(d.getAssuntoPrincipal());
            circleGraph.setPercentage(StringRandom.getRandomFromString(d.getNome()));
            layActiveContent();
        }
    }

    private void layActiveContent() {
        content.getChildren().setAll(activeContent);
    }

    private void layInactiveContent() {
        content.getChildren().setAll(inactiveLabel);
    }

    public Hyperlink getArgumentosLink() {
        return argumentosLink;
    }

    @Override
    public StackPane getContent() {
        return content;
    }
}
