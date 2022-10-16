package dev.findram.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;


@AllArgsConstructor
@Builder
public class WeatherForecast {
    double lat;
    double lon;
    String timezone;
    public HourlyForecast[] hourly;
}


