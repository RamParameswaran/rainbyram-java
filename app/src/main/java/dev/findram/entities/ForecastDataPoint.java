package dev.findram.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ForecastDataPoint {
    long id;
    String main;
    String description;
}
