package io.jurai.ui.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SimplePropertyBindAnimation extends SimplePropertyAnimation{
    public SimplePropertyBindAnimation(PropertyBindPair start, PropertyBindPair end, Interpolator interpolator, double duration) {
        super();

        KeyValue startKeyValue = new KeyValue(start.p, start.b.getValue(), interpolator);
        KeyValue endKeyValue = new KeyValue(end.p, end.b.getValue(), interpolator);

        startKeyFrame = new KeyFrame(Duration.millis(duration), startKeyValue);
        endKeyFrame = new KeyFrame(Duration.millis(duration), endKeyValue);

        timeline = new Timeline();
        timeline.playFromStart();
        timeline.setCycleCount(1);
    }
}
