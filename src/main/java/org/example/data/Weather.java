package org.example.data;

public enum Weather {
    Sunny(new double[]{1, 0, 0}),
    Rainy(new double[]{0, 1, 0}),
    Snowy(new double[]{0, 0, 1});

    private final double[] oneHotEncoding;

    Weather(double[] oneHotEncoding) {
        this.oneHotEncoding = oneHotEncoding;
    }

    public double[] getOneHotEncoding() {
        return oneHotEncoding;
    }
}
