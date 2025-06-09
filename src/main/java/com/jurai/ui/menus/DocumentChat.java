package com.jurai.ui.menus;

import javafx.scene.layout.VBox;

public class DocumentChat extends AbstractMenu<VBox> {
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
