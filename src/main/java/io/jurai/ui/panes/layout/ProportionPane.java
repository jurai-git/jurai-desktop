package io.jurai.ui.panes.layout;

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

    @Override
    protected void layoutChildren() {

        for(Node child : getChildren()) {
            NodeConstraints constraints = constraintsMap.get(child);

            if(constraints == null) constraints = new NodeConstraints();

            double x = getWidth() * constraints.x;
            double y = getHeight() * constraints.y;
            double width = getWidth() * constraints.w;
            double height = getHeight() * constraints.h;

            child.relocate(x, y);
            child.resize(
                    Math.max(width, constraints.minWidth.get()),
                    Math.max(height, constraints.minHeight.get())
            );
        }
    }
}
