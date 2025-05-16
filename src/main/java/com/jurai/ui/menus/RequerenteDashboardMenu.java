package com.jurai.ui.menus;

import com.jurai.data.model.Requerente;
import com.jurai.ui.animation.HoverAnimator;
import com.jurai.ui.controller.RequerenteEditingModalController;
import com.jurai.ui.controller.RequerenteRegisterModalController;
import com.jurai.ui.controls.SimpleList;
import com.jurai.ui.modal.ModalManager;
import com.jurai.ui.modal.RequerenteEditingModal;
import com.jurai.ui.modal.RequerenteRegisterModal;
import com.jurai.ui.util.SpacerFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RequerenteDashboardMenu extends AbstractMenu<VBox> {
    private SimpleList<Requerente> requerentesList;
    private VBox content;
    private HBox form;
    private Button addRequerente, editDeleteRequerente;

    public RequerenteDashboardMenu() {
        super();
        ModalManager.getInstance().registerModalFactory("requerenteRegisterModal", () -> {
            RequerenteRegisterModal modal = new RequerenteRegisterModal();
            RequerenteRegisterModalController controller = new RequerenteRegisterModalController();
            controller.initialize(modal);
            return modal;
        }, RequerenteRegisterModal.class);
        ModalManager.getInstance().registerModalFactory("requerenteEditingModal", () -> {
            Requerente r = requerentesList.getSelectedItem().getObject();
            RequerenteEditingModal modal = new RequerenteEditingModal(requerentesList.getSelectedItem().getObject());
            RequerenteEditingModalController controller = new RequerenteEditingModalController();
            controller.initialize(modal);
            return modal;
        }, RequerenteEditingModal.class);
    }

    @Override
    protected void initControls() {
        requerentesList = new SimpleList<>("Requerentes");
        content = new VBox();
        form = new HBox();
        form.getStyleClass().addAll("buttons-row");
        addRequerente = new Button("Adicionar");
        editDeleteRequerente = new Button("Visualizar/Editar");
        HoverAnimator.animateAll(1, 1, addRequerente, editDeleteRequerente);
    }

    @Override
    protected void layControls() {
        HBox.setHgrow(editDeleteRequerente, Priority.ALWAYS);
        HBox.setHgrow(addRequerente, Priority.ALWAYS);

        form.getChildren().addAll(
                SpacerFactory.hSpacer(Priority.ALWAYS),
                addRequerente,
                SpacerFactory.hSpacer(Priority.ALWAYS),
                editDeleteRequerente,
                SpacerFactory.hSpacer(Priority.ALWAYS)
        );

        VBox.setVgrow(form, Priority.NEVER);
        VBox.setVgrow(requerentesList, Priority.ALWAYS);

        content.getChildren().addAll(requerentesList, form);
    }

    @Override
    public VBox getContent() {
        return content;
    }

    public Button getAddRequerente() {
        return addRequerente;
    }

    public Button getEditDeleteRequerente() {
        return editDeleteRequerente;
    }

    public SimpleList<Requerente> getRequerentesList() {
        return requerentesList;
    }
}
