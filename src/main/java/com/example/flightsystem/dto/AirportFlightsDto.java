package com.example.flightsystem.dto;

import com.example.flightsystem.model.Flight;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportFlightsDto {
    private String airportName;
    private List<Flight> flights;
}
