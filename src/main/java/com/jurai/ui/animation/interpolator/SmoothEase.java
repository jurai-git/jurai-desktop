package com.jurai.ui.animation.interpolator;

import javafx.animation.Interpolator;

public class SmoothEase extends Interpolator {

    @Override
    protected double curve(double t) {
        return (Math.sin(t * Math.PI - 1.5) + 1) / 2;
    }
}
