package com.jurai.ui.panes;

import com.jurai.data.AppState;
import com.jurai.ui.controls.NavUrl;
import com.jurai.ui.util.SpacerFactory;
import com.jurai.util.EventLogger;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import lombok.Getter;

public class Header extends AbstractPane {
    private HBox view;
    private ImageView logo;
    private Image darkLogo, lightLogo;

    @Getter
    private ImageView pfp;

    @Getter
    private NavUrl navUrl;

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

        pfp = new ImageView();
        pfp.setCursor(Cursor.HAND);
        pfp.setSmooth(true);
        pfp.setPreserveRatio(true);
        pfp.fitHeightProperty().bind(view.heightProperty().multiply(0.6));
        Tooltip pfpToolTip = new Tooltip("Acessar sua conta");
        Tooltip.install(pfp, pfpToolTip);

        Circle clip = new Circle();
        clip.radiusProperty().bind(pfp.fitHeightProperty().multiply(0.5));
        clip.centerXProperty().bind(pfp.fitHeightProperty().multiply(0.5));
        clip.centerYProperty().bind(pfp.fitHeightProperty().multiply(0.5));
        pfp.setClip(clip);


        navUrl = new NavUrl();
        navUrl.setUrl("/ dashboard / requerente ");
    }

    @Override
    protected void layControls() {
        view.getChildren().addAll(
                logo,
                SpacerFactory.hSpacer(12),
                navUrl,
                SpacerFactory.hSpacer(Priority.ALWAYS),
                pfp
        );
        view.setAlignment(Pos.CENTER_LEFT);
    }

    public void updatePfp(String url) {
        if (url == null || url.isEmpty()) {
            loadFallback();
            return;
        }
        EventLogger.log("Loading profile picture with URL " + url + " on Header");

        ChangeListener<Boolean> errorHandler = (obs, oldVal, hasError) -> {
            if (hasError) {
                EventLogger.logWarning("No image found for current user, loading default user image on Header");
                loadFallback();
            }
        };
        Image img = new Image(url, true);
        img.errorProperty().addListener(errorHandler);
        pfp.setImage(img);
    }

    public void loadFallback() {
        Image img = new Image(getClass().getResource(AppState.get().getFallbackPfpPath()).toExternalForm());
        pfp.setImage(img);
    }

    @Override
    public Pane getView() {
        return view;
    }

    public void themeChanged() {
        if (AppState.get().isUseLightTheme()) {
            logo.setImage(lightLogo);
        } else {
            logo.setImage(darkLogo);
        }
    }
}
