package com.jurai.ui.controls;

import com.jurai.data.ApplicationState;
import com.jurai.ui.util.ImageUtils;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ProfilePicture extends StackPane {
    private ImageView iv;
    private final Image imagePlusImgLight, imagePlusImgDark;
    private final ImageView imagePlus;
    private final Circle imageCircle;
    private final Color imageCircleBgLight, imageCircleBgDark;

    public ProfilePicture() {
        super();
        iv = new ImageView();
        getChildren().add(iv);

        imagePlus = new ImageView();
        imagePlusImgLight = new Image(getClass().getResource("/img/image-plus-light.png").toExternalForm());
        imagePlusImgDark = new Image(getClass().getResource("/img/image-plus-dark.png").toExternalForm());
        imagePlus.setImage(ApplicationState.getInstance().isUseLightTheme() ? imagePlusImgLight : imagePlusImgDark); // default to theme while the user doesn't choose an image

        imagePlus.fitWidthProperty().bind(iv.fitWidthProperty().multiply(0.25));
        imagePlus.setPreserveRatio(true);
        imagePlus.setSmooth(true);
        imagePlus.setCursor(Cursor.HAND);

        imageCircle = new Circle();
        imageCircle.centerXProperty().bind(imagePlus.fitWidthProperty().multiply(0.6));
        imageCircle.centerYProperty().bind(imagePlus.fitHeightProperty().multiply(0.6));
        imageCircle.radiusProperty().bind(imagePlus.fitWidthProperty().multiply(1.2));
        imageCircleBgDark = Color.rgb(255, 255, 255, 0.3);
        imageCircleBgLight = Color.rgb(0, 0, 0, 0.3);
        imageCircle.setFill(ApplicationState.getInstance().isUseLightTheme() ? imageCircleBgLight : imageCircleBgDark);
        imageCircle.setCursor(Cursor.HAND);

        // TODO: add nice enter and exit animation
        setOnMouseEntered(e -> getChildren().addAll(imageCircle, imagePlus));
        setOnMouseExited(e -> getChildren().removeAll(imageCircle, imagePlus));
    }

    public void setFitWidth(double w) {
        iv.setFitWidth(w);
    }

    public void setFitHeight(double h) {
        iv.setFitHeight(h);
    }

    public void setImage(Image img) {
        if (img == null) return;

        if (img.getPixelReader() == null) {
            img.progressProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.doubleValue() == 1.0 && img.getPixelReader() != null) {
                    double luminance = ImageUtils.computeCenterLuminance(img);
                    System.out.println("LUMINANCE: " + luminance);
                    imagePlus.setImage(luminance > 0.6 ? imagePlusImgLight : imagePlusImgDark);
                    imageCircle.setFill(luminance > 0.6 ? imageCircleBgLight : imageCircleBgDark);
                }
            });
        } else {
            double luminance = ImageUtils.computeCenterLuminance(img);
            imagePlus.setImage(luminance > 0.6 ? imagePlusImgLight : imagePlusImgDark);
            imageCircle.setFill(luminance > 0.6 ? imageCircleBgLight : imageCircleBgDark);
        }

        iv.setImage(img);
    }

    public void setImageViewClip(Node n) {
        iv.setClip(n);
    }

    public void setPreserveRatio(boolean preserve) {
        iv.setPreserveRatio(preserve);
    }

    public void setSmooth(boolean smooth) {
        iv.setSmooth(smooth);
    }

    public ImageView getImageView() {
        return iv;
    }

    public void setOnAction(EventHandler<? super MouseEvent> event) {
        setOnMouseClicked(event);
    }
}
