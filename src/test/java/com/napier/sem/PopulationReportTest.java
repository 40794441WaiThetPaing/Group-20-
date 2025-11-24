package com.napier.sem;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PopulationReportTest {

    private static Connection mockCon;
    private static PopulationReport populationReport;

    @BeforeAll
    static void init() {
        mockCon = Mockito.mock(Connection.class);
        populationReport = new PopulationReport(mockCon);
    }


    // Check if the method runs without throwing errors
    @Test
    void testPrintPopulationByContinent_RunWithoutCrash() {
        assertDoesNotThrow(() -> populationReport.printPopulationByContinent());
    }


    // Check if the method still runs even if the connection is NULL
    @Test
    void testPrintPopulationByContinent_NullConnection() {
        PopulationReport pr = new PopulationReport(null);
        assertDoesNotThrow(pr::printPopulationByContinent);
    }


    // Check if the method runs and prints without error
    @Test
    void testPrintLanguageReport_RunWithoutCrash() {
        assertDoesNotThrow(() -> populationReport.printLanguageReport());

    }


    // Check if the method handles NULL connection safely
    @Test
    void testPrintLanguageReport_NullConnection() {
        PopulationReport pr = new PopulationReport(null);
        assertDoesNotThrow(pr::printLanguageReport);
    }
}
