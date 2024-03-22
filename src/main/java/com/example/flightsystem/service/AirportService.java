package com.example.flightsystem.service;

import java.util.List;

import java.io.InputStream;

import com.example.flightsystem.dto.AirportDto;
import com.example.flightsystem.model.Airport;

public interface AirportService {

    List<Airport> importFromCSV(InputStream inputStream);

    Airport saveAirport(AirportDto airportDto);

    Airport saveAirportByCountry(AirportDto airportDto, String country);

    List<Airport> getAllAirports();

    Airport getAirportByCode(String code);

    Airport mostPassengers(String country);

    String deleteAirportByCode(String code);

}
