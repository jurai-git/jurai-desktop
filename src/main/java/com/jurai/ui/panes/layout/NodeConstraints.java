package com.jurai.ui.panes.layout;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

public class NodeConstraints {
    public float x;
    public float y;
    public float w;
    public float h;
    public FloatProperty minWidth = new SimpleFloatProperty(0);
    public FloatProperty minHeight = new SimpleFloatProperty(0);

    public NodeConstraints() {
        x = 0;
        y = 0;
        w = 0;
        h = 0;
    }
    public NodeConstraints(float x, float y, float w, float h) {
        this.x = x;
        this.y =y ;
        this.w = w;
        this.h = h;
    }

    public NodeConstraints(float x, float y, float w, float h, FloatProperty minWidth, FloatProperty minHeight) {
        this(x, y, w, h);
        this.minHeight = minHeight;
        this.minWidth = minWidth;
    }
}