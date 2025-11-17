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

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

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


        /**
         * user-defined number of top cities to display
         */

        // Create instance of report generator
        GeneralPopulationReports reports = new GeneralPopulationReports(a.con);

        // Get and print total world population
        long totalPopulation = reports.getTotalWorldPopulation();
        reports.printTotalWorldPopulation(totalPopulation);

        // Get and print total population for North America
        long northAmericaPop = reports.getContinentPopulation("North America");
        reports.printContinentPopulation("North America", northAmericaPop);

        // Get and print total population for Caribbean
        long caribbeanPop = reports.getRegionPopulation("Caribbean");
        reports.printRegionPopulation("Caribbean", caribbeanPop);


        PopulationReport reportpp = new PopulationReport(a.con);
        // 1. Population by Country
        reportpp.printPopulationByCountry();

        // 2. Population by Region
        reportpp.printPopulationByRegion();

        // 3. Population by Continent
        reportpp.printPopulationByContinent();

        // 4. Language Report
        reportpp.printLanguageReport();


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


        int n = 10; // user-defined number

        /**
         * Create report object with database connection
         */
        TopNCityReports report = new TopNCityReports(a.con);

        /**
         * Get and Print top N cities in world
         */
        ArrayList<City> worldCities = report.getTopNCitiesInWorld(n);
        report.printCityReport(worldCities, "Top " + n + " Populated Cities in the World");

        /**
         * Get and Print top N cities in Asia
         */
        ArrayList<City> continentCities = report.getTopNCitiesInContinent("Asia", n);
        report.printCityReport(continentCities, "Top " + n + " Populated Cities in Asia");


        /**
         * Get and Print top N cities in Eastern Asia
         */
        ArrayList<City> regionCities = report.getTopNCitiesInRegion("Eastern Asia", n);
        report.printCityReport(regionCities, "Top " + n + " Populated Cities in Eastern Asia");

        /**
         * Get and Print top N cities in Japan
         */
        ArrayList<City> countryCities = report.getTopNCitiesInCountry("Japan", n);
        report.printCityReport(countryCities, "Top " + n + " Populated Cities in Japan");

        /**
         * Get and Print top N cities in Tokyo
         */
        ArrayList<City> districtCities = report.getTopNCitiesInDistrict("Tokyo-to", n);
        report.printCityReport(districtCities, "Top " + n + " Populated Cities in Tokyo-to");


    // Generate City Report
        GenerateCityReports gcr = new GenerateCityReports(a.con);

        //Example: All cities in the world
        ArrayList<City> allcities = gcr.getAllCitiesByPopulation();
        gcr.printCityReport(allcities);

        //Example: Cities by continent
        ArrayList<City> asianCities = gcr.getCitiesByContinent("Asia");
        gcr.printCityReport(asianCities);

        // Example: Cities by region
        ArrayList<City> westernEuropeCities = gcr.getCitiesByRegion("Western Europe");
        gcr.printCityReport(westernEuropeCities);

        // Example: Cities by country
        ArrayList<City> japaneseCities = gcr.getCitiesByCountry("Japan");
        gcr.printCityReport(japaneseCities);

        // Example: Cities by district
        ArrayList<City> englandCities = gcr.getCitiesByDistrict("England");
        gcr.printCityReport(englandCities);

        // Disconnect from database
        a.disconnect();
    }
}