package com.jurai.ui.menus;

import com.jurai.ui.controls.CircleGraph;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class DemandaAnalysisDashboardMenu extends AbstractMenu<HBox> {
    private HBox content;
    private CircleGraph circleGraph;

    @Override
    protected void initControls() {
        content = new HBox();
        circleGraph = new CircleGraph(8, 0.1);
        HBox.setHgrow(circleGraph, Priority.ALWAYS);
        content.getChildren().add(circleGraph);
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Thread.sleep(100);
                    Platform.runLater(() -> {
                        double newPercentage = circleGraph.getPercentage() + 0.01;
                        if (newPercentage >= 1) {
                            newPercentage = 0.01;
                        }
                        circleGraph.setPercentage(newPercentage);
                    });
                }
            }
        };

        new Thread(task).start();
    }

    @Override
    protected void layControls() {

    }

    @Override
    public HBox getContent() {
        return content;
    }
}
