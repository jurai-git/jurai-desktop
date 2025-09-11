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
import com.jurai.ui.viewmodel.RequerenteDashboardMenuVM;
import dev.mgcvale.fluidfx.components.controls.FButton;
import dev.mgcvale.fluidfx.components.groups.HGroup;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RequerenteDashboardMenu extends VBox {
    private SimpleList<Requerente> requerentesList;
    private final RequerenteDashboardMenuVM vm;

    public RequerenteDashboardMenu(RequerenteDashboardMenuVM vm) {
        super();
        this.vm = vm;
        initControls();
        layControls();
    }

    protected void initControls() {
        requerentesList = new SimpleList<>("Requerentes");
        requerentesList.selectedItem().bindBidirectional(vm.selectedRequerente);
        Bindings.bindContentBidirectional(vm.requerentes, requerentesList.getListObjects());
    }

    protected void layControls() {
        VBox.setVgrow(requerentesList, Priority.ALWAYS);
        requerentesList.setMaxHeight(Double.MAX_VALUE);

        VBox.setVgrow(this, Priority.ALWAYS);
        setMaxHeight(Double.MAX_VALUE);
        getChildren().addAll(
            requerentesList,
            new HGroup().spaceAround().wStyleClass("buttons-row").wChildren(
                new FButton("Adicionar").onAction(e -> vm.onAdd()).applyCustomFunction(HoverAnimator::animateAll).hgrow(),
                new FButton("Visualizar/Editar").onAction(e -> vm.onEdit()).applyCustomFunction(HoverAnimator::animateAll).hgrow().inDisable(vm.selectedRequerente.isNull())
            )
        );
    }

}
