package com.jurai.ui.panes.layout;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class NodeConstraints {

    public enum Anchor {
        TOP_LEFT,
        TOP_RIGHT;
    }

    public Anchor anchor = Anchor.TOP_LEFT;

    public DoubleProperty wProperty = new SimpleDoubleProperty(-1);
    public DoubleProperty hProperty = new SimpleDoubleProperty(-1);
    public DoubleProperty xProperty = new SimpleDoubleProperty(-1);
    public DoubleProperty yProperty = new SimpleDoubleProperty(-1);

    public DoubleProperty exclusiveXProperty = new SimpleDoubleProperty(-1);
    public DoubleProperty exclusiveYProperty = new SimpleDoubleProperty(-1);
    public DoubleProperty exclusiveWProperty = new SimpleDoubleProperty(-1);
    public DoubleProperty exclusiveHProperty = new SimpleDoubleProperty(-1);

    public NodeConstraints() {
        xProperty.set(0);
        yProperty.set(0);
        wProperty.set(0);
        hProperty.set(0);
    }

    public NodeConstraints(float x, float y, float w, float h) {
        this.xProperty.set(x);
        this.yProperty.set(y);
        this.wProperty.set(w);
        this.hProperty.set(h);
    }

}