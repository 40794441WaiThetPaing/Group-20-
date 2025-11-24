package com.napier.sem;

import java.sql.*;

/**
 * Provides methods to retrieve and print population statistics from a database
 * at multiple geographic levels, including world, continent, region, country,
 * district, and city. Handles database access and basic error reporting.
 */

public class GeneralPopulationReports {

    final Connection con;

    public GeneralPopulationReports(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves the total population of the world from the 'country' table.
     */
    public long getTotalWorldPopulation() {
        long totalPopulation = 0;
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT SUM(Population) AS TotalWorldPopulation FROM country;";
            ResultSet rset = stmt.executeQuery(strSelect);
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
     */
    public long getContinentPopulation(String continentName) {
        long continentPopulation = 0;
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT SUM(Population) AS ContinentPopulation " +
                    "FROM country WHERE Continent = '" + continentName + "';";
            ResultSet rset = stmt.executeQuery(strSelect);
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

    /**
     * Retrieves the total population of a given region.
     */
    public long getRegionPopulation(String regionName) {
        long regionPopulation = 0;
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT SUM(Population) AS RegionPopulation " +
                    "FROM country WHERE Region = '" + regionName + "';";
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                regionPopulation = rset.getLong("RegionPopulation");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get region population");
        }
        return regionPopulation;
    }

    /**
     * Prints the total population of a given region.
     */
    public void printRegionPopulation(String regionName, long population) {
        System.out.printf("%-30s %-12d\n", "Population of " + regionName + ":", population);
    }

    /**
     * Retrieves the total population of a given country.
     */
    public long getCountryPopulation(String countryName) {
        long countryPopulation = 0;
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT Population AS CountryPopulation " +
                    "FROM country WHERE Name = '" + countryName + "';";
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                countryPopulation = rset.getLong("CountryPopulation");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country population");
        }
        return countryPopulation;
    }

    /**
     * Prints the total population of a given country.
     */
    public void printCountryPopulation(String countryName, long population) {
        System.out.printf("%-30s %-12d\n", "Population of " + countryName + ":", population);
    }



    /**
     * Retrieves the total population of a given district.
     * Note: District populations come from the `city` table.
     */
    public long getDistrictPopulation(String districtName) {
        long districtPopulation = 0;
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT SUM(Population) AS DistrictPopulation " +
                    "FROM city WHERE District = '" + districtName + "';";
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                districtPopulation = rset.getLong("DistrictPopulation");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get district population");
        }
        return districtPopulation;
    }

    /**
     * Prints the total population of a given district.
     */
    public void printDistrictPopulation(String districtName, long population) {
        System.out.printf("%-30s %-12d\n", "Population of district " + districtName + ":", population);
    }



    /**
     * Retrieves the population of a given city.
     */
    public long getCityPopulation(String cityName) {
        long cityPopulation = 0;
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT Population AS CityPopulation " +
                    "FROM city WHERE Name = '" + cityName + "';";
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                cityPopulation = rset.getLong("CityPopulation");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city population");
        }
        return cityPopulation;
    }

    /**
     * Prints the population of a given city.
     */
    public void printCityPopulation(String cityName, long population) {
        System.out.printf("%-30s %-12d\n", "Population of city " + cityName + ":", population);
    }

}
