package com.napier.sem;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TopNCityReportsTest {

    private static TopNCityReports topNCityReports;

    @BeforeAll
    static void init() {
        Connection mockCon = Mockito.mock(Connection.class);
        topNCityReports = new TopNCityReports(mockCon);
    }

    @Test
    void testPrintCityReport_EmptyList() {
        ArrayList<City> cities = new ArrayList<>();
        assertDoesNotThrow(() -> topNCityReports.printCityReport(cities, "Empty List Test"));
    }

    @Test
    void testPrintCityReport_ValidCity() {
        ArrayList<City> cities = new ArrayList<>();
        City city = new City();
        city.setName("Tokyo");
        city.setDistrict("Tokyo-To");
        city.setPopulation(13929286);

        Country country = new Country();
        country.setName("Japan");
        city.setCountry(country);

        cities.add(city);

        assertDoesNotThrow(() -> topNCityReports.printCityReport(cities, "Valid City Test"));
    }
}
