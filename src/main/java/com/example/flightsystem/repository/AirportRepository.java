package com.example.flightsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.flightsystem.model.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    Integer deleteByCode(String code);

    Airport findByCode(String code);

    Optional<Airport> findByCountryOrderByNumPassengersDesc(String country);

}
