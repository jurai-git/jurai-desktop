package io.jurai.ui.panes;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class Navbar extends AbstractPane {
    private HBox view;

    public Navbar() {
        super();
    }

    @Override
    protected void initControls() {
        view = new HBox();
        view.getStyleClass().add("navbar");
    }

    @Override
    protected void layControls() {
    }
    @Override
    public Pane getView() {
        return view;
    }
}
