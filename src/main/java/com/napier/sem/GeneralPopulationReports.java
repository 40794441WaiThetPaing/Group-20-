package com.napier.sem;

import java.sql.*;

public class GeneralPopulationReports {

    private final Connection con;

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
}
