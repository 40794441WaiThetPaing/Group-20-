package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GenerateCityReportsIntegrationTest
{
    static App app;
    static GenerateCityReports gcr;

    @BeforeAll
    void init()
    {
        // Create App and connect to database
        app = new App();
        app.connect("localhost:33060", 30000);   // Uses your existing connect() → db:3306 inside docker

        // Create report generator with live DB connection
        gcr = new GenerateCityReports(app.con);
    }

    // -----------------------------------------------------------
    // TEST 1: All cities in the world
    // -----------------------------------------------------------
    @Test
    void testGetAllCitiesByPopulation()
    {
        ArrayList<City> cities = gcr.getAllCitiesByPopulation();

        assertNotNull(cities);
        assertTrue(cities.size() > 0);

        // Check first city is valid
        City top = cities.get(0);
        assertNotNull(top.getName());
        assertTrue(top.getPopulation() > 0);
    }

    // -----------------------------------------------------------
    // TEST 2: Cities by Continent
    // -----------------------------------------------------------
    @Test
    void testGetCitiesByContinent()
    {
        ArrayList<City> cities = gcr.getCitiesByContinent("Asia");

        assertNotNull(cities);
        assertTrue(cities.size() > 0);

        // Check all results are actually in Asia
        for (City c : cities)
        {
            assertEquals("Asia", c.getCountry().getContinent());
        }
    }

    // -----------------------------------------------------------
    // TEST 3: Cities by Region
    // -----------------------------------------------------------
    @Test
    void testGetCitiesByRegion()
    {
        ArrayList<City> cities = gcr.getCitiesByRegion("Western Europe");

        assertNotNull(cities);
        assertTrue(cities.size() > 0);

        // Check region is correct
        for (City c : cities)
        {
            assertEquals("Western Europe", c.getCountry().getRegion());
        }
    }

    // -----------------------------------------------------------
    // TEST 4: Cities by Country
    // -----------------------------------------------------------
    @Test
    void testGetCitiesByCountry()
    {
        ArrayList<City> cities = gcr.getCitiesByCountry("Japan");

        assertNotNull(cities);
        assertTrue(cities.size() > 0);

        for (City c : cities)
        {
            assertEquals("Japan", c.getCountry().getName());
        }
    }

    // -----------------------------------------------------------
    // TEST 5: Cities by District
    // -----------------------------------------------------------
    @Test
    void testGetCitiesByDistrict()
    {
        ArrayList<City> cities = gcr.getCitiesByDistrict("England");

        assertNotNull(cities);
        assertTrue(cities.size() > 0);

        for (City c : cities)
        {
            assertEquals("England", c.getDistrict());
        }
    }
}

