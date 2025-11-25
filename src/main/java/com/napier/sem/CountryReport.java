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
     * This query
     * Selects country details
     * Joins city table to get the capital name
     * Orders results by population (DESC)
     */
    public void printCountriesByPopulation() {
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                    "FROM country c " +
                    "LEFT JOIN city ci ON c.Capital = ci.ID " +
                    "ORDER BY c.Population DESC";

            ResultSet rset = stmt.executeQuery(sql);

            // Print table header
            System.out.printf("%-5s %-40s %-15s %-25s %-15s %-20s%n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital");
            System.out.println("----------------------------------------------------------------------------------------------------");

            // Print each row returned from the query
            while (rset.next()) {
                System.out.printf("%-5s %-40s %-15s %-25s %-15d %-20s%n",
                        rset.getString("Code"),
                        rset.getString("Name"),
                        rset.getString("Continent"),
                        rset.getString("Region"),
                        rset.getInt("Population"),
                        rset.getString("Capital"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving country report: " + e.getMessage());
        }
    }

    /**
     * 2. All countries in a continent sorted by population
     * Uses a PreparedStatement because it includes
     * a user-provided parameter (continent).
     * Sorts the returned countries by population in descending order.
     *
     * @param continent the continent for which the country should be listed.
     */
    public void printCountriesByContinent(String continent) {
        try {
            String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                    "FROM country c " +
                    "LEFT JOIN city ci ON c.Capital = ci.ID " +
                    "WHERE c.Continent = ? " +
                    "ORDER BY c.Population DESC";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, continent);

            // Print header
            ResultSet rset = stmt.executeQuery();
            System.out.printf("%-5s %-40s %-15s %-25s %-15s %-20s%n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital");
            System.out.println("----------------------------------------------------------------------------------------------------");

            // Print result
            while (rset.next()) {
                System.out.printf("%-5s %-40s %-15s %-25s %-15d %-20s%n",
                        rset.getString("Code"),
                        rset.getString("Name"),
                        rset.getString("Continent"),
                        rset.getString("Region"),
                        rset.getInt("Population"),
                        rset.getString("Capital"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * 3. All countries in a given region sorted by population
     * Filters results using the provided region name.
     *
     * @param region the region for which the countries should be listed
     */
    public void printCountriesByRegion(String region) {
        try {
            String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                    "FROM country c " +
                    "LEFT JOIN city ci ON c.Capital = ci.ID " +
                    "WHERE c.Region = ? " +
                    "ORDER BY c.Population DESC";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, region);

            ResultSet rset = stmt.executeQuery();
            //print header
            System.out.printf("\nCountries in %s (sorted by population):%n", region);
            System.out.printf("%-5s %-40s %-15s %-25s %-15s %-20s%n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital");
            System.out.println("----------------------------------------------------------------------------------------------------");
            //print result
            while (rset.next()) {
                System.out.printf("%-5s %-40s %-15s %-25s %-15d %-20s%n",
                        rset.getString("Code"),
                        rset.getString("Name"),
                        rset.getString("Continent"),
                        rset.getString("Region"),
                        rset.getInt("Population"),
                        rset.getString("Capital"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving countries by region: " + e.getMessage());
        }
    }

    /**
     * 4. Top N countries in the world by population
     * Using LIMIT ? to restrict the number of returned rows.
     *
     * @param topN the maximum number of countries to return (e.g., 5, 10)
     */
    public void printTopCountriesByPopulation(int topN) {
        try {
            String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                    "FROM country c " +
                    "LEFT JOIN city ci ON c.Capital = ci.ID " +
                    "ORDER BY c.Population DESC " +
                    "LIMIT ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, topN);

            ResultSet rset = stmt.executeQuery();
            System.out.printf("\nTop %d Countries by Population:\n", topN);
            System.out.printf("%-5s %-40s %-15s %-25s %-15s %-20s%n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital");
            System.out.println("----------------------------------------------------------------------------------------------------");

            while (rset.next()) {
                System.out.printf("%-5s %-40s %-15s %-25s %-15d %-20s%n",
                        rset.getString("Code"),
                        rset.getString("Name"),
                        rset.getString("Continent"),
                        rset.getString("Region"),
                        rset.getInt("Population"),
                        rset.getString("Capital"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving top countries by population: " + e.getMessage());
        }
    }

    /**
     * 5. Top N countries in a continent by population
     * Uses LIMIT ? to restrict the number of returned rows.
     *
     * @param continent the continent for which the top populated countries should be displayed
     * @param topN the maximum number of countries to return (e.g., 5, 10)
     */
    public void printTopCountriesInContinent(String continent, int topN) {
        try {
            String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                    "FROM country c " +
                    "LEFT JOIN city ci ON c.Capital = ci.ID " +
                    "WHERE c.Continent = ? " +
                    "ORDER BY c.Population DESC " +
                    "LIMIT ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, continent);
            stmt.setInt(2, topN);

            ResultSet rset = stmt.executeQuery();
            System.out.printf("\nTop %d Countries in %s by Population:\n", topN, continent);
            System.out.printf("%-5s %-40s %-15s %-25s %-15s %-20s%n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital");
            System.out.println("----------------------------------------------------------------------------------------------------");

            while (rset.next()) {
                System.out.printf("%-5s %-40s %-15s %-25s %-15d %-20s%n",
                        rset.getString("Code"),
                        rset.getString("Name"),
                        rset.getString("Continent"),
                        rset.getString("Region"),
                        rset.getInt("Population"),
                        rset.getString("Capital"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving top countries in continent: " + e.getMessage());
        }
    }

    /**
     * 6. Top N countries in a region by population
     * Uses LIMIT ? to restrict the number of returned rows.
     *
     * @param region the region for which the top populated countries should be displayed
     * @param topN the maximum number of countries to return (e.g., 5, 10)
     */
    public void printTopCountriesInRegion(String region, int topN) {
        try {
            String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                    "FROM country c " +
                    "LEFT JOIN city ci ON c.Capital = ci.ID " +
                    "WHERE c.Region = ? " +
                    "ORDER BY c.Population DESC " +
                    "LIMIT ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, region);
            stmt.setInt(2, topN);

            ResultSet rset = stmt.executeQuery();
            System.out.printf("\nTop %d Countries in %s by Population:\n", topN, region);
            System.out.printf("%-5s %-40s %-15s %-25s %-15s %-20s%n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital");
            System.out.println("----------------------------------------------------------------------------------------------------");

            while (rset.next()) {
                System.out.printf("%-5s %-40s %-15s %-25s %-15d %-20s%n",
                        rset.getString("Code"),
                        rset.getString("Name"),
                        rset.getString("Continent"),
                        rset.getString("Region"),
                        rset.getInt("Population"),
                        rset.getString("Capital"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving top countries in region: " + e.getMessage());
        }
    }
}
