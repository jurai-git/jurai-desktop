package io.jurai.ui.panes;

import io.jurai.ui.controls.SimpleList;
import io.jurai.ui.menus.RequerenteInfoMenu;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class DashboardPane extends AbstractPane {
    private BorderPane view;
    private SimpleList<?> requerentesList;
    private RequerenteInfoMenu requerenteInfoMenu;

    public DashboardPane() {
        super();
    }

    @Override
    protected void initControls() {
        view = new BorderPane();
        view.getStyleClass().add("content");
        requerentesList = new SimpleList<>("Requerentes");
        requerenteInfoMenu = new RequerenteInfoMenu();
    }

    @Override
    protected void layControls() {
        final Label l = new Label("Dashboard");
        l.getStyleClass().add("header-label");
        view.setTop(l);
        view.setCenter(requerentesList);
        view.setRight(requerenteInfoMenu.getContent());
    }

    @Override
    public BorderPane getView() {
        return view;
    }
}
