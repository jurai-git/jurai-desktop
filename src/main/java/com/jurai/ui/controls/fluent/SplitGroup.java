package com.jurai.ui.controls.fluent;

import com.jurai.util.UILogger;
import dev.mgcvale.fluidfx.components.core.FluidControl;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.SplitPane;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class SplitGroup extends SplitPane implements FluidControl<SplitGroup> {
    Map<Integer, Pair<Double, Double>> positionConstraints;

    @Override
    public Control getSelf() {
        return this;
    }

    public SplitGroup() {
        super();
        positionConstraints = new HashMap<>();
    }

    public SplitGroup wDividerPosition(int i, double pos) {
        setDividerPosition(i, pos);
        positionConstraints.put(i, new Pair<>(0D, 1D));
        try {
            getDividers().get(i).positionProperty().addListener((obs, oldVal, newVal) -> {
                Pair<Double, Double> thisConstraints = positionConstraints.get(i);
                double clamped = Math.max(thisConstraints.getKey(), Math.min(thisConstraints.getValue(), newVal.doubleValue()));
                if (clamped != newVal.doubleValue()) {
                    Platform.runLater(() -> setDividerPositions(clamped));
                }
            });
        } catch (Exception e) {
            UILogger.logWarning("Failed to set divider position on Split Group: " + e.getMessage());
        }
        return this;
    }

    public SplitGroup wConstraints(int i, Pair<Double, Double> constraints) {
        positionConstraints.put(i, constraints);
        try {
            calculateConstraints(i, getDividers().get(i).getPosition());
        } catch (Exception e) {
            UILogger.logWarning("Failed to add calculate constraint on SplitGroup.wConstraints(): " + e.getMessage());
        }
        return this;
    }

    private void calculateConstraints(int i, double val) {
        try {
            Pair<Double, Double> thisConstraints = positionConstraints.get(i);
            double clamped = Math.max(thisConstraints.getKey(), Math.min(thisConstraints.getValue(), val));
            if (clamped != val) {
                Platform.runLater(() -> setDividerPositions(clamped));
            }
        } catch (Exception e) {
            UILogger.logWarning("Failed to calculate constraints on Split Group: " + e.getMessage());
        }
    }

    public SplitGroup wItems(Node... items) {
        getItems().addAll(items);
        return this;
    }

    public SplitGroup wOrientation(Orientation o) {
        setOrientation(o);
        return this;
    }
}
