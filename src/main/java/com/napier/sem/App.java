package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/world?useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "example"
                );

                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public ArrayList<City> getAllCitiesByPopulation() {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, c.Name, city.District, city.Population\n" +
                            "From city\n" +
                            "         JOIN world.country c on city.CountryCode = c.Code\n" +
                            "ORDER BY Population DESC;";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            while (rset.next()) {
                City city = new City();
                city.Name = rset.getString("Name");
                city.District = rset.getString("District");
                city.Population = rset.getInt("Population");
                cities.add(city);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");

        }
        return cities;
    }
    /**
     * Prints city report in the required format.
     */
    public void printCityReport(ArrayList<City> cities) {
        System.out.printf("%-30s %-30s %-12s\n", "Name", "District", "Population");
        for (City city : cities) {
            System.out.printf("%-30s %-30s %-12d\n", city.Name, city.District, city.Population);
        }
    }

    /**
     * 1. All the countries in the world organised by largest population to smallest.
     *
     */
    public void printCountriesByPopulation() {
        try {
            Statement stmt = con.createStatement();

            // SQL query: join country with city to get the capital name
            String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital
            FROM country c
            LEFT JOIN city ci ON c.Capital = ci.ID
            ORDER BY c.Population DESC;
        """;

            ResultSet rset = stmt.executeQuery(sql);

            // Header
            System.out.printf("%-5s %-40s %-15s %-25s %-15s %-20s%n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital");
            System.out.println("----------------------------------------------------------------------------------------------------");

            // Loop through results
            while (rset.next()) {
                Country country = new Country();
                country.Code = 0; // because your Code is an int (we’ll store it as 0, since Code is actually CHAR in DB)
                country.Name = rset.getString("Name");
                country.Continent = rset.getString("Continent");
                country.Region = rset.getString("Region");
                country.Population = rset.getInt("Population");
                // The capital city name (from join)
                String capitalName = rset.getString("Capital");

                // Print output
                System.out.printf("%-5s %-40s %-15s %-25s %-15d %-20s%n",
                        rset.getString("Code"),
                        country.Name,
                        country.Continent,
                        country.Region,
                        country.Population,
                        capitalName);
            }

        } catch (Exception e) {
            System.out.println("Error retrieving country report: " + e.getMessage());
        }
    }

    /**
     * 2. all countries in a continent sorted by population (descending order).
     * @param continent The name of the continent.
     */
    public void printCountriesByContinent(String continent) {
        try {
            String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                    "FROM country c " +
                    "LEFT JOIN city ci ON c.Capital = ci.ID " +
                    "WHERE c.Continent = ? " +
                    "ORDER BY c.Population DESC";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, continent); // safely sets the continent parameter

            ResultSet rset = stmt.executeQuery();

            System.out.printf("%-5s %-40s %-15s %-25s %-15s %-20s%n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital");
            System.out.println("----------------------------------------------------------------------------------------------------");

            while (rset.next()) {
                String code = rset.getString("Code");
                String name = rset.getString("Name");
                String cont = rset.getString("Continent");
                String region = rset.getString("Region");
                int population = rset.getInt("Population");
                String capital = rset.getString("Capital");

                System.out.printf("%-5s %-40s %-15s %-25s %-15d %-20s%n",
                        code, name, cont, region, population, capital);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * 3. all countries in a given region sorted by population (descending order).
     * @param region The name of the region
     */
    public void printCountriesByRegion(String region) {
        try {
            String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital " +
                    "FROM country c " +
                    "LEFT JOIN city ci ON c.Capital = ci.ID " +
                    "WHERE c.Region = ? " +
                    "ORDER BY c.Population DESC";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, region); // safely set region parameter

            ResultSet rset = stmt.executeQuery();

            System.out.printf("\nCountries in %s (sorted by population):%n", region);
            System.out.printf("%-5s %-40s %-15s %-25s %-15s %-20s%n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital");
            System.out.println("----------------------------------------------------------------------------------------------------");

            while (rset.next()) {
                String code = rset.getString("Code");
                String name = rset.getString("Name");
                String cont = rset.getString("Continent");
                String reg = rset.getString("Region");
                int population = rset.getInt("Population");
                String capital = rset.getString("Capital");

                System.out.printf("%-5s %-40s %-15s %-25s %-15d %-20s%n",
                        code, name, cont, reg, population, capital);
            }

        } catch (Exception e) {
            System.out.println("Error retrieving countries by region: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        // Create new Application
        App a = new App();


        // Connect to database
        a.connect();
        ArrayList<City> cities = a.getAllCitiesByPopulation();
        //a.printCityReport(cities);

        //country report 1
        //a.printCountriesByPopulation();
        //country report 2
        //a.printCountriesByContinent("Asia");
        //country report 3
        a.printCountriesByRegion("Eastern Asia");

        // Disconnect from database
        a.disconnect();
    }
}