package com.jurai.ui.menus;

import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class QuickQueryTab extends AbstractMenu<BorderPane> {
    private BorderPane tabContent;
    private HBox form, bottomRegion;
    private VBox oddsBox, argumentsBox;
    private BorderPane argumentsContainer;
    private Button send, openFileChooser, inputEmentaBtn;
    private FileChooser fileChooser;
    private Mode mode = Mode.PDF;

    private Label oddsResult, argumentsHeader, oddsHeader;
    private TextArea argument1, argument2, argument3;


    @Override
    protected void initControls() {
        send = new Button("Enviar");
        send.getStyleClass().add("blue-button");
        HBox.setHgrow(send, Priority.ALWAYS);
        HoverAnimator.animateAll(send, 1, 1);

        openFileChooser = new Button("Escolher documento");
        HBox.setHgrow(openFileChooser, Priority.ALWAYS);
        HoverAnimator.animateAll(openFileChooser, 1, 1);

        inputEmentaBtn = new Button("Inserir ementa");
        HoverAnimator.animateAll(inputEmentaBtn, 1, 1);
        fileChooser = new FileChooser();

        oddsResult = new Label("Nenhuma previsão");
        argumentsHeader = new Label("Argumentos:");
        oddsHeader = new Label("Previsão:");

        final Insets emptyInsets = new Insets(0, 0, 0, 0);
        argument1 = new TextArea("Argumento 1: Nenhum argumento");
        argument1.setEditable(false);
        argument1.setWrapText(true);
        argument2 = new TextArea("Argumento 2: Nenhum argumento");
        argument2.setEditable(false);
        argument2.setWrapText(true);
        argument3 = new TextArea("Argumento 3: Nenhum argumento");
        argument3.setEditable(false);
        argument3.setWrapText(true);

        argumentsBox = new VBox();
        argumentsContainer = new BorderPane();
        form = new HBox();
        form.getStyleClass().add("query-form");
        form.setSpacing(12);
        tabContent = new BorderPane();
        tabContent.getStyleClass().add("quick-query-tab");
        bottomRegion = new HBox();
        oddsBox = new VBox();

    }

    @Override
    protected void layControls() {
        setMode(Mode.PDF);
        form.setSpacing(12);
        tabContent.setTop(form);

        VBox.setVgrow(argument1, Priority.ALWAYS);
        VBox.setVgrow(argument2, Priority.ALWAYS);
        VBox.setVgrow(argument3, Priority.ALWAYS);
        argumentsBox.setSpacing(16);
        argumentsBox.getChildren().addAll(
                argument1,
                argument2,
                argument3
        );

        VBox.setVgrow(oddsHeader, Priority.NEVER);
        VBox.setVgrow(oddsResult, Priority.NEVER);
        oddsBox.setSpacing(16);
        oddsBox.getChildren().addAll(
                oddsHeader,
                oddsResult
        );

        argumentsContainer.setTop(argumentsHeader);
        argumentsContainer.setCenter(argumentsBox);

        HBox.setHgrow(argumentsContainer, Priority.ALWAYS);
        argumentsContainer.prefWidthProperty().bind(bottomRegion.widthProperty().multiply(0.6));
        HBox.setHgrow(oddsBox, Priority.ALWAYS);
        oddsBox.prefWidthProperty().bind(bottomRegion.widthProperty().multiply(0.4));
        bottomRegion.getChildren().addAll(
                oddsBox,
                argumentsContainer
        );

        tabContent.setCenter(bottomRegion);
    }

    @Override
    public BorderPane getContent() {
        return tabContent;
    }

    public void setMode(Mode newMode) {
        this.mode = newMode;
        switch(mode) {
            case PDF:
                layFormControlsAsPDF();
                break;
            case EMENTA:
                layFormControlsAsEmenta();
        }
    }

    private void layFormControlsAsPDF() {
         
	form.getChildren().removeAll(form.getChildren());
        form.getChildren().addAll(
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                openFileChooser,
                SpacerFactory.createHBoxSpacer(12),
                send,
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS)
        );
    }

    private void layFormControlsAsEmenta() {
        form.getChildren().removeAll(form.getChildren());
        form.getChildren().addAll(
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                inputEmentaBtn,
                SpacerFactory.createHBoxSpacer(12),
                send,
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS)
        );
    }

    public enum Mode {
        PDF,
        EMENTA
    }

}
