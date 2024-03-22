package com.example.flightsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.flightsystem.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByCodeStartAirport(String code);

    List<Flight> findByCodeStartAirportAndCodeDestAirport(String from, String to);

}
