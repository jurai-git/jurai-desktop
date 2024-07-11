package io.jurai.ui.controls.controller;

import io.jurai.ui.controls.AccountControl;

public class AccountControlController {

    public AccountControlController() {
    }

    public void initialize(AccountControl node) {
        attachEvents(node);
    }

    private void attachEvents(AccountControl node) {
        node.setOnMouseClicked(e -> {
            if(node.getPopOver().isShowing()) {
                node.getPopOver().hide();
                return;
            }
            node.getPopOver().show(node);
        });
    }

}
