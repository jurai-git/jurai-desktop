package com.jurai.ui.panes;

import javafx.scene.layout.BorderPane;

public class PlanPane extends AbstractPane {
    private BorderPane view;

    public PlanPane() {
        super();
    }

    @Override
    protected void initControls() {
        view = new BorderPane();
        view.getStyleClass().add("pane");
    }

    @Override
    protected void layControls() {

    }

    @Override
    public BorderPane getView() {
        return view;
    }
}
