package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main application class responsible for connecting to the database,
 * running reports, and controlling the program workflow.
 */
public class App {
    /**
     * Active MySQL database connection.
     */
    Connection con = null;
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    /**
     * Connects to a MySQL database using the provided location and delay time.
     * Attempts a maximum of 10 retries before giving up.
     *
     * @param location the host and port of the MySQL server
     * @param delay    time to wait between retries (milliseconds)
     */
    public void connect(String location, int delay) throws Exception {
        // Load Database driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        int retries = 10;
        int attempt = 0;
        boolean connected = false;

        while (attempt < retries && !connected) {
            LOGGER.info("Connecting to database..."); // PMD-compliant
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.fine("Attempt " + (attempt + 1));
            }

            // Wait a bit for db to start
            Thread.sleep(delay);

            try {
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");

                connected = true; // connection successful
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.fine("Successfully connected");
                }

            } catch (SQLException e) {
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.fine("Failed to connect to database on attempt " + (attempt + 1));
                    LOGGER.fine(e.getMessage());
                }
                attempt++; // increment retry count
            }
        }

        if (!connected) {
            throw new SQLException("Could not connect to database after " + retries + " attempts.");
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() throws Exception {
        if (con != null) {
            con.close();
        }
    }

    /**
     * Application entry point. Establishes a database connection,
     * generates city reports, and then disconnects.
     *
     * @throws Exception if a report query fails
     */
    public static void main(String[] args) throws Exception {
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine(System.getProperty("java.class.path"));
        }
        // Create new Application
        App a = new App();

        // Connect to database
        if (args.length < 1) {
            a.connect("localhost:33060", 30000);
        } else {
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        /**
         * The CountryReport class is used to generate different types of population-based reports.
         *
         * Each method call performs a specific SQL query and prints the results.
         */
        // Create CountryReport object with the connection
        CountryReport cr = new CountryReport(a.con);
        /**
         * 1. All countries by population
         * This shows all countries are sorting by population largest to smallest
         */
        cr.printCountriesByPopulation();

        /**
         * 2. All countries in a continent
         * Here, "Asia" is used as the filter, and results are sorted by population.
         */
        cr.printCountriesByContinent("Asia");

        /**
         * 3. All countries in a region
         * "Eastern Asia" is used as the region filter.
         */
        cr.printCountriesByRegion("Eastern Asia");

        /**
         * 4. Top N countries in the world
         * N = 10, so it prints the top 10 most populated countries.
         */
        cr.printTopCountriesByPopulation(10);

        /**
         * 5. Top N countries in a continent
         * This prints the 10 most populated countries in Asia.
         */
        cr.printTopCountriesInContinent("Asia", 10);

        /**
         * 6. Top N countries in a region
         * This prints the top 10 most populated countries in "Southern Europe".
         */
        cr.printTopCountriesInRegion("Southern Europe", 10);

        /**
         * user-defined number of top cities to display
         */

        /**
         * General Population Reports
         */
        GeneralPopulationReports reports = new GeneralPopulationReports(a.con);

        /**
         * Get and print total world population
         */
        long totalPopulation = reports.getTotalWorldPopulation();
        reports.printTotalWorldPopulation(totalPopulation);

        /**
         * Get and print total population for North America (continent)
         */
        long northAmericaPop = reports.getContinentPopulation("North America");
        reports.printContinentPopulation("North America", northAmericaPop);

        /**
         * Get and print total population for the Caribbean (region)
         */
        long caribbeanPop = reports.getRegionPopulation("Caribbean");
        reports.printRegionPopulation("Caribbean", caribbeanPop);

        /**
         * Get and print population for the United Kingdom (country)
         */
        long ukPop = reports.getCountryPopulation("United Kingdom");
        reports.printCountryPopulation("United Kingdom", ukPop);

        /**
         * Get and print population for California (district)
         */
        long californiaPop = reports.getDistrictPopulation("California");
        reports.printDistrictPopulation("California", californiaPop);

        /**
         * Get and print population for London (city)
         */
        long londonPop = reports.getCityPopulation("London");
        reports.printCityPopulation("London", londonPop);

        PopulationReport reportpp = new PopulationReport(a.con);
        /** 1. Population by Country */
        reportpp.printPopulationByCountry();

        /** 2. Population by Region */
        reportpp.printPopulationByRegion();

        /** 3. Population by Continent */
        reportpp.printPopulationByContinent();

        /** 4. Language Report */
        reportpp.printLanguageReport();

        /** Check if connection was successful */
        if (a.con != null) {
            /** Create CapitalCityReport */
            CapitalCityReport capitalReport = new CapitalCityReport(a.con);

            /** CAPITAL CITY REPORTS */
            /** All capital cities in the world sorted by population */
            capitalReport.printAllCapitalCitiesWorld();

            /** All capital cities in a continent (e.g., "Asia") */
            capitalReport.printCapitalCitiesByContinent("Asia");

            /** All capital cities in a region (e.g., "Western Europe") */
            capitalReport.printCapitalCitiesByRegion("Western Europe");

            /** Top N capital cities in the world (e.g., Top 10) */
            capitalReport.printTopNCapitalCitiesWorld(10);

            /** Top N capital cities in a continent (e.g., Top 5 in Africa) */
            capitalReport.printTopNCapitalCitiesByContinent("Africa", 5);

            /** Top N capital cities in a region (e.g., Top 3 in Southern Europe) */
            capitalReport.printTopNCapitalCitiesByRegion("Southern Europe", 3);
        } else if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("Connection failed. Reports not generated.");
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

        ArrayList<City> allcities = gcr.getAllCitiesByPopulation();
        gcr.printCityReport(allcities);

        ArrayList<City> asianCities = gcr.getCitiesByContinent("Asia");
        gcr.printCityReport(asianCities);

        ArrayList<City> westernEuropeCities = gcr.getCitiesByRegion("Western Europe");
        gcr.printCityReport(westernEuropeCities);

        ArrayList<City> japaneseCities = gcr.getCitiesByCountry("Japan");
        gcr.printCityReport(japaneseCities);

        ArrayList<City> englandCities = gcr.getCitiesByDistrict("England");
        gcr.printCityReport(englandCities);

        // Disconnect from database
        a.disconnect();
    }
}
