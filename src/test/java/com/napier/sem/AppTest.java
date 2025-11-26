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
        logger.setLevel(Level.FINE);
    }

    @Test
    @Order(1)
    @DisplayName("connect: normal connection triggers success log")
    public void connectSuccess() throws Exception {
        App app = new App();
        Connection mockCon = mock(Connection.class);

        try (MockedStatic<DriverManager> dm = Mockito.mockStatic(DriverManager.class)) {
            dm.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockCon);

            app.connect("localhost:33060", 0);
            assertNotNull(app.con);
            dm.verify(() -> DriverManager.getConnection(startsWith("jdbc:mysql://localhost:33060"), eq("root"), eq("example")));
        }
    }

    @Test
    @Order(2)
    @DisplayName("connect: retry logic, throws twice then succeeds")
    public void connectRetriesOnSQLException() throws Exception {
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
            dm.verify(() -> DriverManager.getConnection(anyString(), anyString(), anyString()), times(3));
        }
    }

    @Test
    @Order(3)
    @DisplayName("disconnect: null connection does nothing")
    public void disconnectNull() {
        App app = new App();
        app.con = null;
        assertDoesNotThrow(app::disconnect);
    }

    @Test
    @Order(4)
    @DisplayName("disconnect: successful close triggers close")
    public void disconnectCloses() throws Exception {
        App app = new App();
        Connection mockCon = mock(Connection.class);
        app.con = mockCon;
        app.disconnect();
        verify(mockCon, times(1)).close();
    }

    @Test
    @Order(5)
    @DisplayName("disconnect: error in close triggers error log")
    public void disconnectCloseThrows() throws Exception {
        App app = new App();
        Connection mockCon = mock(Connection.class);
        doThrow(new RuntimeException("close error")).when(mockCon).close();
        app.con = mockCon;
        assertDoesNotThrow(app::disconnect);
    }
}
