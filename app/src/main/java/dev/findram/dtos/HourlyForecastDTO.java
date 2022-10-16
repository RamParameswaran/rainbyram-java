package dev.findram.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HourlyForecastDTO {

    long dt;
    double temp;
    double feels_like;
    long pressure;
    long humidity;
    double dew_point;
    double uvi;
    ForecastDataPointDTO[] weather;
}
