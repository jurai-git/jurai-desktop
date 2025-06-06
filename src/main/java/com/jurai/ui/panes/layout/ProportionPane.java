package com.jurai.ui.panes.layout;

import com.jurai.ui.controls.ArrowToggleButton;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class ProportionPane extends Pane {


    private final Map<Node, NodeConstraints> constraintsMap = new HashMap<>();

    public void addConstraints(Node n, NodeConstraints c) {
        constraintsMap.put(n, c);
        requestLayout();
    }

    public void recalculateLayoutPosition(Node n) {
        NodeConstraints c = constraintsMap.get(n);
        n.relocate(getWidth() * c.xProperty.get(), getHeight() * c.yProperty.get());
    }

    public void recalculateLayoutSize(Node n) {
        NodeConstraints c = constraintsMap.get(n);
        n.resize(getWidth() * c.wProperty.get(), getHeight() * c.hProperty.get());
    }

    @Override
    protected void layoutChildren() {
        for(Node child : getChildren()) {
            if(!child.isManaged()) {
                continue;
            }

            NodeConstraints constraints = constraintsMap.get(child);

            double x = getWidth() * constraints.xProperty.get();
            double y = getHeight() * constraints.yProperty.get();

            if(constraints.anchor == NodeConstraints.Anchor.TOP_RIGHT) {
                double widthPropertyValue = (constraints.exclusiveWProperty.get() == -1) ?
                                constraints.wProperty.get() * getWidth() :
                                constraints.exclusiveWProperty.get();

                x = getWidth() - widthPropertyValue + getWidth() * constraints.xProperty.get();
            }

            double width = getWidth() * constraints.wProperty.get();
            double height = getHeight() * constraints.hProperty.get();

            if(constraints.exclusiveWProperty.get() != -1) {
                width = constraints.exclusiveWProperty.get();
            }

            child.relocate(x, y);
            child.resize(width, height);
        }
    }
}
