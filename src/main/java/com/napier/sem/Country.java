package com.napier.sem;

/**
 * Represents a country in the world database.
 */
public class Country {
    /**
     * The unique country code.
     */
    private int code;

    /**
     * The official name of the country.
     */
    private String name;

    /**
     * The continent where the country is located.
     */
    private String continent;

    /**
     * The geographical region of the country.
     */
    private String region;

    /**
     * The total population of the country.
     */
    private int population;

    /**
     * The ID of the capital city.
     */
    private int capital;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

}
