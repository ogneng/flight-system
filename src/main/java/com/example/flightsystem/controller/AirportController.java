package com.example.flightsystem.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.flightsystem.dto.AirportDto;
import com.example.flightsystem.exceptions.RecordNotFoundException;
import com.example.flightsystem.model.Airport;
import com.example.flightsystem.service.AirportService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/airports")
@AllArgsConstructor
public class AirportController {

    private AirportService airportService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AirportController.class);

    @PostMapping("/saveCSV")
    public ResponseEntity<?> saveCSVAirport() {
        try {
            LOGGER.info("Received request to create new airports from csv.");

            // Load CSV file from resources/static directory
            Resource resource = new ClassPathResource("static/airports.csv");

            List<Airport> result = airportService.importFromCSV(resource.getInputStream());
            LOGGER.info("Airports created succefully. ");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Internal service error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/saveByCountry")
    public ResponseEntity<?> saveAirportByCountry(@RequestBody AirportDto airportDto,
            @RequestParam(value = "country", required = true) String country) {

        try {
            LOGGER.info("Received request to create new airport by country with name = " + country);
            Airport result = airportService.saveAirportByCountry(airportDto, country);
            LOGGER.info("Airport created succefully. ");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Internal service error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveAirport(@RequestBody AirportDto airportDto) {
        try {
            LOGGER.info("Received request to create new airport.");
            Airport result = airportService.saveAirport(airportDto);
            LOGGER.info("Airport created succefully. ");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Internal service error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getDirectFlights")
    public ResponseEntity<?> getDirectFlights() {
        try {
            LOGGER.info("Received request to get all airports.");
            List<Airport> result = airportService.getAllAirports();
            LOGGER.info("Airports fetched succefully. ");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Internal service error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/mostPassengers")
    public ResponseEntity<?> mostPassengers(@RequestParam(value = "country", required = true) String country) {
        try {
            LOGGER.info("Received request to get all airports.");
            Airport result = airportService.mostPassengers(country);
            LOGGER.info("Airports fetched succefully. ");

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Internal service error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Guess there are different ways to delete depending on the business logic,
    // I went by code it semed like unique ID
    @DeleteMapping("/deleteAirportByCode")
    public ResponseEntity<?> deletePrefferedRouteById(@RequestParam(value = "code", required = true) String code) {

        try {
            LOGGER.info("Received request to delete airport by code.");
            String result = airportService.deleteAirportByCode(code);
            LOGGER.info("Airport deleted succefully. ");
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal service error, destinations cannot be deleted, please try again.");
        }
    }
}
