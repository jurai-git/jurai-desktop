package com.jurai.ui.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class CircleGraph extends StackPane {
    private double lineWidth;
    private double percentage;

    private Path path;
    private final Arc arc;
    private Rectangle base;
    private final Label percentageLabel;

    public CircleGraph(double lineWidth, double percentage) {
        this.lineWidth = lineWidth;
        getStyleClass().add("circle-graph");
        this.percentage = percentage;
        percentageLabel = new Label(String.format("%.1f%%", percentage * 100));
        path = new Path();
        arc = new Arc();
        initGraphics();
    }

    private void initGraphics() {
        this.widthProperty().addListener((observableValue, number, t1) -> redraw());
        this.heightProperty().addListener((observableValue, number, t1) -> redraw());
        redraw();
    }

    private void redraw() {
        double centerX = getWidth() / 2; // Center of the circle
        double centerY = getHeight() / 2; // Center of the circle
        double radius = Math.min(getWidth(), getHeight()) * 0.4;  // Radius of the circle
        double endAngle = percentage * 360;
        percentageLabel.setText(String.format("%.1f%%", percentage * 100));
        double endAngleRadians = Math.toRadians(endAngle - 90);
        /*
        This -90 has to be there because the trigonometric functions start in the
        right side of the cartesian plane. However, since we have to make it start from
        the top in our app, we need to subtract 90 degrees.
         */

        double startX = centerX;
        double startY = centerY - radius;
        double endX = centerX + radius * Math.cos(endAngleRadians);
        double endY = centerY + radius * Math.sin(endAngleRadians);
        
        // Create an Arc instead of using Path with ArcTo
        arc.setCenterX(centerX);
        arc.setCenterY(centerY);
        arc.setRadiusX(radius);
        arc.setRadiusY(radius);
        arc.setStartAngle(90);
        arc.setLength(-endAngle);
        arc.setFill(null);
        arc.setStroke(Color.rgb(83, 156, 212));
        arc.setStrokeWidth(lineWidth);
        arc.setType(ArcType.OPEN);
        arc.setStrokeLineCap(StrokeLineCap.ROUND);

        System.out.println(path.getElements());

        applyCss();
        layout();

        getChildren().clear();
        getChildren().setAll(arc, percentageLabel);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        redraw();
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
        layoutChildren();
    }

    public double getPercentage() {
        return percentage;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
        redraw();
    }

    public double getLineWidth() {
        return lineWidth;
    }
}
