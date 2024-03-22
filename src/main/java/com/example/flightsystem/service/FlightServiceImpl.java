package com.example.flightsystem.service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.example.flightsystem.dto.AirportFlightsDto;
import com.example.flightsystem.dto.FlightDto;
import com.example.flightsystem.helper.CSVHelper;
import com.example.flightsystem.model.Airport;
import com.example.flightsystem.model.Flight;
import com.example.flightsystem.repository.FlightRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightServiceImpl.class);
    private final ModelMapper modelMapper;

    private FlightRepository flightRepository;
    private AirportService airportService;

    @Override
    @Transactional
    public Flight saveFlight(FlightDto flightDto) {
        Flight flight = modelMapper.map(flightDto, Flight.class);
        LOGGER.info("Flight mapped succefully. ");

        return flightRepository.save(flight);
    }

    @Override
    @Transactional
    public List<Flight> importFromCSV(InputStream inputStream) {
        try {
            List<FlightDto> flightDtos = CSVHelper.csvToFlight(inputStream);
            List<Flight> flights = flightDtos.stream()
                    .map(dto -> modelMapper.map(dto, Flight.class))
                    .collect(Collectors.toList());
            LOGGER.info("Flights mapped succefully. ");

            return flightRepository.saveAll(flights);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred in file upload. Please check the validity of your file.");
        }
    }

    @Override
    public List<Flight> getDirectFlights(String from, String to) {
        return flightRepository.findByCodeStartAirportAndCodeDestAirport(
                from != null ? from : "",
                to != null ? to : "");
    }

    @Override
    public AirportFlightsDto getFlightsByAirportCode(String code) {

        AirportFlightsDto airportFlightsDto = new AirportFlightsDto();
        Airport airport = airportService.getAirportByCode(code);
        List<Flight> flights = flightRepository.findByCodeStartAirport(code);

        flights.sort(Comparator.comparing(Flight::getCodeDestAirport)
                .thenComparing(Flight::getDepartureTime));

        airportFlightsDto.setAirportName(airport.getName());
        airportFlightsDto.setFlights(flights);

        return airportFlightsDto;
    }

}
