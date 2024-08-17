package org.example.data;

import java.util.List;

public class DataProvider {

    public static List<DataBean> provideData() {
        return List.of(
                new DataBean(Weather.Sunny, Weekday.Monday, 17.1),
                new DataBean(Weather.Sunny, Weekday.Tuesday, 17.2),
                new DataBean(Weather.Sunny, Weekday.Wednesday, 17.3),
                new DataBean(Weather.Sunny, Weekday.Thursday, 16.7),
                new DataBean(Weather.Sunny, Weekday.Friday, 18.1),

                new DataBean(Weather.Rainy, Weekday.Monday, 18.1),
                new DataBean(Weather.Rainy, Weekday.Tuesday, 18.2),
                new DataBean(Weather.Rainy, Weekday.Wednesday, 18.3),
                new DataBean(Weather.Rainy, Weekday.Thursday, 17.7),
                new DataBean(Weather.Rainy, Weekday.Friday, 19.1),

                new DataBean(Weather.Snowy, Weekday.Monday, 19.1),
                new DataBean(Weather.Snowy, Weekday.Tuesday, 19.2),
                new DataBean(Weather.Snowy, Weekday.Wednesday, 19.3),
                new DataBean(Weather.Snowy, Weekday.Thursday, 18.7),
                new DataBean(Weather.Snowy, Weekday.Friday, 20.1)
                // and more data...
        );
    }
}