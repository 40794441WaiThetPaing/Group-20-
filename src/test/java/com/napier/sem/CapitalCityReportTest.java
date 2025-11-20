package com.napier.sem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;

import static org.mockito.Mockito.*;

class CapitalCityReportTest {

    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rset;
    private CapitalCityReport report;

    @BeforeEach
    void setUp() {
        con = mock(Connection.class);
        stmt = mock(Statement.class);
        pstmt = mock(PreparedStatement.class);
        rset = mock(ResultSet.class);
        report = new CapitalCityReport(con);
    }

    // -------------------------------------------------------
    // 1. Test: printAllCapitalCitiesWorld()
    // -------------------------------------------------------
    @Test
    void testPrintAllCapitalCitiesWorld() throws Exception {
        when(con.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rset);

        when(rset.next()).thenReturn(true, false);
        when(rset.getString("Capital")).thenReturn("Tokyo");
        when(rset.getString("Country")).thenReturn("Japan");
        when(rset.getInt("Population")).thenReturn(37843000);

        report.printAllCapitalCitiesWorld();

        verify(con).createStatement();
        verify(stmt).executeQuery(contains("SELECT city.Name AS Capital"));
    }

    // -------------------------------------------------------
    // 2. Test: printCapitalCitiesByContinent()
    // -------------------------------------------------------
    @Test
    void testPrintCapitalCitiesByContinent() throws Exception {
        when(con.prepareStatement(anyString())).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rset);

        when(rset.next()).thenReturn(true, false);
        when(rset.getString("Capital")).thenReturn("Berlin");
        when(rset.getString("Country")).thenReturn("Germany");
        when(rset.getInt("Population")).thenReturn(3645000);

        report.printCapitalCitiesByContinent("Europe");

        verify(pstmt).setString(1, "Europe");
        verify(pstmt).executeQuery();
    }

    // -------------------------------------------------------
    // 3. Test: printCapitalCitiesByRegion()
    // -------------------------------------------------------
    @Test
    void testPrintCapitalCitiesByRegion() throws Exception {
        when(con.prepareStatement(anyString())).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rset);

        when(rset.next()).thenReturn(true, false);
        when(rset.getString("Capital")).thenReturn("Madrid");
        when(rset.getString("Country")).thenReturn("Spain");
        when(rset.getInt("Population")).thenReturn(3223000);

        report.printCapitalCitiesByRegion("Southern Europe");

        verify(pstmt).setString(1, "Southern Europe");
    }

    // -------------------------------------------------------
    // 4. Test: printTopNCapitalCitiesWorld()
    // -------------------------------------------------------
    @Test
    void testPrintTopNCapitalCitiesWorld() throws Exception {
        when(con.prepareStatement(anyString())).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rset);

        when(rset.next()).thenReturn(true, false);
        when(rset.getString("Capital")).thenReturn("Beijing");
        when(rset.getString("Country")).thenReturn("China");
        when(rset.getInt("Population")).thenReturn(21540000);

        report.printTopNCapitalCitiesWorld(5);

        verify(pstmt).setInt(1, 5);
    }

    // -------------------------------------------------------
    // 5. Test: printTopNCapitalCitiesByContinent()
    // -------------------------------------------------------
    @Test
    void testPrintTopNCapitalCitiesByContinent() throws Exception {
        when(con.prepareStatement(anyString())).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rset);

        when(rset.next()).thenReturn(true, false);

        report.printTopNCapitalCitiesByContinent("Asia", 3);

        verify(pstmt).setString(1, "Asia");
        verify(pstmt).setInt(2, 3);
    }

    // -------------------------------------------------------
    // 6. Test: printTopNCapitalCitiesByRegion()
    // -------------------------------------------------------
    @Test
    void testPrintTopNCapitalCitiesByRegion() throws Exception {
        when(con.prepareStatement(anyString())).thenReturn(pstmt);
        when(pstmt.executeQuery()).thenReturn(rset);

        when(rset.next()).thenReturn(true, false);

        report.printTopNCapitalCitiesByRegion("Western Europe", 2);

        verify(pstmt).setString(1, "Western Europe");
        verify(pstmt).setInt(2, 2);
    }
}
