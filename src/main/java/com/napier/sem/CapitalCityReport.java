package com.napier.sem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CapitalCityReport {
    private Connection con;

    // Constructor to initialize the database connection
    public CapitalCityReport(Connection con) {
        this.con = con;
    }

    /**
     * 1. All capital cities in the world organized by largest population to smallest
     */
    public void printAllCapitalCitiesWorld() {
        try {
            String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.ID = country.Capital " +
                    "ORDER BY city.Population DESC";

            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(sql);

            System.out.printf("%-30s %-40s %-15s%n", "Capital", "Country", "Population");
            System.out.println("--------------------------------------------------------------------------");

            while (rset.next()) {
                System.out.printf("%-30s %-40s %-15d%n",
                        rset.getString("Capital"),
                        rset.getString("Country"),
                        rset.getInt("Population"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving capital cities: " + e.getMessage());
        }
    }

    /**
     * 2. All capital cities in a continent sorted by population
     */
    public void printCapitalCitiesByContinent(String continent) {
        try {
            String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.ID = country.Capital " +
                    "WHERE country.Continent = ? " +
                    "ORDER BY city.Population DESC";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, continent);
            ResultSet rset = stmt.executeQuery();

            System.out.printf("\nCapital cities in %s (sorted by population):%n", continent);
            System.out.printf("%-30s %-40s %-15s%n", "Capital", "Country", "Population");
            System.out.println("--------------------------------------------------------------------------");

            while (rset.next()) {
                System.out.printf("%-30s %-40s %-15d%n",
                        rset.getString("Capital"),
                        rset.getString("Country"),
                        rset.getInt("Population"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving capitals by continent: " + e.getMessage());
        }
    }

    /**
     * 3. All capital cities in a region sorted by population
     */
    public void printCapitalCitiesByRegion(String region) {
        try {
            String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.ID = country.Capital " +
                    "WHERE country.Region = ? " +
                    "ORDER BY city.Population DESC";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, region);
            ResultSet rset = stmt.executeQuery();

            System.out.printf("\nCapital cities in %s (sorted by population):%n", region);
            System.out.printf("%-30s %-40s %-15s%n", "Capital", "Country", "Population");
            System.out.println("--------------------------------------------------------------------------");

            while (rset.next()) {
                System.out.printf("%-30s %-40s %-15d%n",
                        rset.getString("Capital"),
                        rset.getString("Country"),
                        rset.getInt("Population"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving capitals by region: " + e.getMessage());
        }
    }

    /**
     * 4. Top N capital cities in the world by population
     */
    public void printTopNCapitalCitiesWorld(int topN) {
        try {
            String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.ID = country.Capital " +
                    "ORDER BY city.Population DESC " +
                    "LIMIT ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, topN);
            ResultSet rset = stmt.executeQuery();

            System.out.printf("\nTop %d Capital Cities in the World:\n", topN);
            System.out.printf("%-30s %-40s %-15s%n", "Capital", "Country", "Population");
            System.out.println("--------------------------------------------------------------------------");

            while (rset.next()) {
                System.out.printf("%-30s %-40s %-15d%n",
                        rset.getString("Capital"),
                        rset.getString("Country"),
                        rset.getInt("Population"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving top capital cities: " + e.getMessage());
        }
    }

    /**
     * 5. Top N capital cities in a continent by population
     */
    public void printTopNCapitalCitiesByContinent(String continent, int topN) {
        try {
            String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.ID = country.Capital " +
                    "WHERE country.Continent = ? " +
                    "ORDER BY city.Population DESC " +
                    "LIMIT ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, continent);
            stmt.setInt(2, topN);
            ResultSet rset = stmt.executeQuery();

            System.out.printf("\nTop %d Capital Cities in %s:\n", topN, continent);
            System.out.printf("%-30s %-40s %-15s%n", "Capital", "Country", "Population");
            System.out.println("--------------------------------------------------------------------------");

            while (rset.next()) {
                System.out.printf("%-30s %-40s %-15d%n",
                        rset.getString("Capital"),
                        rset.getString("Country"),
                        rset.getInt("Population"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving top capitals in continent: " + e.getMessage());
        }
    }

    /**
     * 6. Top N capital cities in a region by population
     */
    public void printTopNCapitalCitiesByRegion(String region, int topN) {
        try {
            String sql = "SELECT city.Name AS Capital, country.Name AS Country, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.ID = country.Capital " +
                    "WHERE country.Region = ? " +
                    "ORDER BY city.Population DESC " +
                    "LIMIT ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, region);
            stmt.setInt(2, topN);
            ResultSet rset = stmt.executeQuery();

            System.out.printf("\nTop %d Capital Cities in %s:\n", topN, region);
            System.out.printf("%-30s %-40s %-15s%n", "Capital", "Country", "Population");
            System.out.println("--------------------------------------------------------------------------");

            while (rset.next()) {
                System.out.printf("%-30s %-40s %-15d%n",
                        rset.getString("Capital"),
                        rset.getString("Country"),
                        rset.getInt("Population"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving top capitals in region: " + e.getMessage());
        }
    }
}

