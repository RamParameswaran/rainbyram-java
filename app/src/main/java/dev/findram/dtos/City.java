package dev.findram.dtos;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class City {
    String name;
    Double lat;
    Double lon;
}
