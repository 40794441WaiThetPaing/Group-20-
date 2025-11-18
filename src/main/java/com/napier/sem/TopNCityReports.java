package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class TopNCityReports {

    /**
     * Database connection used to execute queries
     */
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
                city.setName(rset.getString("CityName"));

                Country country = new Country();
                country.setName(rset.getString("CountryName"));
                city.setCountry(country);

                city.setDistrict(rset.getString("District"));
                city.setPopulation(rset.getInt("Population"));

                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return cities;
    }

    /**
     * Retrieves the top N most populated cities in the world.
     */
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

    /**
     * Retrieves the top N most populated cities in continent.
     */
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

    /**
     * Retrieves the top N most populated cities in region.
     */
    public ArrayList<City> getTopNCitiesInRegion(String region, int n) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Region = ?
            ORDER BY city.Population DESC
            LIMIT ?;
            """;
        return executeCityQuery(query, region, n);
    }

    /**
     * Retrieves the top N most populated cities in country.
     */
    public ArrayList<City> getTopNCitiesInCountry(String countryName, int n) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Name = ?
            ORDER BY city.Population DESC
            LIMIT ?;
            """;
        return executeCityQuery(query, countryName, n);
    }

    /**
     * Retrieves the top N most populated cities in district.
     */
    public ArrayList<City> getTopNCitiesInDistrict(String district, int n) {
        String query = """
            SELECT city.Name AS CityName, country.Name AS CountryName, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE city.District = ?
            ORDER BY city.Population DESC
            LIMIT ?;
            """;
        return executeCityQuery(query, district, n);
    }


    /**
     * Print cities reports with city name, country, district and population in columns
     */
    public void printCityReport(ArrayList<City> cities, String title) {
        System.out.println("\n" + title);
        System.out.printf("%-30s %-30s %-30s %-12s%n", "City Name", "Country", "District", "Population");
        for (City city : cities) {
            String countryName = (city.getCountry() != null && city.getCountry().getName() != null) ? city.getCountry().getName() : "Unknown";
            System.out.printf("%-30s %-30s %-30s %-12d%n",
                    city.getName(), countryName, city.getDistrict(), city.getPopulation());
        }
    }
}


