package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GenerateCityReports {

    private final Connection con;

    public GenerateCityReports(Connection con) {
        this.con = con;
    }

    public ArrayList<City> getAllCitiesByPopulation() {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT city.Name AS CityName, c.Name AS CountryName, city.District, city.Population " +
                            "FROM city JOIN world.country c ON city.CountryCode = c.Code " +
                            "ORDER BY city.Population DESC;";
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) {
                City city = new City();
                city.Name = rset.getString("CityName");
                city.District = rset.getString("District");
                city.Population = rset.getInt("Population");

                Country country = new Country();
                country.Name = rset.getString("CountryName");
                city.Country = country;

                cities.add(city);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
        }
        return cities;
    }

    public ArrayList<City> getCitiesByContinent(String continent) {
        ArrayList<City> cities = new ArrayList<>();
        try {
            String strSelect =
                    "SELECT city.Name AS CityName, c.Name AS CountryName, city.District, city.Population " +
                            "FROM city JOIN world.country c ON city.CountryCode = c.Code " +
                            "WHERE c.Continent = ? " +
                            "ORDER BY city.Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);
            stmt.setString(1, continent);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                City city = new City();
                city.Name = rset.getString("CityName");
                city.District = rset.getString("District");
                city.Population = rset.getInt("Population");
                Country country = new Country();
                country.Name = rset.getString("CountryName");
                city.Country = country;
                cities.add(city);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
        }
        return cities;
    }


    /**
     * Prints city report in the given format.
     */
    public void printCityReport(ArrayList<City> cities) {
        System.out.printf("%-30s %-30s %-30s %-12s%n", "Name", "Country", "District", "Population");
        for (City city : cities) {
            String countryName = (city.Country != null && city.Country.Name != null) ? city.Country.Name : "Unknown";
            System.out.printf("%-30s %-30s %-30s %-12d%n", city.Name, countryName, city.District, city.Population);
        }
    }
}
