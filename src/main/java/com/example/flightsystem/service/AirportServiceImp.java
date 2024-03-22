package com.example.flightsystem.service;

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.flightsystem.dto.AirportDto;
import com.example.flightsystem.helper.CSVHelper;
import com.example.flightsystem.model.Airport;
import com.example.flightsystem.repository.AirportRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AirportServiceImp implements AirportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirportServiceImp.class);

    private AirportRepository airportRepository;

    @Override
    @Transactional
    public List<Airport> importFromCSV(InputStream inputStream) {
        try {

            List<AirportDto> airportsDto = CSVHelper.csvToAirport(inputStream);
            List<Airport> airports = airportsDto.stream().map(Airport::createAirport).collect(Collectors.toList());
            LOGGER.info("Airports mapped succefully. ");

            return airportRepository.saveAll(airports);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred in file upload. Please check the validity of your file.");
        }
    }

    @Override
    @Transactional
    public Airport saveAirport(AirportDto airportDto) {
        Airport airport = Airport.createAirport(airportDto);
        return airportRepository.save(airport);
    }

    @Override
    @Transactional
    public Airport saveAirportByCountry(AirportDto airportDto, String country) {
        Airport airport = Airport.createAirport(airportDto);
        if (airport.getCountry() == null) {
            airport.setCountry(country);
        }
        return airportRepository.save(airport);
    }

    @Override
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    @Override
    public Airport getAirportByCode(String code) {
        return airportRepository.findByCode(code);
    }

    @Override
    public Airport mostPassengers(String country) {
        Optional<Airport> optionalAirport = airportRepository.findByCountryOrderByNumPassengersDesc(country);
        return optionalAirport.orElseThrow(null);
    }

    @Override
    @Transactional
    public String deleteAirportByCode(String code) {
        try {
            int rowsAffected = airportRepository.deleteByCode(code);
            if (rowsAffected > 0) {
                LOGGER.info("Airports deleted successfully for code: " + code);
                return "Deleted with code " + code;
            } else {
                LOGGER.info("No airports found with code: " + code);
                return "No airports found.";
            }
        } catch (DataAccessException e) {
            throw e;
        }
    }
}