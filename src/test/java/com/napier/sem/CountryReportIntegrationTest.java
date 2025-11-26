package com.napier.sem;

import org.junit.jupiter.api.*;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class CountryReportIntegrationTest {

    static App app;
    static CountryReport countryReport;
    static Connection con;

    @BeforeAll
    static void init() throws Exception {
        // Start application and connect to real DB
        app = new App();
        app.connect("localhost:33060", 30000);   // Docker world-db
        con = app.con;

        // CountryReport uses this connection
        countryReport = new CountryReport(con);
    }

    @Test
    void testPrintCountriesByPopulation() {
        assertDoesNotThrow(() -> {
            countryReport.printCountriesByPopulation();
        });
    }

    @Test
    void testPrintCountriesByContinent() {
        assertDoesNotThrow(() -> {
            countryReport.printCountriesByContinent("Asia");
        });
    }

    @Test
    void testPrintCountriesByRegion() {
        assertDoesNotThrow(() -> {
            countryReport.printCountriesByRegion("Eastern Asia");
        });
    }

    @Test
    void testPrintTopCountriesByPopulation() {
        assertDoesNotThrow(() -> {
            countryReport.printTopCountriesByPopulation(10);
        });
    }

    @Test
    void testPrintTopCountriesInContinent() {
        assertDoesNotThrow(() -> {
            countryReport.printTopCountriesInContinent("Europe", 5);
        });
    }

    @Test
    void testPrintTopCountriesInRegion() {
        assertDoesNotThrow(() -> {
            countryReport.printTopCountriesInRegion("Caribbean", 5);
        });
    }

    @AfterAll
    static void teardown() throws Exception {
        app.disconnect();
    }
}
