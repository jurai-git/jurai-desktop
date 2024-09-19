package com.jurai.ui.menus;

import com.jurai.data.model.Demanda;
import com.jurai.ui.controller.DemandaEditingController;
import com.jurai.ui.controller.DemandaRegisterController;
import com.jurai.ui.controls.SimpleList;
import com.jurai.ui.modal.DemandaEditingModal;
import com.jurai.ui.modal.DemandaRegisterModal;
import com.jurai.ui.modal.ModalManager;
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

    public DemandaDashboardMenu() {
        super();
        ModalManager.getInstance().registerModalFactory("demandaRegisterModal", () -> {
            DemandaRegisterModal modal = new DemandaRegisterModal();
            DemandaRegisterController modalController = new DemandaRegisterController();
            modalController.initialize(modal);
            return modal;
        }, DemandaRegisterModal.class);
        ModalManager.getInstance().registerModalFactory("demandaEditingModal", () -> {
            Demanda d = demandaList.getSelectedItem().getObject();
            DemandaEditingModal modal = new DemandaEditingModal(d);
            DemandaEditingController modalController = new DemandaEditingController();
            modalController.initialize(modal);
            return modal;
        }, DemandaEditingModal.class);

    }

    @Override
    protected void initControls() {
        content = new VBox();
        demandaList = new SimpleList<>("Demanda");
        form = new HBox();
        form.getStyleClass().add("buttons-row");
        addDemanda = new Button("Adicionar");
        addDemanda.setDisable(true);
        editDeleteDemanda = new Button("Visualizar/editar");
        editDeleteDemanda.setDisable(true);
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

    public SimpleList<Demanda> getDemandaList() {
        return demandaList;
    }

    public Button getAddDemanda() {
        return addDemanda;
    }

    public Button getEditDeleteDemanda() {
        return editDeleteDemanda;
    }

    @Override
    public VBox getContent() {
        return content;
    }
}
