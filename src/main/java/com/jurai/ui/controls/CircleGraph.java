package com.jurai.ui.controls;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class CircleGraph extends StackPane {
    private double percentage;

    private Circle base;
    private Arc percentageArc;
    private final Label percentageLabel;

    public CircleGraph(double percentage) {
        getStyleClass().add("circle-graph");
        this.percentage = percentage;
        percentageLabel = new Label(String.format("%.1f%%", percentage * 100));
        initGraphics();
    }

    private void initGraphics() {
        this.widthProperty().addListener((observableValue, number, t1) -> redraw());
        this.heightProperty().addListener((observableValue, number, t1) -> redraw());
        redraw();
        getChildren().setAll(percentageArc, percentageLabel);
    }

    private void redraw() {
        double centerX = getWidth() / 2; // Center of the circle
        double centerY = getHeight() / 2; // Center of the circle
        double radius = Math.min(getWidth(), getHeight()) * 0.4;  // Radius of the circle
        double endAngle = percentage * 360;
        percentageLabel.setText(String.format("%.1f%%", percentage * 100));
        
        // Create an Arc instead of using Path with ArcTo
        percentageArc = new Arc(centerX, centerY, radius, radius, 90, -endAngle);
        percentageArc.getStyleClass().add("percentage");
        percentageArc.setStroke(Color.rgb(83, 156, 212));
        percentageArc.setType(ArcType.OPEN);
        percentageArc.setStrokeLineCap(StrokeLineCap.ROUND);

        base = new Circle(centerX, centerY, radius);
        base.getStyleClass().add("base");

        getChildren().clear();
        getChildren().setAll(base, percentageArc, percentageLabel);
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
}
