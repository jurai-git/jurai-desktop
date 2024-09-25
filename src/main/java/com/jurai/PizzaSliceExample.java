package com.jurai;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;

public class PizzaSliceExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Parameters for the circle segment
        double centerX = 200; // Center of the circle
        double centerY = 200; // Center of the circle
        double radius = 100;  // Radius of the circle
        double startAngle = 30;  // Start angle in degrees
        double endAngle = 120;   // End angle in degrees

        // Convert start and end angles to radians
        double startAngleRadians = Math.toRadians(startAngle);
        double endAngleRadians = Math.toRadians(endAngle);

        // Calculate start and end points of the arc
        double startX = centerX + radius * Math.cos(startAngleRadians);
        double startY = centerY + radius * Math.sin(startAngleRadians);
        double endX = centerX + radius * Math.cos(endAngleRadians);
        double endY = centerY + radius * Math.sin(endAngleRadians);

        // Create a Path
        Path path = new Path();
        path.setStroke(Color.BLUE);
        path.setStrokeWidth(8);
        path.setFill(null); // No fill to make it look like just the crust
        path.setStrokeLineCap(StrokeLineCap.ROUND); // Rounded ends
        path.setStrokeLineJoin(StrokeLineJoin.ROUND); // Rounded corners

        // Move to the start of the arc
        path.getElements().add(new MoveTo(startX, startY));

        // Draw the arc
        ArcTo arcTo = new ArcTo();
        arcTo.setX(endX);
        arcTo.setY(endY);
        arcTo.setRadiusX(radius);
        arcTo.setRadiusY(radius);
        arcTo.setSweepFlag(true); // Sweep in the clockwise direction
        path.getElements().add(arcTo);

        // Optionally, draw lines from the center to the start and end of the arc
        // Uncomment the lines below to include the sides of the pizza slice.
        // path.getElements().add(new LineTo(centerX, centerY)); // Line to the center
        // path.getElements().add(new MoveTo(endX, endY));       // Move to end of arc
        // path.getElements().add(new LineTo(centerX, centerY)); // Line to the center

        // Create a scene and add the path
        Scene scene = new Scene(new StackPane(path), 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pizza Slice Path Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
