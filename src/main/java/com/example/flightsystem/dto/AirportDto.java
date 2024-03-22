package com.example.flightsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportDto {
    private String name;
    private String country;
    private String code;
    private Long numPassengers;
}
