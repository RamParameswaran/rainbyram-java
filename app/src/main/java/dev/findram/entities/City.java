package dev.findram.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class City {
    String name;
    Double lat;
    Double lon;
}
