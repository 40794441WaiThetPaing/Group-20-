package com.napier.sem;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link GenerateCityReports} class.
 * These tests verify printing behaviour and object handling
 * without requiring a real database connection.
 */
public class GenerateCityReportsTest {

    /**
     * Instance of the class being tested.
     * Uses a mocked database connection.
     */
    private static GenerateCityReports gcr;

    /**
     * Initialises the test suite by creating a mock Connection
     * and passing it into a new {@link GenerateCityReports} instance.
     */
    @BeforeAll
    static void init() {
        Connection mockCon = Mockito.mock(Connection.class);
        gcr = new GenerateCityReports(mockCon);
    }

    /**
     * Ensures that passing {@code null} into printCityReport()
     * does not throw an exception.
     */
    @Test
    void testPrintCityReport_Null() {
        assertDoesNotThrow(() -> gcr.printCityReport(null));
    }

    /**
     * Ensures an empty list of cities does not cause a failure.
     */
    @Test
    void testPrintCityReport_EmptyList() {
        ArrayList<City> cities = new ArrayList<>();
        assertDoesNotThrow(() -> gcr.printCityReport(cities));
    }

    /**
     * Ensures that lists containing null elements are ignored safely.
     */
    @Test
    void testPrintCityReport_ListContainsNull() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        assertDoesNotThrow(() -> gcr.printCityReport(cities));
    }

    /**
     * Ensures valid City objects can be printed without errors.
     */
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

    // ---------------------------
    // Country Getter/Setter Tests
    // ---------------------------

    /**
     * Tests Country.setCode() and getCode().
     */
    @Test
    void testSetAndGetCode() {
        Country c = new Country();
        c.setCode(123);
        assertEquals(123, c.getCode());
    }

    /**
     * Tests Country.setName() and getName().
     */
    @Test
    void testSetAndGetName() {
        Country c = new Country();
        c.setName("Japan");
        assertEquals("Japan", c.getName());
    }

    /**
     * Tests Country.setContinent() and getContinent().
     */
    @Test
    void testSetAndGetContinent() {
        Country c = new Country();
        c.setContinent("Asia");
        assertEquals("Asia", c.getContinent());
    }

    /**
     * Tests Country.setRegion() and getRegion().
     */
    @Test
    void testSetAndGetRegion() {
        Country c = new Country();
        c.setRegion("Eastern Asia");
        assertEquals("Eastern Asia", c.getRegion());
    }

    /**
     * Tests Country.setPopulation() and getPopulation().
     */
    @Test
    void testSetAndGetPopulation() {
        Country c = new Country();
        c.setPopulation(125000000);
        assertEquals(125000000, c.getPopulation());
    }

    /**
     * Tests Country.setCapital() and getCapital().
     */
    @Test
    void testSetAndGetCapital() {
        Country c = new Country();
        c.setCapital(1234);
        assertEquals(1234, c.getCapital());
    }

    /**
     * Tests City.setId() and getId().
     */
    @Test
    void testSetAndGetId() {
        City city = new City();
        city.setId(123);
        assertEquals(123, city.getId());
    }

}
