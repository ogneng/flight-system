package com.example.flightsystem.helper;

import java.util.*;
import com.example.flightsystem.dto.AirportDto;
import com.example.flightsystem.dto.FlightDto;
import com.example.flightsystem.exceptions.*;
import org.apache.commons.csv.*;

import java.io.*;

public class CSVHelper {

    public static List<AirportDto> csvToAirport(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';')
                        .withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<AirportDto> airportDtos = new ArrayList<AirportDto>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                AirportDto airportDto = new AirportDto();

                airportDto.setName(csvRecord.get(0));
                airportDto.setCountry(csvRecord.get(1));
                airportDto.setCode(csvRecord.get(2));
                airportDto.setNumPassengers(Long.parseLong(csvRecord.get(3)));

                airportDtos.add(airportDto);
            }
            return airportDtos;
        } catch (IOException e) {
            throw new ImportException("Error to import data from CSV file: " + e.getMessage());
        }
    }

    public static List<FlightDto> csvToFlight(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';')
                        .withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<FlightDto> flightDtos = new ArrayList<FlightDto>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                FlightDto flightDto = new FlightDto();

                flightDto.setCodeStartAirport(csvRecord.get(0));
                flightDto.setCodeDestAirport(csvRecord.get(1));
                flightDto.setDepartureTime(Long.parseLong(csvRecord.get(2)));
                flightDto.setFlightDuration(Long.parseLong(csvRecord.get(3)));

                flightDtos.add(flightDto);
            }
            return flightDtos;
        } catch (IOException e) {
            throw new ImportException("Error to import data from CSV file: " + e.getMessage());
        }
    }

}
