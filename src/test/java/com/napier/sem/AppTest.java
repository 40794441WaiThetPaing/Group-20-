package com.napier.sem;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {

    @BeforeAll
    static void setLoggerLevel() throws Exception {
        Field loggerField = App.class.getDeclaredField("LOGGER");
        loggerField.setAccessible(true);
        Logger logger = (Logger) loggerField.get(null);
        logger.setLevel(Level.FINE); // ensures branch coverage for FINE logs
    }

    @Test
    @Order(1)
    @DisplayName("connect: immediate success")
    public void testConnectSuccess() throws Exception {
        App app = new App();
        Connection mockCon = mock(Connection.class);

        try (MockedStatic<DriverManager> dm = Mockito.mockStatic(DriverManager.class)) {
            dm.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockCon);

            app.connect("localhost:33060", 0);
            assertNotNull(app.con);
        }
    }

    @Test
    @Order(2)
    @DisplayName("connect: retries twice then succeeds")
    public void testConnectRetryThenSuccess() throws Exception {
        App app = new App();
        Connection mockCon = mock(Connection.class);

        try (MockedStatic<DriverManager> dm = Mockito.mockStatic(DriverManager.class)) {
            Answer<Connection> answer = new Answer<Connection>() {
                int callCount = 0;

                @Override
                public Connection answer(org.mockito.invocation.InvocationOnMock invocation) throws Throwable {
                    callCount++;
                    if (callCount <= 2) throw new SQLException("fail " + callCount);
                    return mockCon;
                }
            };
            dm.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenAnswer(answer);

            app.connect("localhost:33060", 0);
            assertNotNull(app.con);
        }
    }

    @Test
    @Order(3)
    @DisplayName("connect: fails after all retries")
    public void testConnectFailure() throws Exception {
        App app = new App();

        try (MockedStatic<DriverManager> dm = Mockito.mockStatic(DriverManager.class)) {
            dm.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenAnswer(invocation -> { throw new SQLException("fail"); });

            SQLException thrown = assertThrows(SQLException.class, () ->
                    app.connect("localhost:33060", 0)
            );
            assertTrue(thrown.getMessage().contains("Could not connect"));
        }
    }


    @Test
    @Order(4)
    @DisplayName("disconnect: null connection does nothing")
    public void testDisconnectNull() throws Exception {
        App app = new App();
        app.con = null;
        app.disconnect(); // Should not throw
    }

    @Test
    @Order(5)
    @DisplayName("disconnect: closes valid connection")
    public void testDisconnectValid() throws Exception {
        App app = new App();
        Connection mockCon = mock(Connection.class);
        app.con = mockCon;
        app.disconnect();
        verify(mockCon, times(1)).close();
    }

    @Test
    @Order(6)
    @DisplayName("disconnect: close throws exception")
    public void testDisconnectThrows() throws Exception {
        App app = new App();
        Connection mockCon = mock(Connection.class);
        doThrow(new SQLException("close error")).when(mockCon).close();
        app.con = mockCon;
        assertThrows(SQLException.class, app::disconnect);
    }
}
