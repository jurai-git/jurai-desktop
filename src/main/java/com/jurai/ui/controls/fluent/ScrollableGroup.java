package com.jurai.ui.controls.fluent;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class ScrollableGroup extends ScrollPane implements FluentControl<ScrollableGroup> {

    @Override
    public Control getSelf() {
        return this;
    }

    public ScrollableGroup() {
        super();
    }

    public ScrollableGroup hbarPolicy(ScrollBarPolicy policy) {
        setHbarPolicy(policy);
        return this;
    }

    public ScrollableGroup vbarPolicy(ScrollBarPolicy policy) {
        setVbarPolicy(policy);
        return this;
    }

    public ScrollableGroup withContent(Node content) {
        setContent(content);
        return this;
    }

    public ScrollableGroup withFixedWitdh() {
        setFitToWidth(true);
        return this;
    }


}
