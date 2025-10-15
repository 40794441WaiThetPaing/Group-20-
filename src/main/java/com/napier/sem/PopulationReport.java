package com.napier.sem;

import java.sql.*;

public class PopulationReport {

    private final Connection con;

    public PopulationReport(Connection con) {
        this.con = con;
    }

    /**
     * 1️⃣ Population by Country
     */
    public void printPopulationByCountry() {
        System.out.println("========================================================");
        System.out.println("POPULATION BY COUNTRY");
        System.out.println("========================================================");
        try {
            Statement stmt = con.createStatement();
            String sql = """
                    SELECT c.Name AS Country, 
                           c.Population AS TotalPopulation,
                           SUM(ci.Population) AS CityPopulation,
                           (c.Population - SUM(ci.Population)) AS NonCityPopulation
                    FROM country c
                    LEFT JOIN city ci ON c.Code = ci.CountryCode
                    GROUP BY c.Code
                    ORDER BY c.Population DESC;
                    """;

            ResultSet rs = stmt.executeQuery(sql);
            System.out.printf("%-35s %-15s %-15s %-15s%n",
                    "Country", "Total Pop.", "City Pop.", "Non-City Pop.");
            System.out.println("--------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-35s %-15d %-15d %-15d%n",
                        rs.getString("Country"),
                        rs.getInt("TotalPopulation"),
                        rs.getInt("CityPopulation"),
                        rs.getInt("NonCityPopulation"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving country data: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * 2️⃣ Population by Region
     */
    public void printPopulationByRegion() {
        System.out.println("========================================================");
        System.out.println("POPULATION BY REGION");
        System.out.println("========================================================");
        try {
            Statement stmt = con.createStatement();
            String sql = """
                    SELECT c.Region AS Region,
                           SUM(c.Population) AS TotalPopulation,
                           SUM(ci.Population) AS CityPopulation,
                           (SUM(c.Population) - SUM(ci.Population)) AS NonCityPopulation
                    FROM country c
                    LEFT JOIN city ci ON c.Code = ci.CountryCode
                    GROUP BY c.Region
                    ORDER BY TotalPopulation DESC;
                    """;

            ResultSet rs = stmt.executeQuery(sql);
            System.out.printf("%-30s %-15s %-15s %-15s%n",
                    "Region", "Total Pop.", "City Pop.", "Non-City Pop.");
            System.out.println("--------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-30s %-15d %-15d %-15d%n",
                        rs.getString("Region"),
                        rs.getInt("TotalPopulation"),
                        rs.getInt("CityPopulation"),
                        rs.getInt("NonCityPopulation"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving region data: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * 3️⃣ Population by Continent
     */
    public void printPopulationByContinent() {
        System.out.println("========================================================");
        System.out.println("POPULATION BY CONTINENT");
        System.out.println("========================================================");
        try {
            Statement stmt = con.createStatement();
            String sql = """
                    SELECT c.Continent AS Continent,
                           SUM(c.Population) AS TotalPopulation,
                           SUM(ci.Population) AS CityPopulation,
                           (SUM(c.Population) - SUM(ci.Population)) AS NonCityPopulation
                    FROM country c
                    LEFT JOIN city ci ON c.Code = ci.CountryCode
                    GROUP BY c.Continent
                    ORDER BY TotalPopulation DESC;
                    """;

            ResultSet rs = stmt.executeQuery(sql);
            System.out.printf("%-20s %-15s %-15s %-15s%n",
                    "Continent", "Total Pop.", "City Pop.", "Non-City Pop.");
            System.out.println("--------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-20s %-15d %-15d %-15d%n",
                        rs.getString("Continent"),
                        rs.getInt("TotalPopulation"),
                        rs.getInt("CityPopulation"),
                        rs.getInt("NonCityPopulation"));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving continent data: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * 4️⃣ Language Report — Chinese, English, Hindi, Spanish, Arabic
     */
    public void printLanguageReport() {
        System.out.println("========================================================");
        System.out.println("LANGUAGE REPORT");
        System.out.println("========================================================");

        String[] languages = {"Chinese", "English", "Hindi", "Spanish", "Arabic"};
        try {
            Statement stmt = con.createStatement();
            ResultSet totalPopRs = stmt.executeQuery("SELECT SUM(Population) AS WorldPopulation FROM country");
            totalPopRs.next();
            long worldPop = totalPopRs.getLong("WorldPopulation");

            System.out.printf("%-15s %-20s %-20s%n", "Language", "Total Speakers", "% of World Pop.");
            System.out.println("--------------------------------------------------------------");

            for (String lang : languages) {
                String sql = """
                        SELECT cl.Language, SUM(c.Population * cl.Percentage / 100) AS Speakers
                        FROM countrylanguage cl
                        JOIN country c ON cl.CountryCode = c.Code
                        WHERE cl.Language = ?
                        GROUP BY cl.Language;
                        """;

                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, lang);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    long speakers = rs.getLong("Speakers");
                    double percent = ((double) speakers / worldPop) * 100;
                    System.out.printf("%-15s %-20d %-19.2f%%%n", lang, speakers, percent);
                }
            }

        } catch (Exception e) {
            System.out.println("Error retrieving language data: " + e.getMessage());
        }
        System.out.println();
    }
}
