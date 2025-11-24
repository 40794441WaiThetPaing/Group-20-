package com.napier.sem;

/**
 * Represents a city in the world database.
 * A city contains an ID, name, district, population,
 * and an associated country.
 */
public class City {

    /**
     * Unique ID of the city.
     */
    private int id;

    /**
     * Name of the city.
     */
    private String name;

    /**
     * District or administrative division the city belongs to.
     */
    private String district;

    /**
     * Population of the city.
     */
    private int population;

    /**
     * Country object representing the country the city belongs to.
     */
    private Country country;

    // --- Getters and Setters ---

    /**
     * Gets the city's unique ID.
     *
     * @return City ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the city's unique ID.
     *
     * @param id City ID value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the city's name.
     *
     * @return City name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the city's name.
     *
     * @param name Name of the city
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the district the city belongs to.
     *
     * @return District name
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the district the city belongs to.
     *
     * @param district District name
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * Gets the population of the city.
     *
     * @return Population count
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Sets the population of the city.
     *
     * @param population Number of people living in the city
     */
    public void setPopulation(int population) {
        this.population = population;
    }

    /**
     * Gets the country the city belongs to.
     *
     * @return Country object
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets the country the city belongs to.
     *
     * @param country Country object
     */
    public void setCountry(Country country) {
        this.country = country;
    }
}