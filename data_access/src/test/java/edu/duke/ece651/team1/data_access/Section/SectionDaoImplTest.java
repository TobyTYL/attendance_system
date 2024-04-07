package edu.duke.ece651.team1.data_access.Section;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.duke.ece651.team1.shared.Section;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SectionDaoImplTest {
  private Connection conn;
    private SectionDaoImpl sectionDao;

    @BeforeEach
    void setUp() throws Exception {
        // Connect to H2 in-memory database
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        sectionDao = new SectionDaoImpl(conn);

        // Create a sections table
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS sections;");
            stmt.execute("CREATE TABLE sections (sectionid INT PRIMARY KEY, classid INT, professorid INT)");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        conn.close();
    }

    @Test
    void testGetAllSections() throws Exception {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO sections (sectionid, classid, professorid) VALUES (1, 101, 1001)");
        }
        List<Section> sections = sectionDao.getAllSections();
        assertEquals(1, sections.size());
        Section section = sections.get(0);
        assertEquals(1, section.getSectionId());
    }

    @Test
    void testGetSectionById() throws Exception {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO sections (sectionid, classid, professorid) VALUES (2, 102, 1002)");
        }
        Section section = sectionDao.getSectionById(2);
        assertNotNull(section);
        assertEquals(102, section.getClassId());
    }

    @Test
    void testAddAndDeleteSection() throws Exception {
        Section newSection = new Section(3, 103, 1003);
        sectionDao.addSection(newSection);

        Section fetchedSection = sectionDao.getSectionById(3);
        assertNotNull(fetchedSection);

        sectionDao.deleteSection(3);
        assertNull(sectionDao.getSectionById(3));
    }

    @Test
    void testUpdateSection() throws Exception {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO sections (sectionid, classid, professorid) VALUES (4, 104, 1004)");
        }
        Section updatedSection = new Section(4, 105, 1005);
        sectionDao.updateSection(updatedSection);

        Section fetchedSection = sectionDao.getSectionById(4);
        assertEquals(105, fetchedSection.getClassId());
    }

    @Test
    void testGetSectionsByProfessorId() throws Exception {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO sections (sectionid, classid, professorid) VALUES (5, 105, 1005), (6, 106, 1005)");
        }
        List<Section> sections = sectionDao.getSectionsByProfessorId(1005);
        assertEquals(2, sections.size());
    }
    @Test
    void testGetSectionByIdSQLException() {
        // Mock the Connection and PreparedStatement to throw an SQLException
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);
        SectionDaoImpl daoWithMock = new SectionDaoImpl(mockConn);
        
        try {
            when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
            when(mockPs.executeQuery()).thenThrow(SQLException.class);
        } catch (SQLException e) {
          //e.printStackTrace();
        }

        assertNull(daoWithMock.getSectionById(1), "Expected getSectionById to return null on SQLException");

        // Verify the interaction
        try {
            verify(mockPs).executeQuery();
            verify(mockConn).prepareStatement(anyString());
        } catch (SQLException e) {
          //e.printStackTrace();
        }
    }
    @Test
    void testGetAllSectionsWithSQLException() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
        when(mockPs.executeQuery()).thenThrow(SQLException.class);

        SectionDaoImpl daoWithMock = new SectionDaoImpl(mockConn);
        List<Section> sections = daoWithMock.getAllSections();
        
        assertTrue(sections.isEmpty(), "Expected no sections due to SQLException");
        
        verify(mockPs, times(1)).executeQuery();
        verify(mockConn, times(1)).prepareStatement(anyString());
    }
    @Test
    void testUpdateSectionWithSQLException() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
        doThrow(SQLException.class).when(mockPs).executeUpdate();

        SectionDaoImpl daoWithMock = new SectionDaoImpl(mockConn);
        daoWithMock.updateSection(new Section(1, 2, 3)); // Use dummy values
        
        verify(mockPs, times(1)).executeUpdate();
        verify(mockConn, times(1)).prepareStatement(anyString());
    } 
    @Test
    void testDeleteSectionWithSQLException() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
        doThrow(SQLException.class).when(mockPs).executeUpdate();

        SectionDaoImpl daoWithMock = new SectionDaoImpl(mockConn);
        daoWithMock.deleteSection(1); // Use a dummy sectionId
        
        verify(mockPs, times(1)).executeUpdate();
        verify(mockConn, times(1)).prepareStatement(anyString());
    }
    @Test
    void testGetSectionsByProfessorIdWithSQLException() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
        when(mockPs.executeQuery()).thenThrow(SQLException.class);

        SectionDaoImpl daoWithMock = new SectionDaoImpl(mockConn);
        List<Section> sections = daoWithMock.getSectionsByProfessorId(1); // Use dummy professorId
        
        assertTrue(sections.isEmpty(), "Expected no sections due to SQLException");
        
        verify(mockPs, times(1)).executeQuery();
        verify(mockPs, times(1)).setInt(1, 1); // Verify that the professorId is set
        verify(mockConn, times(1)).prepareStatement(anyString());
    }
    @Test
    void testAddSectionWithSQLException() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockPs = mock(PreparedStatement.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
        doThrow(SQLException.class).when(mockPs).executeUpdate();

        SectionDaoImpl daoWithMock = new SectionDaoImpl(mockConn);
        
        // Attempt to add a section, expecting the SQLException to be caught and handled inside the method
        daoWithMock.addSection(new Section(1, 1, 1)); // Use dummy values for sectionId, classId, and professorId
        
        // Verify that executeUpdate was called, which means we reached the line that throws SQLException
        verify(mockPs, times(1)).executeUpdate();
        verify(mockConn, times(1)).prepareStatement(anyString());
        
    }

}
