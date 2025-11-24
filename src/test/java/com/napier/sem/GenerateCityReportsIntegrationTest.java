package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the {@link GenerateCityReports} class.
 * <p>
 * These tests run against a live MySQL database using the App
 * connection method. The purpose is to verify that SQL queries
 * correctly retrieve real data from the World database.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GenerateCityReportsIntegrationTest {

    /**
     * Application instance used to establish a real database connection.
     */
    static App app;

    /**
     * Report generator used to run live SQL queries.
     */
    static GenerateCityReports gcr;

    /**
     * Establishes a connection to the running database container and
     * initialises the GenerateCityReports instance. This method runs
     * once before all integration tests.
     */
    @BeforeAll
    void init() {
        app = new App();
        app.connect("localhost:33060", 30000);
        gcr = new GenerateCityReports(app.con);
    }

    /**
     * Tests that all cities in the world can be retrieved and sorted
     * by population. Ensures the list is non-empty and that the top
     * entry contains valid data.
     */
    @Test
    void testGetAllCitiesByPopulation() throws Exception {
        ArrayList<City> cities = gcr.getAllCitiesByPopulation();

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        City top = cities.get(0);
        assertNotNull(top.getName());
        assertTrue(top.getPopulation() > 0);
    }

    /**
     * Tests that cities can be retrieved by continent and verifies that
     * all returned cities belong to the specified continent.
     */
    @Test
    void testGetCitiesByContinent() throws Exception {
        ArrayList<City> cities = gcr.getCitiesByContinent("Asia");

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        for (City c : cities) {
            assertEquals("Asia", c.getCountry().getContinent());
        }
    }

    /**
     * Tests that cities can be retrieved by region and verifies that all
     * returned cities belong to the specified region.
     */
    @Test
    void testGetCitiesByRegion() throws Exception {
        ArrayList<City> cities = gcr.getCitiesByRegion("Western Europe");

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        for (City c : cities) {
            assertEquals("Western Europe", c.getCountry().getRegion());
        }
    }

    /**
     * Tests that cities can be retrieved by country name and verifies
     * that all returned cities match the specified country.
     */
    @Test
    void testGetCitiesByCountry() throws Exception {
        ArrayList<City> cities = gcr.getCitiesByCountry("Japan");

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        for (City c : cities) {
            assertEquals("Japan", c.getCountry().getName());
        }
    }

    /**
     * Tests that cities can be retrieved by district and verifies that
     * all results belong to the specified district.
     */
    @Test
    void testGetCitiesByDistrict() throws Exception {
        ArrayList<City> cities = gcr.getCitiesByDistrict("England");

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        for (City c : cities) {
            assertEquals("England", c.getDistrict());
        }
    }
}
