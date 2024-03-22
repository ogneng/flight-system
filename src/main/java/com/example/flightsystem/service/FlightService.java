package com.example.flightsystem.service;

import java.io.InputStream;
import java.util.List;

import com.example.flightsystem.dto.AirportFlightsDto;
import com.example.flightsystem.dto.FlightDto;
import com.example.flightsystem.model.Flight;

public interface FlightService {
    Flight saveFlight(FlightDto flightDto);

    List<Flight> importFromCSV(InputStream inputStream);

    List<Flight> getDirectFlights(String from, String to);

    AirportFlightsDto getFlightsByAirportCode(String code);
}
