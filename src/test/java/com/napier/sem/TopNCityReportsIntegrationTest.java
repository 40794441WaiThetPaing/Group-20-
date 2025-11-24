package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for TopNCityReports class.
 * Tests fetching and printing top N city reports from a live database.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TopNCityReportsIntegrationTest {

    /**
     * App instance used to connect to the database.
     */
    static App app;

    /**
     * Instance of TopNCityReports used for integration testing.
     */
    private TopNCityReports reports;

    /**
     * Initializes the database connection and TopNCityReports before all tests.
     */
    @BeforeAll
    void init() {
        app = new App();
        app.connect("localhost: 33060", 30000);
        reports = new TopNCityReports(app.con);
    }

    /**
     * Optional setup method for establishing a direct database connection.
     * Update the connection details as needed for your environment.
     *
     * @throws Exception if a database connection cannot be established
     */
    void setup() throws Exception {
        // Update this as needed for your environment
        String jdbcUrl = "jdbc:mysql://localhost:33060/world?allowPublicKeyRetrieval=true&useSSL=false";
        String username = "root";
        String password = "example";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(jdbcUrl, username, password);
        reports = new TopNCityReports(con);
    }

    /**
     * Tests fetching the top 5 most populated cities in the world.
     * Ensures the list is not null and does not exceed 5 cities.
     *
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetTopNCitiesInWorld() throws SQLException {
        ArrayList<City> cities = reports.getTopNCitiesInWorld(5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
        reports.printCityReport(cities, "Top 5 Cities In World");
    }

    /**
     * Tests fetching the top 5 most populated cities in Asia.
     * Ensures the list is not null and does not exceed 5 cities.
     *
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetTopNCitiesInContinent() throws SQLException {
        ArrayList<City> cities = reports.getTopNCitiesInContinent("Asia", 5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
        reports.printCityReport(cities, "Top 5 Cities In Asia");
    }

    /**
     * Tests fetching the top 5 most populated cities in Eastern Asia region.
     * Ensures the list is not null and does not exceed 5 cities.
     *
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetTopNCitiesInRegion() throws SQLException {
        ArrayList<City> cities = reports.getTopNCitiesInRegion("Eastern Asia", 5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
        reports.printCityReport(cities, "Top 5 Cities In Eastern Asia");
    }

    /**
     * Tests fetching the top 5 most populated cities in Japan.
     * Ensures the list is not null and does not exceed 5 cities.
     *
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetTopNCitiesInCountry() throws SQLException {
        ArrayList<City> cities = reports.getTopNCitiesInCountry("Japan", 5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
        reports.printCityReport(cities, "Top 5 Cities In Japan");
    }

    /**
     * Tests fetching the top 5 most populated cities in Tokyo-To district.
     * Ensures the list is not null and does not exceed 5 cities.
     *
     * @throws SQLException if a database access error occurs
     */
    @Test
    void testGetTopNCitiesInDistrict() throws SQLException {
        ArrayList<City> cities = reports.getTopNCitiesInDistrict("Tokyo-To", 5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
        reports.printCityReport(cities, "Top 5 Cities In Tokyo-To");
    }
}
