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

    /**
     * Retrieves the total population of the world from the 'country' table.
     */
    public long getTotalWorldPopulation() {
        long totalPopulation = 0;
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT SUM(Population) AS TotalWorldPopulation\n" +
                    "FROM country;";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Retrieve result
            if (rset.next()) {
                totalPopulation = rset.getLong("TotalWorldPopulation");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get total world population");
        }
        return totalPopulation;
    }

    /**
     * Prints the total world population.
     */
    public void printTotalWorldPopulation(long population) {
        System.out.printf("%-30s %-12d\n", "Total World Population:", population);
    }

    /**
     * Retrieves the total population of a given continent.
     * Example: "North America"
     */
    public long getContinentPopulation(String continentName) {
        long continentPopulation = 0;
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // SQL query to sum population for the given continent
            String strSelect = "SELECT SUM(Population) AS ContinentPopulation " +
                    "FROM country WHERE Continent = '" + continentName + "';";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Retrieve result
            if (rset.next()) {
                continentPopulation = rset.getLong("ContinentPopulation");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get continent population");
        }
        return continentPopulation;
    }

    /**
     * Prints the total population of a given continent.
     */
    public void printContinentPopulation(String continentName, long population) {
        System.out.printf("%-30s %-12d\n", "Population of " + continentName + ":", population);
    }


    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        App a = new App();

        // Connect to database
        a.connect();

        // Get and print total world population
        long totalPopulation = a.getTotalWorldPopulation();
        a.printTotalWorldPopulation(totalPopulation);

        // Get and print total population for North America
        long northAmericaPop = a.getContinentPopulation("North America");
        a.printContinentPopulation("North America", northAmericaPop);

        // Disconnect from database
        a.disconnect();
    }
}