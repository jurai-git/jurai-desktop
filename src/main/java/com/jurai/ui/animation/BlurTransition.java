package com.jurai.ui.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.util.Duration;

public class BlurTransition extends BaseAnimation {
    public BlurTransition(double duration, Effect from, Effect to, Interpolator interpolator, Node node) {
        KeyValue startKeyValue = new KeyValue(node.effectProperty(), from, interpolator);
        KeyValue endKeyValue = new KeyValue(node.effectProperty(), to, interpolator);

        startKeyFrame = new KeyFrame(Duration.millis(duration), startKeyValue);
        endKeyFrame = new KeyFrame(Duration.millis(duration), endKeyValue);

        timeline = new Timeline(startKeyFrame, endKeyFrame);
    }
}
