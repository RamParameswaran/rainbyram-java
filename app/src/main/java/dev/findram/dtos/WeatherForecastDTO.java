package dev.findram.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;


@Value
@AllArgsConstructor
@Builder
public class WeatherForecastDTO {
    double lat;
    double lon;
    String timezone;
    public HourlyForecastDTO[] hourly;
}


