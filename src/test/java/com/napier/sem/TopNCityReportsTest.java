package com.napier.sem;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TopNCityReports class.
 * Tests printing of city reports with empty and valid city lists.
 */
public class TopNCityReportsTest {
    /**
     * Instance of TopNCityReports used for testing.
     */
    private static TopNCityReports topNCityReports;

    /**
     * Initializes the TopNCityReports instance with a mock database connection.
     */
    @BeforeAll
    static void init() {
        Connection mockCon = Mockito.mock(Connection.class);
        topNCityReports = new TopNCityReports(mockCon);
    }

    /**
     * Tests printing a city report with an empty city list.
     * Ensures no exceptions are thrown.
     */
    @Test
    void testPrintCityReport_EmptyList() {
        ArrayList<City> cities = new ArrayList<>();
        assertDoesNotThrow(() -> topNCityReports.printCityReport(cities, "Empty List Test"));
    }

    /**
     * Tests printing a city report with a valid city.
     * Ensures no exceptions are thrown.
     */
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
