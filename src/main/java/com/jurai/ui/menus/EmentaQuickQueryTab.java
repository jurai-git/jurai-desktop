package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.util.SpacerFactory;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

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
        analysisResultsMenu.layActiveContent("Argumento 1 ...", "Argumento 2 alsjdsaljdasl alksjdlakjd la alsjd", "Argumento 3", 0.62);
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
                        SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                        clear,
                        SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                        send,
                        SpacerFactory.createHBoxSpacer(Priority.ALWAYS)
                )
        );

        rightContent.getChildren().addAll(
                analysisLabel,
                analysisResultsMenu.getContent(),
                new HGroup().withChildren(
                        SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                        viewArguments,
                        SpacerFactory.createHBoxSpacer(Priority.ALWAYS)
                )
        );

        tabContent.getChildren().addAll(
                leftContent, rightContent
        );
    }

    public AnalysisResultsMenu getAnalysisResultsMenu() {
        return analysisResultsMenu;
    }

    @Override
    public HBox getContent() {
        return tabContent;
    }
}
