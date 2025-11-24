package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PopulationReportsIntegrationTest {

    static App app;
    private PopulationReport populationReport;

    @BeforeAll
    void init() {
        app = new App();
        app.connect("localhost:33060", 30000);
        populationReport = new PopulationReport(app.con);
    }

    // Optional: direct JDBC setup
    void setup() throws Exception {
        String jdbcUrl = "jdbc:mysql://localhost:33060/world?allowPublicKeyRetrieval=true&useSSL=false";
        String username = "root";
        String password = "example";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(jdbcUrl, username, password);

        populationReport = new PopulationReport(con);
    }

    // ============================================================
    // 1️⃣ Population by Country
    // ============================================================
    @Test
    void testPrintPopulationByCountry() {
        assertDoesNotThrow(() -> populationReport.printPopulationByCountry());

        // Optional: check query returns data
        try {
            Statement stmt = app.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM country LIMIT 1");
            assertTrue(rs.next(), "Country table should have at least one row");
        } catch (Exception e) {
            fail("Failed to query country table: " + e.getMessage());
        }
    }

    // ============================================================
    // 2️⃣ Population by Region
    // ============================================================
    @Test
    void testPrintPopulationByRegion() {
        assertDoesNotThrow(() -> populationReport.printPopulationByRegion());

        try {
            Statement stmt = app.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM country LIMIT 1");
            assertTrue(rs.next(), "Country table should have at least one row");
        } catch (Exception e) {
            fail("Failed to query country table: " + e.getMessage());
        }
    }

    // ============================================================
    // 3️⃣ Population by Continent
    // ============================================================
    @Test
    void testPrintPopulationByContinent() {
        assertDoesNotThrow(() -> populationReport.printPopulationByContinent());

        try {
            Statement stmt = app.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT Continent FROM country LIMIT 1");
            assertTrue(rs.next(), "Country table should have at least one continent");
        } catch (Exception e) {
            fail("Failed to query continent data: " + e.getMessage());
        }
    }

    // ============================================================
    // 4️⃣ Language Report
    // ============================================================
    @Test
    void testPrintLanguageReport() {
        assertDoesNotThrow(() -> populationReport.printLanguageReport());

        try {
            Statement stmt = app.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM countrylanguage WHERE Language IN ('Chinese','English','Hindi','Spanish','Arabic') LIMIT 1");
            assertTrue(rs.next(), "Language table should have at least one of the tracked languages");
        } catch (Exception e) {
            fail("Failed to query language table: " + e.getMessage());
        }
    }
}
