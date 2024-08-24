package com.jurai.ui.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SimplePropertyAnimation {
    protected KeyFrame startKeyFrame;
    protected KeyFrame endKeyFrame;
    protected Timeline timeline;

    protected SimplePropertyAnimation() {}

    public SimplePropertyAnimation(PropertyPair start, PropertyPair end, Interpolator interpolator, double duration) {
        KeyValue startKeyValue = new KeyValue(start.p1, start.p2, interpolator);
        KeyValue endKeyValue = new KeyValue(end.p1, end.p2, interpolator);

        startKeyFrame = new KeyFrame(Duration.millis(duration), startKeyValue);
        endKeyFrame = new KeyFrame(Duration.millis(duration), endKeyValue);

        timeline = new Timeline();
        timeline.playFromStart();
        timeline.setCycleCount(1);
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void playFromStart() {
        timeline.stop();
        timeline.getKeyFrames().setAll(startKeyFrame);
        timeline.play();
    }

    public void playFromEnd() {
        timeline.stop();
        timeline.getKeyFrames().setAll(endKeyFrame);
        timeline.play();
    }

    public void playFrom(boolean fromStart) {
        if(fromStart) {
            playFromStart();
        } else {
            playFromEnd();
        }
    }
}
