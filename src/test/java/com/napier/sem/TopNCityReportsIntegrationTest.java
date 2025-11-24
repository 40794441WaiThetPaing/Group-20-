package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TopNCityReportsIntegrationTest {

    static App app;

    private TopNCityReports reports;

    @BeforeAll
    void init()
    {
        app= new App();
        app.connect("localhost: 33060", 30000);
        reports = new TopNCityReports(app.con);
    }

    void setup() throws Exception {
        // Update this as needed for your environment
        String jdbcUrl = "jdbc:mysql://localhost:33060/world?allowPublicKeyRetrieval=true&useSSL=false";
        String username = "root";
        String password = "example";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(jdbcUrl, username, password);
        reports = new TopNCityReports(con);
    }

    @Test
    void testGetTopNCitiesInWorld() throws SQLException {
        ArrayList<City> cities = reports.getTopNCitiesInWorld(5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
        reports.printCityReport(cities, "Top 5 Cities In World");
    }

    @Test
    void testGetTopNCitiesInContinent() throws SQLException {
        ArrayList<City> cities = reports.getTopNCitiesInContinent("Asia", 5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
        reports.printCityReport(cities, "Top 5 Cities In Asia");
    }

    @Test
    void testGetTopNCitiesInRegion() throws SQLException {
        ArrayList<City> cities = reports.getTopNCitiesInRegion("Eastern Asia", 5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
        reports.printCityReport(cities, "Top 5 Cities In Eastern Asia");
    }

    @Test
    void testGetTopNCitiesInCountry() throws SQLException {
        ArrayList<City> cities = reports.getTopNCitiesInCountry("Japan", 5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
        reports.printCityReport(cities, "Top 5 Cities In Japan");
    }

    @Test
    void testGetTopNCitiesInDistrict() throws SQLException {
        ArrayList<City> cities = reports.getTopNCitiesInDistrict("Tokyo-To", 5);
        assertNotNull(cities);
        assertTrue(cities.size() <= 5);
        reports.printCityReport(cities, "Top 5 Cities In Tokyo-To");
    }
}
