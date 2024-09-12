package com.jurai.ui.controls;

import com.jurai.data.model.Model;
import com.jurai.ui.controller.Controllable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class SimpleListItem<T extends Model> extends HBox implements Controllable {
    private Label nameLabel;
    private Rectangle dot;
    private boolean selected;

    T object;

    public SimpleListItem(T object) {
        this.object = object;
        this.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 2, 2, 2))));
        selected = false;
        initControls();
        layControls();
    }

    private void initControls() {
        getStyleClass().add("list-item");
        nameLabel = new Label(object.nomeProperty().get());
        nameLabel.textProperty().bind(object.nomeProperty());

        dot = new Rectangle();
        dot.getStyleClass().add("dot");
        dot.setFill(Color.web("#666"));
        dot.arcWidthProperty().bind(dot.widthProperty());
        dot.arcHeightProperty().bind(dot.widthProperty());
        dot.translateYProperty().bind(nameLabel.heightProperty().multiply(0.2));
        dot.heightProperty().bind(nameLabel.heightProperty().multiply(0.6));
        dot.widthProperty().bind(dot.heightProperty().multiply(0.2));
    }

    private void layControls() {
        double em = Font.getDefault().getSize();
        System.out.println("em: " + em);
        VBox.setVgrow(this, Priority.NEVER);
        getChildren().addAll(dot, nameLabel);
    }

    private void initAnimations() {

    }


    //getters & setters


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if(selected) {
            dot.setFill(Color.web("#539CD4"));
        } else {
            dot.setFill(Color.web("#666"));
        }
    }

    public T getObject() {
        return object;
    }
}
