package com.jurai.ui.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class BaseAnimation {
    protected KeyFrame startKeyFrame;
    protected KeyFrame endKeyFrame;
    protected Timeline timeline;


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
