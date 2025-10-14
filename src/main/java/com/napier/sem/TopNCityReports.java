package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class TopNCityReports {

    private Connection con;

    // Constructor
    public TopNCityReports(Connection con) {
        this.con = con;
    }

    // Generic query executor
    private ArrayList<City> executeCityQuery(String query, Object... params) {
        ArrayList<City> cities = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                City city = new City();
                city.Name = rset.getString("CityName");

                Country country = new Country();
                country.Name = rset.getString("CountryName");
                city.Country = country;

                city.District = rset.getString("District");
                city.Population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return cities;
    }

    public ArrayList<City> getTopNCitiesInWorld(int n) {
        String query = """
                SELECT city.Name AS CityName, country.Name AS CountryName, city.District, city.Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                ORDER BY city.Population DESC
                LIMIT ?;
                """;
        return executeCityQuery(query, n);
    }

    public ArrayList<City> getTopNCitiesInContinent(String continent, int n) {
        String query = """
                SELECT city.Name AS CityName, country.Name AS CountryName, city.District, city.Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE country.Continent = ?
                ORDER BY city.Population DESC
                LIMIT ?;
                """;
        return executeCityQuery(query, continent, n);
    }

    public void printCityReport(ArrayList<City> cities, String title) {
        System.out.println("\n" + title);
        System.out.printf("%-30s %-30s %-30s %-12s%n", "City Name", "Country", "District", "Population");
        for (City city : cities) {
            String countryName = (city.Country != null && city.Country.Name != null) ? city.Country.Name : "Unknown";
            System.out.printf("%-30s %-30s %-30s %-12d%n",
                    city.Name, countryName, city.District, city.Population);
        }
    }
}


