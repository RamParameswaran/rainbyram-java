package dev.findram.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WeatherForecastDTO {
    double lat;
    double lon;
    String timezone;
    HourlyForecast[] hourly;

    @Data
    @AllArgsConstructor
    @Builder
    public static class HourlyForecast {

        long dt;
        double temp;
        double feels_like;
        long pressure;
        long humidity;
        double dew_point;
        double uvi;
        WeatherData[] weather;
    }


    @Data
    @AllArgsConstructor
    @Builder
    public static class WeatherData {
        long id;
        String main;
        String description;
    }
}
