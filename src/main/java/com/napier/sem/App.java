package com.napier.sem;

import java.sql.*;

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
                System.out.println("Failed to connect to database attempt " + i);
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
                System.out.println("Disconnected from database.");
            } catch (Exception e) {
                System.out.println("Error closing connection to database: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Check if connection was successful
        if (a.con != null) {
            // Create CapitalCityReport instance
            CapitalCityReport capitalReport = new CapitalCityReport(a.con);

            // === CAPITAL CITY REPORTS ===
            // 1. All capital cities in the world sorted by population
            capitalReport.printAllCapitalCitiesWorld();

            // 2. All capital cities in a continent (e.g., "Asia")
            capitalReport.printCapitalCitiesByContinent("Asia");

            // 3. All capital cities in a region (e.g., "Western Europe")
            capitalReport.printCapitalCitiesByRegion("Western Europe");

            // 4. Top N capital cities in the world (e.g., Top 10)
            capitalReport.printTopNCapitalCitiesWorld(10);

            // 5. Top N capital cities in a continent (e.g., Top 5 in Africa)
            capitalReport.printTopNCapitalCitiesByContinent("Africa", 5);

            // 6. Top N capital cities in a region (e.g., Top 3 in Southern Europe)
            capitalReport.printTopNCapitalCitiesByRegion("Southern Europe", 3);
        } else {
            System.out.println("Connection failed. Reports not generated.");
        }

        // Disconnect from database
        a.disconnect();
    }
}
