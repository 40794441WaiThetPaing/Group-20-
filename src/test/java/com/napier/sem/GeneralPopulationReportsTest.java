package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralPopulationReportsTest {

    private static GeneralPopulationReports gpr;

    @BeforeAll
    static void init() {
        // Establish mock connection
        Connection mockCon = Mockito.mock(Connection.class);
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


}
