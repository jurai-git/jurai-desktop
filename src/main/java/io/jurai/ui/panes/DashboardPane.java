package io.jurai.ui.panes;

import javafx.scene.layout.BorderPane;

public class DashboardPane extends AbstractPane {
    private BorderPane view;

    public DashboardPane() {
        super();
    }

    @Override
    protected void initControls() {
        view = new BorderPane();
        view.getStyleClass().add("content");
    }

    @Override
    protected void layControls() {
    }

    @Override
    public BorderPane getView() {
        return view;
    }
}
