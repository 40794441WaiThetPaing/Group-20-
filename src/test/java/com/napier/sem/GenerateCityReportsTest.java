package com.napier.sem;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateCityReportsTest {

    private static GenerateCityReports gcr;

    @BeforeAll
    static void init() {
        // Mock connection because we are NOT testing database
        Connection mockCon = Mockito.mock(Connection.class);
        gcr = new GenerateCityReports(mockCon);
    }

    // 1. Null input
    @Test
    void testPrintCityReport_Null() {
        assertDoesNotThrow(() -> gcr.printCityReport(null));
    }

    // 2. Empty list
    @Test
    void testPrintCityReport_EmptyList() {
        ArrayList<City> cities = new ArrayList<>();
        assertDoesNotThrow(() -> gcr.printCityReport(cities));
    }

    // 3. List contains null element
    @Test
    void testPrintCityReport_ListContainsNull() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        assertDoesNotThrow(() -> gcr.printCityReport(cities));
    }

    // 4. Valid city object
    @Test
    void testPrintCityReport_ValidCity() {
        ArrayList<City> cities = new ArrayList<>();
        City c = new City();
        c.setName("Tokyo");
        c.setDistrict("Tokyo-To");
        c.setPopulation(13929286);

        Country country = new Country();
        country.setName("Japan");
        c.setCountry(country);

        cities.add(c);

        assertDoesNotThrow(() -> gcr.printCityReport(cities));
    }


    @Test
    void testSetAndGetCode() {
        Country c = new Country();
        c.setCode(123);
        assertEquals(123, c.getCode());
    }

    @Test
    void testSetAndGetName() {
        Country c = new Country();
        c.setName("Japan");
        assertEquals("Japan", c.getName());
    }

    @Test
    void testSetAndGetContinent() {
        Country c = new Country();
        c.setContinent("Asia");
        assertEquals("Asia", c.getContinent());
    }

    @Test
    void testSetAndGetRegion() {
        Country c = new Country();
        c.setRegion("Eastern Asia");
        assertEquals("Eastern Asia", c.getRegion());
    }

    @Test
    void testSetAndGetPopulation() {
        Country c = new Country();
        c.setPopulation(125000000);
        assertEquals(125000000, c.getPopulation());
    }

    @Test
    void testSetAndGetCapital() {
        Country c = new Country();
        c.setCapital(1234);
        assertEquals(1234, c.getCapital());
    }
}


