package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * CountryReport provides methods to retrieve various country-related
 * population reports from the database. Each method runs a SQL query,
 * then prints the results in a formatted table.
 */
public class CountryReport {
    // Stores the active database connection
    private Connection con;

    /**
     * Constructor
     *
     * @param con The database connection used for executing SQL queries
     */
    public CountryReport(Connection con) {
        this.con = con;
    }

    /**
     * 1. All countries in the world organised by largest population to smallest.
     */
    public void printCountriesByPopulation() throws Exception {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "ORDER BY c.Population DESC";

        try (Statement stmt = con.createStatement(); ResultSet rset = stmt.executeQuery(sql)) {
            printCountryResultSet(rset);
        }
    }

    /**
     * 2. All countries in a continent sorted by population
     */
    public void printCountriesByContinent(String continent) throws Exception {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Continent = ? " +
                "ORDER BY c.Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            try (ResultSet rset = stmt.executeQuery()) {
                printCountryResultSet(rset);
            }
        }
    }

    /**
     * 3. All countries in a given region sorted by population
     */
    public void printCountriesByRegion(String region) throws Exception {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Region = ? " +
                "ORDER BY c.Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            try (ResultSet rset = stmt.executeQuery()) {
                System.out.printf("\nCountries in %s (sorted by population):%n", region);
                printCountryResultSet(rset);
            }
        }
    }

    /**
     * 4. Top N countries in the world by population
     */
    public void printTopCountriesByPopulation(int topN) throws Exception {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "ORDER BY c.Population DESC " +
                "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, topN);
            try (ResultSet rset = stmt.executeQuery()) {
                System.out.printf("\nTop %d Countries by Population:\n", topN);
                printCountryResultSet(rset);
            }
        }
    }

    /**
     * 5. Top N countries in a continent by population
     */
    public void printTopCountriesInContinent(String continent, int topN) throws Exception {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Continent = ? " +
                "ORDER BY c.Population DESC " +
                "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            stmt.setInt(2, topN);
            try (ResultSet rset = stmt.executeQuery()) {
                System.out.printf("\nTop %d Countries in %s by Population:\n", topN, continent);
                printCountryResultSet(rset);
            }
        }
    }

    /**
     * 6. Top N countries in a region by population
     */
    public void printTopCountriesInRegion(String region, int topN) throws Exception {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                "FROM country c " +
                "LEFT JOIN city ci ON c.Capital = ci.ID " +
                "WHERE c.Region = ? " +
                "ORDER BY c.Population DESC " +
                "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            stmt.setInt(2, topN);
            try (ResultSet rset = stmt.executeQuery()) {
                System.out.printf("\nTop %d Countries in %s by Population:\n", topN, region);
                printCountryResultSet(rset);
            }
        }
    }

    /**
     * Helper method to print ResultSet in formatted table
     */
    private void printCountryResultSet(ResultSet rset) throws Exception {
        System.out.printf("%-5s %-40s %-15s %-25s %-15s %-20s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");
        System.out.println("----------------------------------------------------------------------------------------------------");

        while (rset.next()) {
            String code = rset.getString("Code");
            String name = rset.getString("Name");
            String continent = rset.getString("Continent");
            String region = rset.getString("Region");
            int population = rset.getInt("Population");
            String capital = rset.getString("Capital");

            System.out.printf("%-5s %-40s %-15s %-25s %-15d %-20s%n",
                    code, name, continent, region, population, capital);
        }
    }
}
