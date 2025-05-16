package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.util.SpacerFactory;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EmentaQuickQueryTab extends AbstractMenu<HBox> {
    private HBox tabContent;
    private VBox leftContent;
    private VBox rightContent;
    private HBox form;
    private Button send, clear, viewArguments;
    private TextArea ementaTextArea;
    private Label ementaLabel, analysisLabel;
    private AnalysisResultsMenu analysisResultsMenu;

    @Override
    protected void initControls() {
        send = new Button("Enviar");
        send.getStyleClass().addAll("blue-button");
        clear = new Button("Limpar");
        viewArguments = new Button("Visualisar argumentos completos");
        analysisLabel = new Label("Análise gerada");

        HBox.setHgrow(send, Priority.ALWAYS);
        HoverAnimator.animateAll(send, 1, 1);
        HBox.setHgrow(clear, Priority.ALWAYS);
        HoverAnimator.animateAll(clear, 1, 1);

        ementaTextArea = new TextArea();
        ementaTextArea.setWrapText(true);
        ementaTextArea.setPromptText("Insira a ementa do processo aqui...");
        VBox.setVgrow(ementaTextArea, Priority.ALWAYS);

        ementaLabel = new Label("Inserção da ementa");
        VBox.setVgrow(ementaLabel, Priority.NEVER);

        form = new HBox();
        VBox.setVgrow(form, Priority.NEVER);

        analysisResultsMenu = new AnalysisResultsMenu();
        //analysisResultsMenu.layActiveContent("Argumento 1 ...", "Argumento 2 alsjdsaljdasl alksjdlakjd la alsjd", "Argumento 3", 0.62);
        //viewArguments.setDisable(false);
        // exemplo de adicionar analise acima
        // para tirar analise:
        analysisResultsMenu.layInactiveContent();
        viewArguments.setDisable(true);
        VBox.setVgrow(analysisResultsMenu.getContent(), Priority.ALWAYS);
        rightContent = new VBox();
        HBox.setHgrow(rightContent, Priority.ALWAYS);
        rightContent.getStyleClass().add("medium-spacing");
        leftContent = new VBox();
        leftContent.getStyleClass().add("medium-spacing");
        tabContent = new HBox();
        tabContent.getStyleClass().addAll("medium-padding", "medium-spacing");
        leftContent.prefWidthProperty().bind(tabContent.widthProperty().multiply(0.3));
    }

    @Override
    protected void layControls() {

        leftContent.getChildren().addAll(
                ementaLabel,
                ementaTextArea,
                new HGroup().withChildren(
                        SpacerFactory.hSpacer(Priority.ALWAYS),
                        clear,
                        SpacerFactory.hSpacer(Priority.ALWAYS),
                        send,
                        SpacerFactory.hSpacer(Priority.ALWAYS)
                )
        );

        rightContent.getChildren().addAll(
                analysisLabel,
                analysisResultsMenu.getContent(),
                new HGroup().withChildren(
                        SpacerFactory.hSpacer(Priority.ALWAYS),
                        viewArguments,
                        SpacerFactory.hSpacer(Priority.ALWAYS)
                )
        );

        tabContent.getChildren().addAll(
                leftContent, rightContent
        );
    }

    public Button getSend() {
        return send;
    }

    public Button getClear() {
        return clear;
    }

    public TextArea getEmenta() {
        return ementaTextArea;
    }

    public Button getViewArguments() {
        return viewArguments;
    }

    public AnalysisResultsMenu getAnalysisResultsMenu() {
        return analysisResultsMenu;
    }

    @Override
    public HBox getContent() {
        return tabContent;
    }
}
