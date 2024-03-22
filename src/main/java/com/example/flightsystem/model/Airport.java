package com.example.flightsystem.model;

import com.example.flightsystem.dto.AirportDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AIRPORT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Airport {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String country;
    private String code;
    private Long numPassengers;

    public static Airport createAirport(AirportDto airportDto) {
        Airport newAirport = new Airport();

        newAirport.setId(null);
        newAirport.setName(airportDto.getName());
        newAirport.setCountry(airportDto.getCountry());
        newAirport.setCode(airportDto.getCode());
        newAirport.setNumPassengers(airportDto.getNumPassengers());

        return newAirport;
    }
}
