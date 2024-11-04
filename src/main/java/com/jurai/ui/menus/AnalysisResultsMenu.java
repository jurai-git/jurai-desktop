package com.jurai.ui.menus;

import com.jurai.data.ApplicationState;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controls.CircleGraph;
import com.jurai.ui.controls.HGroup;
import com.jurai.ui.controls.VGroup;
import com.jurai.ui.util.ControlWrapper;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AnalysisResultsMenu extends AbstractMenu<StackPane> {
    private StackPane content, inactiveContent;
    private VBox activeContent, demandaInfo;
    private CircleGraph circleGraph;
    private Label circleGraphLabel, nome, inactiveLabel, argument1, argument2, argument3;
    private Button viewAllArguments;
    private boolean isActive = false;

    @Override
    protected void initControls() {
        content = new StackPane();
        inactiveContent = new StackPane();
        activeContent = new VBox();
        argument1 = new Label();
        VBox.setVgrow(argument1, Priority.ALWAYS);
        argument2 = new Label();
        VBox.setVgrow(argument2, Priority.ALWAYS);
        argument3 = new Label();
        VBox.setVgrow(argument3, Priority.ALWAYS);
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
        demandaInfo.getChildren().addAll(
                nome,
                argument1,
                argument2,
                argument3
        );

        HBox.setHgrow(circleGraph, Priority.ALWAYS);
        VBox.setVgrow(circleGraph, Priority.ALWAYS);
        VBox.setVgrow(demandaInfo, Priority.ALWAYS);

        setVertical();
        VBox.setVgrow(activeContent, Priority.ALWAYS);

        inactiveContent.getChildren().add(inactiveLabel);
        layInactiveContent();
        ApplicationState.getInstance().addPropertyChangeListener(propertyChangeEvent -> {
            if ("viewportSmall".equals(propertyChangeEvent.getPropertyName())) {
                if ((boolean) propertyChangeEvent.getNewValue()) {
                    setVertical();
                } else {
                    setHorizontal();
                }
            }
        });
    }

    public void layActiveContent(String arg1, String arg2, String arg3, double odds) {
        argument1.setText(arg1);
        argument2.setText(arg2);
        argument3.setText(arg3);
        circleGraph.setPercentage(odds);
        content.getChildren().setAll(activeContent);
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
