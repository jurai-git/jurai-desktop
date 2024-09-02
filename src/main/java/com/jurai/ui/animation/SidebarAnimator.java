package com.jurai.ui.animation;

import com.jurai.ui.animation.interpolator.PowerEase;
import com.jurai.ui.panes.Sidebar;
import com.jurai.ui.panes.layout.NodeConstraints;
import com.jurai.ui.panes.layout.ProportionPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;



public class SidebarAnimator {

    public static Timeline getSidebarAnimation(Sidebar n, NodeConstraints c, ProportionPane mainPane) {
        Timeline timeline = new Timeline();
        PowerEase ease = new PowerEase(2, true);
        KeyValue start = new KeyValue(c.exclusiveWProperty, n.initialWidthProperty().get(), ease);
        KeyValue end = new KeyValue(c.exclusiveWProperty, n.finalWidthProperty().get(), ease);

        KeyFrame startKeyFrame = new KeyFrame(Duration.ZERO, start);
        KeyFrame endKeyFrame = new KeyFrame(Duration.millis(450), end);

        timeline.getKeyFrames().addAll(
                startKeyFrame,
                endKeyFrame
        );

        timeline.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            mainPane.recalculateLayoutSize(n.getView());
        });

        return timeline;
    }

}
