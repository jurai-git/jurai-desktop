package com.jurai.ui.panes;

import com.jurai.data.ApplicationData;
import com.jurai.ui.controls.NavUrl;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class Navbar extends AbstractPane {
    private HBox view;
    private ImageView logo;
    private NavUrl navUrl;

    public Navbar() {
        super();
    }

    @Override
    protected void initControls() {
        view = new HBox();
        view.getStyleClass().add("navbar");

        logo = new ImageView();
        Image logoImg = new Image(getClass().getResource("/img/jurai-text-white-antialias-8.png").toExternalForm());
        logo.setImage(logoImg);
        logo.setSmooth(true);
        logo.setPreserveRatio(true);
        logo.fitHeightProperty().bind(view.heightProperty().multiply(0.5));

        navUrl = new NavUrl();
        navUrl.setUrl("/ dashboard / requerente ");
    }

    @Override
    protected void layControls() {
        view.getChildren().addAll(
                logo,
                SpacerFactory.createHBoxSpacer(12),
                navUrl
        );
        view.setAlignment(Pos.CENTER_LEFT);
    }

    public NavUrl getNavUrl() {
        return navUrl;
    }

    @Override
    public Pane getView() {
        return view;
    }
}
