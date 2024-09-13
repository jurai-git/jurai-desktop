package com.jurai.ui.modal;

import com.jurai.ui.controls.BasicTabbedPane;
import javafx.scene.layout.BorderPane;

public class DemandaModal extends Modal<BasicTabbedPane> {


    public DemandaModal(String name) {
        super(name);
        System.out.println("creating generic demandaModal");
    }

    @Override
    protected void initControls() {

    }

    @Override
    protected void layControls() {

    }

    @Override
    public BasicTabbedPane getContent() {
        return null;
    }
}
