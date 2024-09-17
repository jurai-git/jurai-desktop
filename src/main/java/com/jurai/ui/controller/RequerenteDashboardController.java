package com.jurai.ui.controller;

import com.jurai.data.ApplicationState;
import com.jurai.data.model.Requerente;
import com.jurai.ui.controls.SimpleList;
import com.jurai.ui.controls.SimpleListItem;
import com.jurai.ui.menus.RequerenteDashboardMenu;
import com.jurai.ui.modal.ModalManager;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TextField;

public class RequerenteDashboardController extends AbstractController<RequerenteDashboardMenu>  {
    @Override
    protected void attachEvents(RequerenteDashboardMenu pane) {
        pane.getAddRequerente().setOnAction(e -> {
            ModalManager.getInstance().requestModal("requerenteRegisterModal");
        });
        pane.getEditDeleteRequerente().setOnAction(e -> {
            ModalManager.getInstance().requestModal("requerenteEditingModal");
        });

        pane.getRequerentesList().addSelectedItemListener((observableValue, oldValue, newValue) -> {
            ApplicationState.setSelectedRequerente(newValue == null ? null : newValue.getObject());
            if(newValue == null) {
                pane.getEditDeleteRequerente().setDisable(true);
            } else {
                pane.getEditDeleteRequerente().setDisable(false);
            }
        });

        TextField searchTextField = pane.getRequerentesList().getSearchTextField();
        searchTextField.setOnKeyTyped(event -> {
            String search = searchTextField.getText().toLowerCase();
            pane.getRequerentesList().getListItemsContainer().getChildren().forEach(listItem -> {
                if (listItem instanceof SimpleListItem<?> casted) {
                    if (casted.getObject() instanceof Requerente castedRequerente) {
                        boolean matches = castedRequerente.nomeProperty().get().toLowerCase().contains(search);
                        listItem.setVisible(matches);
                        listItem.setManaged(matches);
                    }
                }
            });
        });
    }

    @Override
    protected void attachNotifiers(RequerenteDashboardMenu pane) {
        ApplicationState.addPropertyChangeListener(propertyChangeEvent -> {
            if("currentUser".equals(propertyChangeEvent.getPropertyName())) {
                if(ApplicationState.getCurrentUser() != null) {
                    bindRequerenteList(pane.getRequerentesList());
                }
            }
        });
    }

    private void bindRequerenteList(SimpleList<Requerente> listPane) {
        Bindings.bindContent(listPane.getListObjects(), ApplicationState.getCurrentUser().getRequerentes());
        ApplicationState.getCurrentUser().getRequerentes().addListener((ListChangeListener<Requerente>) change -> {
            listPane.getSearchTextField().clear();
        });
    }
}
