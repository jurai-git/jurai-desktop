package io.jurai.ui.panes.layout;

public class NodeConstraints {
    public float x;
    public float y;
    public float w;
    public float h;
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
}