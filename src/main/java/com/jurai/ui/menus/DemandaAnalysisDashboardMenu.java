package com.jurai.ui.menus;

import com.jurai.ui.controls.CircleGraph;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class DemandaAnalysisDashboardMenu extends AbstractMenu<HBox> {
    private HBox content;
    private CircleGraph circleGraph;
    private Label circleGraphLabel;

    @Override
    protected void initControls() {
        content = new HBox();
        circleGraph = new CircleGraph( 0.242);
        HBox.setHgrow(circleGraph, Priority.ALWAYS);
        content.getChildren().addAll(circleGraph);
    }

    @Override
    protected void layControls() {

    }

    @Override
    public HBox getContent() {
        return content;
    }
}
