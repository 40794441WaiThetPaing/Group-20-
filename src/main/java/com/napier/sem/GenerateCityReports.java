package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * Provides methods for generating various city population reports
 * based on continent, region, country, district, or worldwide.
 * Uses SQL queries to retrieve city and country data from the database.
 */
public class GenerateCityReports {
    private final Connection con;

    /**
     * Creates a new report generator using the given database connection.
     *
     * @param con Active SQL database connection
     */
    public GenerateCityReports(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves all cities in the world sorted by population (highest first).
     *
     * @return A list of {@link City} objects populated with city and country data
     * @throws Exception If the SQL query fails
     */
    public ArrayList<City> getAllCitiesByPopulation() throws Exception {
        String sql = "SELECT city.Name AS CityName, c.Name AS CountryName, c.Continent, c.Region, city.District, city.Population " + "FROM city JOIN world.country c ON city.CountryCode = c.Code " + "ORDER BY city.Population DESC;";

        return executeCityQuery(sql);
    }

    /**
     * Retrieves all cities in a specified continent sorted by population.
     *
     * @param continent The continent name to filter by
     * @return A list of matching {@link City} objects
     * @throws Exception If the SQL query fails
     */
    public ArrayList<City> getCitiesByContinent(String continent) throws Exception {
        String sql = "SELECT city.Name AS CityName, c.Name AS CountryName, c.Continent, c.Region, city.District, city.Population " + "FROM city JOIN world.country c ON city.CountryCode = c.Code WHERE c.Continent = ? " + "ORDER BY city.Population DESC;";

        return executeCityQueryWithParam(sql, continent);
    }

    /**
     * Retrieves all cities in a specified region sorted by population.
     *
     * @param region The region name to filter by
     * @return A list of matching {@link City} objects
     * @throws Exception If the SQL query fails
     */
    public ArrayList<City> getCitiesByRegion(String region) throws Exception {
        String sql = "SELECT city.Name AS CityName, c.Name AS CountryName,c.Continent, c.Region, city.District, city.Population " + "FROM city JOIN world.country c ON city.CountryCode = c.Code WHERE c.Region = ? " + "ORDER BY city.Population DESC;";
        return executeCityQueryWithParam(sql, region);
    }

    /**
     * Retrieves all cities in a specified country sorted by population.
     *
     * @param country The country name to filter by
     * @return A list of matching {@link City} objects
     * @throws Exception If the SQL query fails
     */
    public ArrayList<City> getCitiesByCountry(String country) throws Exception {
        String sql = "SELECT city.Name AS CityName, c.Name AS CountryName, c.Continent, c.Region, city.District, city.Population " + "FROM city JOIN world.country c ON city.CountryCode = c.Code WHERE c.Name = ? " + "ORDER BY city.Population DESC;";
        return executeCityQueryWithParam(sql, country);
    }

    /**
     * Retrieves all cities in a specified district sorted by population.
     *
     * @param district The district name to filter by
     * @return A list of matching {@link City} objects
     * @throws Exception If the SQL query fails
     */
    public ArrayList<City> getCitiesByDistrict(String district) throws Exception {
        String sql = "SELECT city.Name AS CityName, c.Name AS CountryName, c.Continent, c.Region, city.District, city.Population " + "FROM city JOIN world.country c ON city.CountryCode = c.Code WHERE city.District = ? " + "ORDER BY city.Population DESC;";
        return executeCityQueryWithParam(sql, district);
    }


    /**
     * Executes a SQL SELECT query that does not require parameters.
     *
     * @param sql The SQL query to execute
     * @return A list of {@link City} objects created from the result set
     * @throws Exception If an SQL error occurs
     */
    private ArrayList<City> executeCityQuery(String sql) throws Exception {
        ArrayList<City> cities = new ArrayList<>();
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(sql);
        while (rset.next()) {
            cities.add(createCityFromResultSet(rset));
        }

        return cities;
    }

    /**
     * Executes a SQL SELECT query with one parameter.
     *
     * @param sql   SQL query containing one placeholder (?)
     * @param param The value to substitute for the placeholder
     * @return A list of {@link City} objects created from the result set
     * @throws Exception If an SQL error occurs
     */
    private ArrayList<City> executeCityQueryWithParam(String sql, String param) throws Exception {
        ArrayList<City> cities = new ArrayList<>();
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, param);
        ResultSet rset = stmt.executeQuery();
        while (rset.next()) {
            cities.add(createCityFromResultSet(rset));
        }

        return cities;
    }

    /**
     * Creates a {@link City} object populated with values from a SQL ResultSet.
     *
     * @param rset The active ResultSet row
     * @return A fully populated {@link City} object
     * @throws Exception If reading fields from the ResultSet fails
     */
    private City createCityFromResultSet(ResultSet rset) throws Exception {
        City city = new City();
        city.setName(rset.getString("CityName"));
        city.setDistrict(rset.getString("District"));
        city.setPopulation(rset.getInt("Population"));

        Country country = new Country();
        country.setName(rset.getString("CountryName"));
        country.setContinent(rset.getString("Continent"));  // Set Continent
        country.setRegion(rset.getString("Region"));  // NEW: set region here
        city.setCountry(country);
        return city;
    }

    /**
     * Prints a formatted city report to the console.
     *
     * @param cities List of cities to print. If null, a message is shown instead.
     */
    public void printCityReport(ArrayList<City> cities) {
        if (cities == null) {
            System.out.println("No cities");
            return;
        }

        System.out.printf("%-30s %-30s %-30s %-12s%n", "Name", "Country", "District", "Population");

        for (City city : cities) {
            if (city == null) continue;

            String countryName = (city.getCountry() != null && city.getCountry().getName() != null) ? city.getCountry().getName() : "Unknown";

            System.out.printf("%-30s %-30s %-30s %-12d%n", city.getName(), countryName, city.getDistrict(), city.getPopulation());
        }
    }

}
