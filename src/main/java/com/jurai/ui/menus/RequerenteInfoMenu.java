package com.jurai.ui.menus;

import com.jurai.ui.controller.Controllable;
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
