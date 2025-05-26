package com.jurai.ui.panes;

import com.jurai.data.ApplicationState;
import com.jurai.ui.controls.NavUrl;
import com.jurai.ui.util.SpacerFactory;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;

public class Header extends AbstractPane {
    private HBox view;
    private ImageView logo;

    @Getter
    private NavUrl navUrl;

    private Image darkLogo, lightLogo;

    public Header() {
        super();
    }

    @Override
    protected void initControls() {
        view = new HBox();
        view.getStyleClass().add("navbar");

        darkLogo = new Image(getClass().getResource("/img/jurai-text-white-antialias-8.png").toExternalForm());
        lightLogo = new Image(getClass().getResource("/img/jurai-text-dark-antialias-8.png").toExternalForm());

        logo = new ImageView();
        themeChanged();
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
                SpacerFactory.hSpacer(12),
                navUrl
        );
        view.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    public Pane getView() {
        return view;
    }

    public void themeChanged() {
        if (ApplicationState.getInstance().isUseLightTheme()) {
            logo.setImage(darkLogo);
        } else {
            logo.setImage(lightLogo);
        }
    }
}
