package com.napier.sem;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppIntegrationTest {

    @Test
    @DisplayName("main: full workflow runs without error")
    public void testMainRuns() {
        String[] args = {"localhost:33060", "0"};
        assertDoesNotThrow(() -> App.main(args));
    }
}
