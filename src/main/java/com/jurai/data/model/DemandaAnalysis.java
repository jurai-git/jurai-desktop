package com.jurai.data.model;

public class DemandaAnalysis {
    private double negative, partial, positive;
    private String predicted;

    public DemandaAnalysis() {}

    public DemandaAnalysis(double negative, double partial, double positive, String predicted) {
        this.negative = negative;
        this.partial = partial;
        this.positive = positive;
        this.predicted = predicted;
    }

    public double getNegative() {
        return negative;
    }

    public void setNegative(double negative) {
        this.negative = negative;
    }

    public double getPartial() {
        return partial;
    }

    public void setPartial(double partial) {
        this.partial = partial;
    }

    public double getPositive() {
        return positive;
    }

    public void setPositive(double positive) {
        this.positive = positive;
    }

    public String getPredicted() {
        return predicted;
    }

    public void setPredicted(String predicted) {
        this.predicted = predicted;
    }
}
