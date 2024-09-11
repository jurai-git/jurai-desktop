package com.jurai.ui.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PropertyValueAnimation<T> extends BaseAnimation{
    public PropertyValueAnimation(PropertyValuePair<T> start, PropertyValuePair<T> end, Interpolator interpolator, double duration) {
        super();

        KeyValue startKeyValue = new KeyValue(start.property, start.value, interpolator);
        KeyValue endKeyValue = new KeyValue(end.property, end.value, interpolator);

        startKeyFrame = new KeyFrame(Duration.ZERO, startKeyValue);
        endKeyFrame = new KeyFrame(Duration.millis(duration), endKeyValue);

        timeline = new Timeline(startKeyFrame, endKeyFrame);
    }
}