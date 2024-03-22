package com.example.flightsystem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flightsystem.dto.AirportFlightsDto;
import com.example.flightsystem.dto.FlightDto;
import com.example.flightsystem.model.Flight;
import com.example.flightsystem.service.FlightService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/flights")
@AllArgsConstructor
public class FlightController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);

    private FlightService flightService;

    @PostMapping("/saveCSV")
    public ResponseEntity<?> saveCSVFlight() {
        try {
            LOGGER.info("Received request to create new flights from csv.");

            // Load CSV file from resources/static directory
            Resource resource = new ClassPathResource("static/flights.csv");

            List<Flight> result = flightService.importFromCSV(resource.getInputStream());
            LOGGER.info("Flights created succefully. ");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Internal service error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveFlight(@RequestBody FlightDto flightDto) {
        try {
            LOGGER.info("Received request to create new flight.");
            Flight result = flightService.saveFlight(flightDto);
            LOGGER.info("Flight created succefully. ");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Internal service error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getDirectFlights")
    public ResponseEntity<?> getDirectFlights(@RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to) {
        try {
            LOGGER.info("Received request to get flights. ");
            List<Flight> result = flightService.getDirectFlights(from, to);
            LOGGER.info("Flights returned succefully. ");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Internal service error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/flightsByCode")
    public ResponseEntity<?> getFlightsByAirportCode(@RequestParam(value = "code", required = true) String code) {
        try {
            LOGGER.info("Received request to get flight by code " + code + ".");
            AirportFlightsDto result = flightService.getFlightsByAirportCode(code);
            LOGGER.info("Flights returned succefully. ");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Internal service error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
