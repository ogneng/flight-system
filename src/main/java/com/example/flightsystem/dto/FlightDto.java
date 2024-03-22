package com.example.flightsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    private String codeStartAirport;
    private String codeDestAirport;
    private Long departureTime;
    private Long flightDuration;
}
