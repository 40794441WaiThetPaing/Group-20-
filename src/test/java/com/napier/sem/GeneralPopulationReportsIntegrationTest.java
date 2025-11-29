package com.napier.sem;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for GeneralPopulationReports, verifying real database
 * queries return valid population data for various geographic levels.
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GeneralPopulationReportsIntegrationTest {

    static App app;
    static GeneralPopulationReports gpr;

    @BeforeAll
    static void init() throws Exception {
        // Create App and connect to database
        app = new App();
        app.connect("localhost:33060", 30000);

        // Create report generator with live DB connection
        gpr = new GeneralPopulationReports(app.con);
    }

    /**
     * Testing population data of total world
     */
    @Test
    void testGetTotalWorldPopulation() {
        long pop = gpr.getTotalWorldPopulation();
        assertTrue(pop > 0, "World population should be greater than zero");
    }

    /**
     * Testing population data of a continent
     */
    @Test
    void testGetContinentPopulation() {
        long pop = gpr.getContinentPopulation("Europe");
        assertTrue(pop > 0, "Europe population should be greater than zero");
    }

    /**
     * Testing population data of a region
     */
    @Test
    void testGetRegionPopulation() {
        long pop = gpr.getRegionPopulation("Caribbean");
        assertTrue(pop > 0, "Caribbean population should be greater than zero");
    }

    /**
     * Testing population data of a country
     */
    @Test
    void testGetCountryPopulation() {
        long pop = gpr.getCountryPopulation("United Kingdom");
        assertTrue(pop > 0, "UK population should be greater than zero");
    }

    /**
     * Testing population data of a district
     */
    @Test
    void testGetDistrictPopulation() {
        long pop = gpr.getDistrictPopulation("California");
        assertTrue(pop > 0, "California population should be greater than zero");
    }

    /**
     * Testing population data of a city
     */
    @Test
    void testGetCityPopulation() {
        long pop = gpr.getCityPopulation("London");
        assertTrue(pop > 0, "London population should be greater than zero");
    }
}
