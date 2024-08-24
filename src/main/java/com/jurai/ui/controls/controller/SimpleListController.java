package com.jurai.ui.controls.controller;

import com.jurai.data.model.Model;
import com.jurai.ui.controller.AbstractController;
import com.jurai.ui.controls.SimpleList;
import com.jurai.ui.controls.SimpleListItem;
import javafx.collections.ListChangeListener;

public class SimpleListController extends AbstractController<SimpleList<? extends Model>> {

    @Override
    protected void attachEvents(SimpleList<? extends Model> pane) {

        // list syncronizer
        pane.getListItems().addListener((ListChangeListener<Model>) change -> {
            while(change.next()) {
                if(change.wasAdded()) {
                    change.getAddedSubList().forEach(item -> {
                        pane.getListItemsContainer().getChildren().add(new SimpleListItem<>(item));
                    });
                }
                if(change.wasRemoved()) {
                    change.getRemoved().forEach(item -> {
                        pane.getListItemsContainer().getChildren().removeIf(node -> {
                            return ((SimpleListItem<? extends Model>) node).getObject().equals(item);
                        });
                    });
                }
            }
        });
    }

    @Override
    protected void attachNotifiers(SimpleList<? extends Model> pane) {
    }
}
