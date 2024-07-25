package io.jurai.ui.menus;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class AccountDashboardMenu extends AbstractMenu {
    private BorderPane content;

    @Override
    protected void initControls() {
        content = new BorderPane();
    }

    @Override
    protected void layControls() {

    }

    public void infoUpdated() {

    }

    @Override
    public Node getContent() {
        return content;
    }
}
