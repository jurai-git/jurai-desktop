package com.jurai.ui.animation.interpolator;

import javafx.animation.Interpolator;

public class PowerEase extends Interpolator {
    private double strength;
    private boolean reverse;

    public PowerEase(double strength, boolean reverse) {
        this.reverse = reverse;
        this.strength = strength;
    }

    @Override
    protected double curve(double v) {
        if(!reverse) {
            return Math.pow(v, strength);
        } else {
            return 1 - Math.pow(1 - v, strength);
        }
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
}
