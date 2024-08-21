package io.jurai.ui.panes;

import io.jurai.ui.menus.SidebarNav;
import io.jurai.ui.menus.controller.SidebarNavController;
import io.jurai.ui.util.SpacerFactory;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Sidebar extends AbstractPane {
    private VBox view;
    private ImageView logo;
    private Separator separator;
    private SidebarNav nav;

    public Sidebar() {
        super();
        attachControllers();
    }

    @Override
    protected void initControls() {
        view = new VBox();
        view.setAlignment(Pos.TOP_CENTER);
        view.getStyleClass().add("sidebar");

        logo = new ImageView();
        Image logoImg = new Image(getClass().getResource("/img/jurai-text-white-antialias.png").toExternalForm());
        logo.setImage(logoImg);
        logo.setSmooth(true);
        logo.setPreserveRatio(true);
        logo.fitWidthProperty().bind(view.widthProperty().multiply(0.7));

        separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        VBox.setVgrow(separator, Priority.NEVER);
        separator.getStyleClass().add("separator");

        nav = new SidebarNav();
        VBox.setVgrow(nav.getContent(), Priority.ALWAYS);
    }

    private void attachControllers() {
        SidebarNavController sidebarNavController = new SidebarNavController();
        sidebarNavController.initialize(nav);
    }

    @Override
    protected void layControls() {
        view.getChildren().addAll(
                logo,
                separator,
                nav.getContent()
                );
    }

    @Override
    public VBox getView() {
        return view;
    }
}
