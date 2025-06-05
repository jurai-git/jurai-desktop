package com.jurai.ui.menus;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.DemandaAnalysis;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.CircleGraph;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.VGroup;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

public class AnalysisResultsMenu extends AbstractMenu<StackPane> {
    private StackPane content, inactiveContent;
    private VBox activeContent, demandaInfo;
    private CircleGraph circleGraph;
    private Label circleGraphLabel, nome, inactiveLabel, positive, partial, negative;
    private Button viewAllArguments;
    private boolean isActive = false;

    @Override
    protected void initControls() {
        content = new StackPane();
        inactiveContent = new StackPane();
        activeContent = new VBox();
        positive = new Label();
        VBox.setVgrow(positive, Priority.ALWAYS);
        partial = new Label();
        VBox.setVgrow(partial, Priority.ALWAYS);
        negative = new Label();
        VBox.setVgrow(negative, Priority.ALWAYS);
        circleGraph = new CircleGraph(0.242, "Chance de provimento");
        circleGraphLabel = new Label("Chance de provimento");
        circleGraphLabel.setAlignment(Pos.CENTER);
        circleGraphLabel.setTextAlignment(TextAlignment.CENTER);
        circleGraphLabel.setMaxWidth(Double.MAX_VALUE);
        viewAllArguments = new Button("Visualisar todos os argumentos");
        HoverAnimator.animateAll(1, 1, viewAllArguments);

        demandaInfo = new VBox();
        demandaInfo.getStyleClass().addAll("info-list", "content-box");
        inactiveLabel = new Label("Faça uma análise rápida para obter os resultados!");
        inactiveLabel.setTextAlignment(TextAlignment.CENTER);
        inactiveLabel.setAlignment(Pos.CENTER);
        inactiveLabel.setWrapText(true);
        nome = new Label("Análise da Ementa");
        nome.setStyle("-fx-font-size: 1.3em;");
        nome.setWrapText(true);
        nome.setTextAlignment(TextAlignment.CENTER);
        VBox.setVgrow(nome, Priority.ALWAYS);
    }

    @Override
    protected void layControls() {
        demandaInfo.getStyleClass().add("medium-padding");
        demandaInfo.getStyleClass().add("medium-spacing");
        demandaInfo.getChildren().addAll(
                SpacerFactory.vSpacer(Priority.NEVER),
                nome,
                SpacerFactory.vSpacer(Priority.NEVER),
                positive,
                SpacerFactory.vSpacer(Priority.NEVER),
                partial,
                SpacerFactory.vSpacer(Priority.NEVER),
                negative,
                SpacerFactory.vSpacer(Priority.NEVER)
        );

        HBox.setHgrow(circleGraph, Priority.ALWAYS);
        VBox.setVgrow(circleGraph, Priority.ALWAYS);
        VBox.setVgrow(demandaInfo, Priority.ALWAYS);

        setHorizontal();
        VBox.setVgrow(activeContent, Priority.ALWAYS);

        inactiveContent.getChildren().add(inactiveLabel);
        layInactiveContent();
        ApplicationState.get().addPropertyChangeListener(propertyChangeEvent -> {
            if ("viewportSmall".equals(propertyChangeEvent.getPropertyName())) {
                if ((boolean) propertyChangeEvent.getNewValue()) {
                    setVertical();
                } else {
                    setHorizontal();
                }
            }
        });
    }

    public void layActiveContent(double negative, double partial, double positive, String predicted) {
        circleGraph.setPercentage(positive);
        nome.setText(switch(predicted) {
            case "positive" -> "Análise da ementa:\nRecurso provido";
            case "negative" -> "Análise da Ementa:\nRecurso não provido";
            case "partial" -> "Análise da Ementa:\nRecurso parcialmente provido";
            default -> "Análise da Ementa: ";
        });
        this.positive.setText(String.format("Chances de provimento: %.2f%%", positive * 100));
        this.negative.setText(String.format("Chances de não provimento: %.2f%%", negative * 100));
        this.partial.setText(String.format("Chances de provimento parcial: %.2f%%", partial * 100));
        content.getChildren().setAll(activeContent);
    }

    public void layActiveContent(DemandaAnalysis analysis) {
        layActiveContent(analysis.getNegative(), analysis.getPartial(), analysis.getPositive(), analysis.getPredicted());
    }

    public void layInactiveContent() {
        content.getChildren().setAll(inactiveLabel);
    }

    private void setHorizontal() {
        activeContent.getChildren().setAll(
                new HGroup().withChildren(
                        new VGroup().withHgrow(Priority.ALWAYS).withVgrow(Priority.ALWAYS).withChildren(
                                circleGraphLabel,
                                circleGraph
                        ).withStyleClass("small-content-box").withStyle("-fx-padding: .5em 0 0 0"),
                        demandaInfo
                ).withHgrow(Priority.ALWAYS).withVgrow(Priority.ALWAYS).withStyleClass("spaced")
        );
    }

    private void setVertical() {
        activeContent.getChildren().setAll(
                new VGroup().withChildren(
                        new VGroup().withHgrow(Priority.ALWAYS).withVgrow(Priority.ALWAYS).withChildren(
                                circleGraphLabel,
                                circleGraph
                        ).withStyleClass("small-content-box").withStyle("-fx-padding: .5em 0 0 0"),
                        demandaInfo
                ).withHgrow(Priority.ALWAYS).withVgrow(Priority.ALWAYS).withStyleClass("spaced")
        );
    }

    @Override
    public StackPane getContent() {
        return content;
    }
}
