package com.napier.sem;

import org.junit.jupiter.api.*;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration Tests for App Class.
 * Ensures the application connects to the database and can run its workflow.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppIntegrationTest {

    private static App app;

    @BeforeAll
    public static void setup() {
        app = new App();
    }

    // ---------------------------------------------------
    // 1. Test Database Connection
    // ---------------------------------------------------
    @Test
    @Order(1)
    public void testDatabaseConnection() {
        app.connect("localhost:33060", 1000);
        assertNotNull(app.con, "Connection should not be null after connect()");
    }

    // ---------------------------------------------------
    // 2. Test Entire Main Application Run
    // ---------------------------------------------------
    @Test
    @Order(2)
    public void testAppMainRunsWithoutException() {
        assertDoesNotThrow(() -> {
            String[] args = {"localhost:33060", "1000"};
            App.main(args);
        }, "App.main() should run the full workflow without throwing exceptions");
    }

    // ---------------------------------------------------
    // 3. Test that Disconnection Works
    // ---------------------------------------------------
    @Test
    @Order(3)
    public void testDatabaseDisconnection() {
        app.disconnect();
        assertNull(app.con, "Connection should be null after disconnect()");
    }

}
