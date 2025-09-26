package com.jurai.ui.viewmodel;

import com.jurai.data.model.Requerente;
import com.jurai.ui.controller.RequerenteEditingModalController;
import com.jurai.ui.controller.RequerenteRegisterModalController;
import com.jurai.ui.controls.SimpleListItem;
import com.jurai.ui.modal.ModalManager;
import com.jurai.ui.modal.RequerenteEditingModal;
import com.jurai.ui.modal.RequerenteRegisterModal;
import com.jurai.util.UILogger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RequerenteDashboardMenuVM extends ViewModelBase {
    public ObservableList<Requerente> requerentes = FXCollections.observableArrayList();
    public ObjectProperty<SimpleListItem<Requerente>> selectedRequerente = new SimpleObjectProperty<>(null);

    public RequerenteDashboardMenuVM() {
        ModalManager.getInstance().registerModalFactory("requerenteRegisterModal", () -> {
            RequerenteRegisterModal modal = new RequerenteRegisterModal();
            RequerenteRegisterModalController controller = new RequerenteRegisterModalController();
            controller.initialize(modal);
            return modal;
        }, RequerenteRegisterModal.class);

        ModalManager.getInstance().registerModalFactory("requerenteEditingModal", () -> {
            if (selectedRequerente.get() == null) {
                UILogger.logError("Couldn't initialize requerenteEditingModal because selected requerente was null. Continuing with null modal.");
                return null;
            }
            RequerenteEditingModal modal = new RequerenteEditingModal(selectedRequerente.get().getObject());
            RequerenteEditingModalController controller = new RequerenteEditingModalController();
            controller.initialize(modal);
            return modal;
        }, RequerenteEditingModal.class);

        selectedRequerente.addListener((observableValue, requerenteSimpleListItem, t1) -> {
            appState.setSelectedRequerente(t1 == null ? null : t1.getObject());
        });

        appState.currentUserProperty().addListener((obs, ov, nv) -> {
            if (nv == null && ov != null) { // we had a binding, but don't anymore
                Bindings.unbindContentBidirectional(requerentes, ov.getRequerentes());
            }
            if (nv == null) return;

            Bindings.bindContentBidirectional(nv.getRequerentes(), requerentes);
        });
    }

    public void onAdd() {
        ModalManager.getInstance().requestModal("requerenteRegisterModal");
    }

    public void onEdit() {
        ModalManager.getInstance().requestModal("requerenteEditingModal");
    }
}
