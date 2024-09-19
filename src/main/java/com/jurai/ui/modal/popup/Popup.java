package com.jurai.ui.modal.popup;

import com.jurai.data.ApplicationState;
import com.jurai.ui.util.Pane;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;

public abstract class Popup extends Dialog<Void> {

    public Popup() {
        super();
        getDialogPane().getStyleClass().add("popup");
        setOnShown(e -> ApplicationState.getInstance().setCurrentPopup(this));
        setOnHidden(e -> ApplicationState.getInstance().setCurrentPopup(null));
    }

    public void setContent(Node content) {
        getDialogPane().setContent(content);
    }

}
