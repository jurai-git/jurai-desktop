package io.jurai.ui.controls.controller;

import io.jurai.data.model.Model;
import io.jurai.ui.controller.AbstractController;
import io.jurai.ui.controls.SimpleList;
import io.jurai.ui.controls.SimpleListItem;
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
