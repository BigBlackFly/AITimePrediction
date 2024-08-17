package org.example.data;

public enum Weekday {
    Monday(new double[]{1, 0, 0, 0, 0}),
    Tuesday(new double[]{0, 1, 0, 0, 0}),
    Wednesday(new double[]{0, 0, 1, 0, 0}),
    Thursday(new double[]{0, 0, 0, 1, 0}),
    Friday(new double[]{0, 0, 0, 0, 1});

    private final double[] oneHotEncoding;

    Weekday(double[] oneHotEncoding) {
        this.oneHotEncoding = oneHotEncoding;
    }

    public double[] getOneHotEncoding() {
        return oneHotEncoding;
    }
}
