package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Provides methods for generating various country population reports
 * based on continent, region, country, district, or worldwide.
 * Uses SQL queries to retrieve country data from the database.
 */
public class CountryReport {

    private Connection con;

    // Constructor takes database connection
    public CountryReport(Connection con) {
        this.con = con;
    }

    /**
     * 1. All countries in the world organised by largest population to smallest.
     */
    public void printCountriesByPopulation() {
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                    "FROM country c " +
                    "LEFT JOIN city ci ON c.Capital = ci.ID " +
                    "ORDER BY c.Population DESC";

            ResultSet rset = stmt.executeQuery(sql);

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
            System.out.println("Error retrieving country report: " + e.getMessage());
        }
    }

    /**
     * 2. All countries in a continent sorted by population
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

            ResultSet rset = stmt.executeQuery();
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
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * 3. All countries in a given region sorted by population
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
            System.out.printf("\nCountries in %s (sorted by population):%n", region);
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
            System.out.println("Error retrieving countries by region: " + e.getMessage());
        }
    }

    /**
     * 4. Top N countries in the world by population
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
