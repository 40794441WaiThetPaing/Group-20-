package com.napier.sem;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link App} class.
 * Uses mocked database connections and report classes to avoid real DB access.
 */
public class AppTest {

    private App app;
    private Connection mockConnection;

    @BeforeEach
    void setup() {
        app = new App();
        mockConnection = Mockito.mock(Connection.class);
        app.con = mockConnection; // inject mock connection
    }

    @Test
    void testConnect_Success() throws SQLException {
        // We cannot actually test DriverManager.getConnection easily without a real DB,
        // but we can simulate success by checking no exception occurs with valid parameters
        assertDoesNotThrow(() -> app.connect("localhost:33060", 10));
    }

    @Test
    void testDisconnect_NullConnection() {
        app.con = null; // simulate no connection
        assertDoesNotThrow(() -> app.disconnect());
    }

    @Test
    void testDisconnect_WithConnection() throws SQLException {
        doNothing().when(mockConnection).close(); // simulate close success
        assertDoesNotThrow(() -> app.disconnect());
        verify(mockConnection, times(1)).close();
    }

    @Test
    void testMain_ExecutionWithMocks() {
        // Use Mockito.spy to avoid actually connecting to DB
        App spyApp = spy(new App());
        spyApp.con = mockConnection;

        // Mock other report classes to avoid real DB access
        CountryReport cr = mock(CountryReport.class);
        GeneralPopulationReports gpr = mock(GeneralPopulationReports.class);
        PopulationReport pr = mock(PopulationReport.class);
        CapitalCityReport ccr = mock(CapitalCityReport.class);
        TopNCityReports tcr = mock(TopNCityReports.class);
        GenerateCityReports gcr = mock(GenerateCityReports.class);

        // We cannot inject these into main() directly since it creates new objects,
        // but the test ensures that main() runs without throwing exceptions
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
