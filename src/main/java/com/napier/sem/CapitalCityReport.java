package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * Provides methods for generating various capital city population reports
 * based on continent, region, country, district, or worldwide.
 * Uses SQL queries to retrieve country data from the database.
 */
public class CapitalCityReport {
    private Connection con;

    /** Constructor to initialize the database connection */
    public CapitalCityReport(Connection con) {
        this.con = con;
    }

    /**
     * 1. All capital cities in the world organized by largest population to smallest
     */
    public void printAllCapitalCitiesWorld() throws Exception {
        String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "ORDER BY city.Population DESC";

        try (Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery(sql)) {
            printCapitalResultSet(rset);
        }
    }

    /**
     * 2. All capital cities in a continent sorted by population
     */
    public void printCapitalCitiesByContinent(String continent) throws Exception {
        String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Continent = ? " +
                "ORDER BY city.Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            try (ResultSet rset = stmt.executeQuery()) {
                System.out.printf("\nCapital cities in %s (sorted by population):%n", continent);
                printCapitalResultSet(rset);
            }
        }
    }

    /**
     * 3. All capital cities in a region sorted by population
     */
    public void printCapitalCitiesByRegion(String region) throws Exception {
        String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            try (ResultSet rset = stmt.executeQuery()) {
                System.out.printf("\nCapital cities in %s (sorted by population):%n", region);
                printCapitalResultSet(rset);
            }
        }
    }

    /**
     * 4. Top N capital cities in the world by population
     */
    public void printTopNCapitalCitiesWorld(int topN) throws Exception {
        String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, topN);
            try (ResultSet rset = stmt.executeQuery()) {
                System.out.printf("\nTop %d Capital Cities in the World:\n", topN);
                printCapitalResultSet(rset);
            }
        }
    }

    /**
     * 5. Top N capital cities in a continent by population
     */
    public void printTopNCapitalCitiesByContinent(String continent, int topN) throws Exception {
        String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Continent = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            stmt.setInt(2, topN);
            try (ResultSet rset = stmt.executeQuery()) {
                System.out.printf("\nTop %d Capital Cities in %s:\n", topN, continent);
                printCapitalResultSet(rset);
            }
        }
    }

    /**
     * 6. Top N capital cities in a region by population
     */
    public void printTopNCapitalCitiesByRegion(String region, int topN) throws Exception {
        String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                "FROM city " +
                "JOIN country ON city.ID = country.Capital " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            stmt.setInt(2, topN);
            try (ResultSet rset = stmt.executeQuery()) {
                System.out.printf("\nTop %d Capital Cities in %s:\n", topN, region);
                printCapitalResultSet(rset);
            }
        }
    }

    /** Helper method to print ResultSet in formatted table */
    private void printCapitalResultSet(ResultSet rset) throws Exception {
        System.out.printf("%-30s %-40s %-15s%n", "Capital", "Country", "Population");
        System.out.println("--------------------------------------------------------------------------");

        while (rset.next()) {
            String capital = rset.getString("Capital");
            String country = rset.getString("Country");
            int population = rset.getInt("Population");

            System.out.printf("%-30s %-40s %-15d%n", capital, country, population);
        }
    }
}
