package com.napier.sem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for App.connect() and App.disconnect().
 */
public class AppTest {

    @Test
    @DisplayName("connect: success on first try")
    public void connectSuccess() throws Exception {
        App app = new App();
        Connection mockCon = mock(Connection.class);

        try (MockedStatic<DriverManager> dm = Mockito.mockStatic(DriverManager.class)) {
            dm.when(() -> DriverManager.getConnection(
                    anyString(), anyString(), anyString()
            )).thenReturn(mockCon);

            app.connect("localhost:33060", 0);

            assertNotNull(app.con);
            dm.verify(() -> DriverManager.getConnection(
                    startsWith("jdbc:mysql://localhost:33060"),
                    eq("root"),
                    eq("example")
            ));
        }
    }

    @Test
    @DisplayName("connect: retries on SQLException (2 fails then success)")
    public void connectRetriesOnSQLException() throws Exception {
        App app = new App();
        Connection mockCon = mock(Connection.class);

        try (MockedStatic<DriverManager> dm = Mockito.mockStatic(DriverManager.class)) {

            // Use Answer instead of chaining thenThrow/thenReturn
            Answer<Connection> answer = new Answer<Connection>() {
                int callCount = 0;

                @Override
                public Connection answer(org.mockito.invocation.InvocationOnMock invocation) throws Throwable {
                    callCount++;
                    if (callCount <= 2) {
                        throw new SQLException("fail " + callCount);
                    }
                    return mockCon;
                }
            };

            dm.when(() -> DriverManager.getConnection(
                    anyString(), anyString(), anyString()
            )).thenAnswer(answer);

            app.connect("localhost:33060", 0);

            assertNotNull(app.con);
            dm.verify(() -> DriverManager.getConnection(
                    anyString(), anyString(), anyString()
            ), times(3));
        }
    }

    @Test
    @DisplayName("disconnect: does nothing when connection null")
    public void disconnectNull() {
        App app = new App();
        app.con = null;

        assertDoesNotThrow(app::disconnect);
    }

    @Test
    @DisplayName("disconnect: closes open connection")
    public void disconnectCloses() throws Exception {
        App app = new App();
        Connection mockCon = mock(Connection.class);
        app.con = mockCon;

        app.disconnect();

        verify(mockCon, times(1)).close();
    }

    @Test
    @DisplayName("disconnect: ignores exception on close")
    public void disconnectCloseThrows() throws Exception {
        App app = new App();
        Connection mockCon = mock(Connection.class);
        doThrow(new RuntimeException("close error")).when(mockCon).close();
        app.con = mockCon;

        assertDoesNotThrow(app::disconnect);
    }
}
