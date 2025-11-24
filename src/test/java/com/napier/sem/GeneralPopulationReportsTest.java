package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for GeneralPopulationReports, covering both successful
 * printing of population data and error-handling behavior when database
 * operations fail.
 */

public class GeneralPopulationReportsTest {

    private static GeneralPopulationReports gpr;

    @BeforeAll
    static void init() {
        // Establish mock connection
        Connection mockCon = mock(Connection.class);
        gpr = new GeneralPopulationReports(mockCon);
    }

    /**
     * Utility to capture console output from System.out
     */
    private String captureOutput(Runnable printMethod) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));

        printMethod.run();

        System.setOut(original);  // Restore stdout
        return out.toString();
    }

    /**
     * Unit Testings for printing population reports on total, continent,..., city
     */
    @Test
    void testPrintTotalWorldPopulation() {
        String output = captureOutput(() ->
                gpr.printTotalWorldPopulation(8000000000L));

        String expected = String.format("%-30s %-12d%s",
                "Total World Population:",
                8000000000L,
                System.lineSeparator()
        );

        assertEquals(expected.stripTrailing(), output.stripTrailing());
    }

    @Test
    void testPrintContinentPopulation() {
        String output = captureOutput(() ->
                gpr.printContinentPopulation("Europe", 748000000L));

        String expected = String.format("%-30s %-12d%s",
                "Population of Europe:",
                748000000L,
                System.lineSeparator()
        );

        assertEquals(expected.stripTrailing(), output.stripTrailing());
    }

    @Test
    void testPrintRegionPopulation() {
        String output = captureOutput(() ->
                gpr.printRegionPopulation("Caribbean", 43000000L));

        String expected = String.format("%-30s %-12d%s",
                "Population of Caribbean:",
                43000000L,
                System.lineSeparator()
        );

        assertEquals(expected.stripTrailing(), output.stripTrailing());
    }

    @Test
    void testPrintCountryPopulation() {
        String output = captureOutput(() ->
                gpr.printCountryPopulation("United Kingdom", 68200000L));

        String expected = String.format("%-30s %-12d%s",
                "Population of United Kingdom:",
                68200000L,
                System.lineSeparator()
        );

        assertEquals(expected.stripTrailing(), output.stripTrailing());
    }

    @Test
    void testPrintDistrictPopulation() {
        String output = captureOutput(() ->
                gpr.printDistrictPopulation("California", 39500000L));

        String expected = String.format("%-30s %-12d%s",
                "Population of district California:",
                39500000L,
                System.lineSeparator()
        );

        assertEquals(expected.stripTrailing(), output.stripTrailing());
    }

    @Test
    void testPrintCityPopulation() {
        String output = captureOutput(() ->
                gpr.printCityPopulation("London", 9000000L));

        String expected = String.format("%-30s %-12d%s",
                "Population of city London:",
                9000000L,
                System.lineSeparator()
        );

        assertEquals(expected.stripTrailing(), output.stripTrailing());
    }

    /**
     * Unit Tests for error handling
     * @throws Exception
     */
    @Test
    void testGetTotalWorldPopulationException() throws Exception {
        Connection mockCon = mock(Connection.class);
        when(mockCon.createStatement()).thenThrow(new SQLException("DB error"));

        GeneralPopulationReports gpr = new GeneralPopulationReports(mockCon);

        long pop = gpr.getTotalWorldPopulation();
        assertEquals(0, pop);  // because default is zero
    }

    @Test
    void testGetContinentPopulationException() throws Exception {
        Connection mockCon = mock(Connection.class);
        when(mockCon.createStatement()).thenThrow(new SQLException("DB error"));

        GeneralPopulationReports gpr = new GeneralPopulationReports(mockCon);

        long pop = gpr.getContinentPopulation("North America");
        assertEquals(0, pop);  // because default is zero
    }

    @Test
    void testGetRegionPopulationException() throws Exception {
        Connection mockCon = mock(Connection.class);
        when(mockCon.createStatement()).thenThrow(new SQLException("DB error"));

        GeneralPopulationReports gpr = new GeneralPopulationReports(mockCon);

        long pop = gpr.getRegionPopulation("Caribbean");
        assertEquals(0, pop);  // because default is zero
    }

    @Test
    void testGetCountryPopulationException() throws Exception {
        Connection mockCon = mock(Connection.class);
        when(mockCon.createStatement()).thenThrow(new SQLException("DB error"));

        GeneralPopulationReports gpr = new GeneralPopulationReports(mockCon);

        long pop = gpr.getCountryPopulation("United Kingdom");
        assertEquals(0, pop);  // because default is zero
    }

    @Test
    void testGetDistrictPopulationException() throws Exception {
        Connection mockCon = mock(Connection.class);
        when(mockCon.createStatement()).thenThrow(new SQLException("DB error"));

        GeneralPopulationReports gpr = new GeneralPopulationReports(mockCon);

        long pop = gpr.getDistrictPopulation("California");
        assertEquals(0, pop);  // because default is zero
    }

    @Test
    void testGetCityPopulationException() throws Exception {
        Connection mockCon = mock(Connection.class);
        when(mockCon.createStatement()).thenThrow(new SQLException("DB error"));

        GeneralPopulationReports gpr = new GeneralPopulationReports(mockCon);

        long pop = gpr.getCityPopulation("London");
        assertEquals(0, pop);  // because default is zero
    }
    
}
