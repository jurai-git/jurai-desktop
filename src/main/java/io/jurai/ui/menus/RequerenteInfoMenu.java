package io.jurai.ui.menus;

import io.jurai.ui.controller.Controllable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class RequerenteInfoMenu extends AbstractMenu implements Controllable {

    private VBox content;

    @Override
    protected void initControls() {
        content = new VBox();
    }

    @Override
    protected void layControls() {

    }

    @Override
    public VBox getContent() {
        return content;
    }
}
