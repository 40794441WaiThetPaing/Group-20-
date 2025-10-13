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


    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        // Create new Application
        App a = new App();


        // Connect to database
        a.connect();
        ArrayList<City> cities = a.getAllCitiesByPopulation();
        //a.printCityReport(cities);

        // Create CountryReport object with the connection
        CountryReport cr = new CountryReport(a.con);

        // 1. All countries by population
        cr.printCountriesByPopulation();

        // 2. All countries in a continent
        cr.printCountriesByContinent("Asia");

        // 3. All countries in a region
        cr.printCountriesByRegion("Eastern Asia");

        // 4. Top N countries in the world
        cr.printTopCountriesByPopulation(10);

        // 5. Top N countries in a continent
        cr.printTopCountriesInContinent("Asia", 10);

        // 6. Top N countries in a region
        cr.printTopCountriesInRegion("Southern Europe", 10);

        // Disconnect from database
        a.disconnect();
    }
}