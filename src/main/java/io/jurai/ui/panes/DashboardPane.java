package io.jurai.ui.panes;

import io.jurai.ui.controls.SimpleList;
import io.jurai.ui.menus.RequerenteInfoMenu;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardPane extends AbstractPane {
    private StackPane view;
    private BorderPane activeView;
    private VBox inactiveView;
    private Hyperlink loginHyperlink;
    private SimpleList<?> requerentesList;
    private RequerenteInfoMenu requerenteInfoMenu;

    public DashboardPane() {
        super();
    }

    @Override
    protected void initControls() {
        view = new StackPane();
        activeView = new BorderPane();
        inactiveView = new VBox();
        loginHyperlink = new Hyperlink("clique aqui para fazer log-in");
        inactiveView.getStyleClass().addAll("content", "vbox");
        inactiveView.setAlignment(Pos.TOP_CENTER);
        activeView.getStyleClass().addAll("content", "border-pane");
        requerentesList = new SimpleList<>("Requerentes");
        requerenteInfoMenu = new RequerenteInfoMenu();
    }

    @Override
    protected void layControls() {
        final Label dl = new Label("Dashboard");
        dl.getStyleClass().add("header");

        activeView.setTop(dl);
        activeView.setCenter(requerentesList);
        activeView.setRight(requerenteInfoMenu.getContent());

        final Label notLoggedHeader = new Label("Parece que você não está logado!");
        notLoggedHeader.getStyleClass().add("header");
        final Label notLoggedLabel = new Label("Para acessar essa página, você deverá fazer login.");
        inactiveView.getChildren().addAll(notLoggedHeader, notLoggedLabel, loginHyperlink);
    }

    public Hyperlink getLoginHyperlink() {
        return loginHyperlink;
    }

    public BorderPane getActiveView() {
        return activeView;
    }

    public VBox getInactiveView() {
        return inactiveView;
    }

    @Override
    public StackPane getView() {
        return view;
    }


}
