package com.napier.sem;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CapitalCityReportIntegrationTest {

    static App app;
    static CapitalCityReport report;

    @BeforeAll
    static void init() throws Exception {
        app = new App();

        // IMPORTANT — matches Docker container port mapping from SEM coursework
        app.connect("localhost:33060", 30000);

        assertNotNull(app.con, "Database connection failed!");

        report = new CapitalCityReport(app.con);
    }

    @AfterAll
    static void cleanup() throws Exception {
        app.disconnect();
    }


    // ───────────────────────────────────────────────────────────────
    // 1. Test all capital cities in the world
    // ───────────────────────────────────────────────────────────────
    @Test
    @Order(1)
    void testPrintAllCapitalCitiesWorld() {
        assertDoesNotThrow(() -> report.printAllCapitalCitiesWorld());
    }

    // ───────────────────────────────────────────────────────────────
    // 2. Test capital cities by continent
    // ───────────────────────────────────────────────────────────────
    @Test
    @Order(2)
    void testCapitalCitiesByContinent() {
        assertDoesNotThrow(() -> report.printCapitalCitiesByContinent("Asia"));
    }

    // ───────────────────────────────────────────────────────────────
    // 3. Test capital cities by region
    // ───────────────────────────────────────────────────────────────
    @Test
    @Order(3)
    void testCapitalCitiesByRegion() {
        assertDoesNotThrow(() -> report.printCapitalCitiesByRegion("Western Europe"));
    }

    // ───────────────────────────────────────────────────────────────
    // 4. Test Top N capital cities in the world
    // ───────────────────────────────────────────────────────────────
    @Test
    @Order(4)
    void testTopNCapitalCitiesWorld() {
        assertDoesNotThrow(() -> report.printTopNCapitalCitiesWorld(5));
    }

    // ───────────────────────────────────────────────────────────────
    // 5. Test Top N capital cities by continent
    // ───────────────────────────────────────────────────────────────
    @Test
    @Order(5)
    void testTopNCapitalCitiesByContinent() {
        assertDoesNotThrow(() -> report.printTopNCapitalCitiesByContinent("Europe", 5));
    }

    // ───────────────────────────────────────────────────────────────
    // 6. Test Top N capital cities by region
    // ───────────────────────────────────────────────────────────────
    @Test
    @Order(6)
    void testTopNCapitalCitiesByRegion() {
        assertDoesNotThrow(() -> report.printTopNCapitalCitiesByRegion("Eastern Asia", 5));
    }

    // ───────────────────────────────────────────────────────────────
    // 7. Confirm connection is still open
    // ───────────────────────────────────────────────────────────────
    @Test
    @Order(7)
    void testConnectionStillValid() throws Exception {
        assertFalse(app.con.isClosed(), "Connection is closed unexpectedly!");
    }
}
