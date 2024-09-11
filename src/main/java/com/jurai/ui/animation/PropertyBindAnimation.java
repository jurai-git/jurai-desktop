package com.jurai.ui.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PropertyBindAnimation extends BaseAnimation {
    public PropertyBindAnimation(PropertyBindPair start, PropertyBindPair end, Interpolator interpolator, double duration) {
        KeyValue startKeyValue = new KeyValue(start.p, start.b.getValue(), interpolator);
        KeyValue endKeyValue = new KeyValue(end.p, end.b.getValue(), interpolator);

        startKeyFrame = new KeyFrame(Duration.millis(duration), startKeyValue);
        endKeyFrame = new KeyFrame(Duration.millis(duration), endKeyValue);

        timeline = new Timeline(startKeyFrame, endKeyFrame);
    }
}
