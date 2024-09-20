package com.jurai.ui.animation.interpolator;

import javafx.animation.Interpolator;

public class SmoothEase extends Interpolator {
    private final double strength;

    public SmoothEase(double strength) {
        this.strength = strength;
    }

    @Override
    protected double curve(double t) {
        return Math.pow(t, strength) / (Math.pow(t, strength) + Math.pow(1 - t, strength));
    }
}
