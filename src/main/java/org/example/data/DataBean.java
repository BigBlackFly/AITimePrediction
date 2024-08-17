package org.example.data;

public class DataBean {

    public final Weather weather;
    public final Weekday weekday;
    public final double homeTime;

    public DataBean(Weather weather, Weekday weekday, double homeTime) {
        this.weekday = weekday;
        this.weather = weather;
        this.homeTime = homeTime;
    }
}
