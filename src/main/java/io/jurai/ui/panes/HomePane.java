package io.jurai.ui.panes;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Objects;

public class HomePane extends AbstractPane {
    private VBox view;
    private Label sloganLbl;
    private ImageView logo;
    BorderPane imageViewWrapper;

    public HomePane() {
        super();
    }

    @Override
    protected void initControls() {
        view = new VBox();
        view.getStyleClass().add("main");
        view.setAlignment(Pos.CENTER);

        sloganLbl = new Label("A inteligência que facilita o processo");
        sloganLbl.getStyleClass().add("h1");

        logo = new ImageView();
        logo.setImage(new Image(
                Objects.requireNonNull(getClass().getResource("/img/horizontal_logo.png")).toExternalForm()
                ));
        logo.setSmooth(true);
        logo.setPreserveRatio(true);

        imageViewWrapper = new BorderPane(logo);
        imageViewWrapper.getStyleClass().add("image");
        imageViewWrapper.maxHeightProperty().bind(view.heightProperty().multiply(0.5));

        logo.fitHeightProperty().bind(imageViewWrapper.maxHeightProperty());
    }

    @Override
    protected void layControls() {
        view.getChildren().addAll(logo, sloganLbl);
    }

    @Override
    public VBox getView() {
        return view;
    }
}
