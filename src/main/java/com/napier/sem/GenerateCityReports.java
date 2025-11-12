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

    // 1. All cities in world sorted by population
    public ArrayList<City> getAllCitiesByPopulation() {
        String sql = "SELECT city.Name AS CityName, c.Name AS CountryName, city.District, city.Population " +
                "FROM city JOIN world.country c ON city.CountryCode = c.Code " +
                "ORDER BY city.Population DESC;";
        return executeCityQuery(sql);
    }

    // 2. All cities in a continent sorted by population
    public ArrayList<City> getCitiesByContinent(String continent) {
        String sql = "SELECT city.Name AS CityName, c.Name AS CountryName, city.District, city.Population " +
                "FROM city JOIN world.country c ON city.CountryCode = c.Code WHERE c.Continent = ? " +
                "ORDER BY city.Population DESC;";
        return executeCityQueryWithParam(sql, continent);
    }

    // 3. All cities in a region sorted by population
    public ArrayList<City> getCitiesByRegion(String region) {
        String sql = "SELECT city.Name AS CityName, c.Name AS CountryName, city.District, city.Population " +
                "FROM city JOIN world.country c ON city.CountryCode = c.Code WHERE c.Region = ? " +
                "ORDER BY city.Population DESC;";
        return executeCityQueryWithParam(sql, region);
    }

    // 4. All cities in country sorted by population
    public ArrayList<City> getCitiesByCountry(String country) {
        String sql = "SELECT city.Name AS CityName, c.Name AS CountryName, city.District, city.Population " +
                "FROM city JOIN world.country c ON city.CountryCode = c.Code WHERE c.Name = ? " +
                "ORDER BY city.Population DESC;";
        return executeCityQueryWithParam(sql, country);
    }

    // 5. All cities in a district sorted by population
    public ArrayList<City> getCitiesByDistrict(String district) {
        String sql = "SELECT city.Name AS CityName, c.Name AS CountryName, city.District, city.Population " +
                "FROM city JOIN world.country c ON city.CountryCode = c.Code WHERE city.District = ? " +
                "ORDER BY city.Population DESC;";
        return executeCityQueryWithParam(sql, district);
    }


    // Helper to execute queries without params, no limit
    private ArrayList<City> executeCityQuery(String sql) {
        ArrayList<City> cities = new ArrayList<>();
        try (Statement stmt = con.createStatement()){
            ResultSet rset = stmt.executeQuery(sql);
            while(rset.next()) {
                cities.add(createCityFromResultSet(rset));
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
        }
        return cities;
    }

    // Helper to execute queries with one parameter, no limit
    private ArrayList<City> executeCityQueryWithParam(String sql, String param) {
        ArrayList<City> cities = new ArrayList<>();
        try(PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, param);
            ResultSet rset = stmt.executeQuery();
            while(rset.next()) {
                cities.add(createCityFromResultSet(rset));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
        }
        return cities;
    }


    private City createCityFromResultSet(ResultSet rset) throws Exception {
        City city = new City();
        city.setName(rset.getString("CityName"));
        city.setDistrict(rset.getString("District"));
        city.setPopulation(rset.getInt("Population"));
        Country country = new Country();
        country.setName(rset.getString("CountryName"));
        city.setCountry(country);
        return city;
    }

    public void printCityReport(ArrayList<City> cities) {
        System.out.printf("%-30s %-30s %-30s %-12s%n", "Name", "Country", "District", "Population");
        for (City city : cities)
        {
            String countryName = (city.getCountry() != null && city.getCountry().getName() != null) ? city.getCountry().getName() : "Unknown";
            System.out.printf("%-30s %-30s %-30s %-12d%n",
                    city.getName(), countryName, city.getDistrict(), city.getPopulation());
        }
    }
}
