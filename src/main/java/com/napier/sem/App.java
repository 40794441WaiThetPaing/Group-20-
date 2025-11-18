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
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        App a = new App();

        // Connect to database
        a.connect();

        // Create instance of report generator
        GeneralPopulationReports reports = new GeneralPopulationReports(a.con);

        // Get and print total world population
        long totalPopulation = reports.getTotalWorldPopulation();
        reports.printTotalWorldPopulation(totalPopulation);

        // Get and print total population for North America (continent)
        long northAmericaPop = reports.getContinentPopulation("North America");
        reports.printContinentPopulation("North America", northAmericaPop);

        // Get and print total population for Caribbean (region)
        long caribbeanPop = reports.getRegionPopulation("Caribbean");
        reports.printRegionPopulation("Caribbean", caribbeanPop);

        // Get and print population for United Kingdom (country)
        long ukPop = reports.getCountryPopulation("United Kingdom");
        reports.printCountryPopulation("United Kingdom", ukPop);

        // Get and print population for California (district)
        long californiaPop = reports.getDistrictPopulation("California");
        reports.printDistrictPopulation("California", californiaPop);

        // Get and print population for London (city)
        long londonPop = reports.getCityPopulation("London");
        reports.printCityPopulation("London", londonPop);


        // Disconnect from database
        a.disconnect();
    }
}
