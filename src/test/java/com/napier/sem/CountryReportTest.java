package com.napier.sem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryReportTest {

    private Connection mockCon;
    private Statement mockStmt;
    private ResultSet mockRset;
    private CountryReport report;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() throws Exception {
        // Redirect system output
        System.setOut(new PrintStream(outputStream));

        mockCon = mock(Connection.class);
        mockStmt = mock(Statement.class);
        mockRset = mock(ResultSet.class);

        // Link mocks
        when(mockCon.createStatement()).thenReturn(mockStmt);
        when(mockStmt.executeQuery(Mockito.anyString())).thenReturn(mockRset);

        report = new CountryReport(mockCon);
    }

    @Test
    void testPrintCountriesByPopulation() throws Exception {

        // Mock 1st row
        when(mockRset.next()).thenReturn(true, true, false);

        // Values for first country
        when(mockRset.getString("Code")).thenReturn("USA", "CHN");
        when(mockRset.getString("Name")).thenReturn("United States", "China");
        when(mockRset.getString("Continent")).thenReturn("North America", "Asia");
        when(mockRset.getString("Region")).thenReturn("Northern America", "Eastern Asia");
        when(mockRset.getInt("Population")).thenReturn(331000000, 1400000000);
        when(mockRset.getString("Capital")).thenReturn("Washington", "Beijing");

        // Execute method
        report.printCountriesByPopulation();

        String output = outputStream.toString();

        // Assertions
        assertTrue(output.contains("USA"));
        assertTrue(output.contains("United States"));
        assertTrue(output.contains("Washington"));

        assertTrue(output.contains("CHN"));
        assertTrue(output.contains("China"));
        assertTrue(output.contains("Beijing"));

        assertTrue(output.contains("Population"));
    }
}
