package com.jurai.ui.menus;

import com.jurai.data.model.Requerente;
import com.jurai.ui.animation.DefaultButtonAnimator;
import com.jurai.ui.controller.Controllable;
import com.jurai.ui.controller.RequerenteModalController;
import com.jurai.ui.controls.SimpleList;
import com.jurai.ui.modal.ModalManager;
import com.jurai.ui.modal.RequerenteRegisterModal;
import com.jurai.ui.util.SpacerFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RequerenteDashboardMenu extends AbstractMenu<VBox> implements Controllable {
    private SimpleList<Requerente> requerentesList;
    private VBox content;
    private HBox form;
    private Button addRequerente, editDeleteRequerente;

    public RequerenteDashboardMenu() {
        super();
        ModalManager.getInstance().registerModalFactory("requerenteRegisterModal", () -> {
            RequerenteRegisterModal modal = new RequerenteRegisterModal();
            RequerenteModalController controller = new RequerenteModalController();
            controller.initialize(modal);
            return modal;
        });
    }

    @Override
    protected void initControls() {
        requerentesList = new SimpleList<>("Requerentes");
        content = new VBox();
        form = new HBox();
        form.getStyleClass().addAll("buttons-row");
        addRequerente = new Button("Adicionar requerente");
        DefaultButtonAnimator.animate(addRequerente);
        editDeleteRequerente = new Button("Editar requerente");
        DefaultButtonAnimator.animate(editDeleteRequerente);
    }

    @Override
    protected void layControls() {
        HBox.setHgrow(editDeleteRequerente, Priority.ALWAYS);
        HBox.setHgrow(addRequerente, Priority.ALWAYS);

        form.getChildren().addAll(
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                addRequerente,
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS),
                editDeleteRequerente,
                SpacerFactory.createHBoxSpacer(Priority.ALWAYS)
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

}
