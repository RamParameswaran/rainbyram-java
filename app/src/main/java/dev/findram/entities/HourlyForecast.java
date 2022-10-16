package dev.findram.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HourlyForecast {

    long dt;
    double temp;
    double feels_like;
    long pressure;
    long humidity;
    double dew_point;
    double uvi;
    ForecastDataPoint[] weather;
}
