package com.jurai.ui.menus;

import com.jurai.data.model.Demanda;
import com.jurai.ui.controls.SimpleList;
import com.jurai.ui.util.SpacerFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DemandaDashboardMenu extends AbstractMenu<VBox> {

    private VBox content;
    private HBox form;
    private Button addDemanda, editDeleteDemanda;
    private SimpleList<Demanda> demandaList;


    @Override
    protected void initControls() {
        content = new VBox();
        demandaList = new SimpleList<>("Demanda");
        form = new HBox();
        form.getStyleClass().add("buttons-row");
        addDemanda = new Button("Adicionar");
        editDeleteDemanda = new Button("Vizualizar/editar");
    }

    @Override
    protected void layControls() {
        HBox.setHgrow(addDemanda, Priority.ALWAYS);
        HBox.setHgrow(editDeleteDemanda, Priority.ALWAYS);
        form.getChildren().addAll(
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                addDemanda,
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                editDeleteDemanda,
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS)
        );

        VBox.setVgrow(form, Priority.NEVER);
        VBox.setVgrow(demandaList, Priority.ALWAYS);
        content.getChildren().addAll(demandaList, form);
    }

    @Override
    public VBox getContent() {
        return content;
    }
}
