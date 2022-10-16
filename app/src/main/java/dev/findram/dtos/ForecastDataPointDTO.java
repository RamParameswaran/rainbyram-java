package dev.findram.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ForecastDataPointDTO {
    long id;
    String main;
    String description;
}
