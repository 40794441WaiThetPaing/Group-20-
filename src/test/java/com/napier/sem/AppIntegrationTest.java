package com.napier.sem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Integration test for App.main().
 * Requires a running MySQL 'world' database on localhost:33060.
 */
public class AppIntegrationTest {

    @Test
    @DisplayName("main: runs full report flow without error")
    public void mainRunsWithoutError() {
        String[] args = {"localhost:33060", "0"};

        assertDoesNotThrow(() -> App.main(args));
    }
}
