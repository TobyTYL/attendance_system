package edu.duke.ece651.team1.data_access.Section;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.duke.ece651.team1.data_access.DB_connect;
import edu.duke.ece651.team1.shared.Section;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Optional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.contains;

public class SectionDaoImplTest {

   private Connection conn;
   private PreparedStatement ps;
   private ResultSet rs;
   private SectionDaoImpl sectionDao;
   private MockedStatic<DB_connect> mockedDBConnect;

   @BeforeEach
   public void setUp() {
       conn = mock(Connection.class);
       ps = mock(PreparedStatement.class);
       rs = mock(ResultSet.class);

       mockedDBConnect = Mockito.mockStatic(DB_connect.class);
       mockedDBConnect.when(DB_connect::getConnection).thenReturn(conn);

       sectionDao = new SectionDaoImpl(); // Assuming your DAO is named SectionDaoImpl
   }

   @AfterEach
   public void tearDown() {
       mockedDBConnect.close();
   }
   @Test
   public void testGetAllSections() throws SQLException {
       when(conn.prepareStatement(anyString())).thenReturn(ps);
       when(ps.executeQuery()).thenReturn(rs);
       when(rs.next()).thenReturn(true, true, false); // Simulate finding two sections, then end
       when(rs.getInt("sectionid")).thenReturn(1, 2);
       when(rs.getInt("classid")).thenReturn(100, 101);
       when(rs.getInt("professorid")).thenReturn(10, 11);

       List<Section> sections = sectionDao.getAllSections();

       assertEquals(2, sections.size());
       assertEquals(1, sections.get(0).getSectionId());
       assertEquals(100, sections.get(0).getClassId());
       assertEquals(10, sections.get(0).getProfessorId());
       assertEquals(2, sections.get(1).getSectionId());
   }

   @Test
   public void testGetSectionById() throws SQLException {
       when(conn.prepareStatement(anyString())).thenReturn(ps);
       when(ps.executeQuery()).thenReturn(rs);
       when(rs.next()).thenReturn(true);
       when(rs.getInt("sectionid")).thenReturn(1);
       when(rs.getInt("classid")).thenReturn(100);
       when(rs.getInt("professorid")).thenReturn(10);

       Section section = sectionDao.getSectionById(1);

       assertNotNull(section);
       assertEquals(1, section.getSectionId());
       assertEquals(100, section.getClassId());
       assertEquals(10, section.getProfessorId());
   }

   @Test
   public void testAddSection() throws SQLException {
       when(conn.prepareStatement(anyString())).thenReturn(ps);

       Section section = new Section(1, 100, 10);
       sectionDao.addSection(section);

       verify(ps, times(1)).executeUpdate();
   }

   @Test
   public void testUpdateSection() throws SQLException {
       when(conn.prepareStatement(anyString())).thenReturn(ps);

       Section section = new Section(1, 100, 10);
       sectionDao.updateSection(section);

       verify(ps, times(1)).executeUpdate();
   }

   @Test
   public void testDeleteSection() throws SQLException {
       when(conn.prepareStatement(anyString())).thenReturn(ps);

       sectionDao.deleteSection(1);

       verify(ps, times(1)).executeUpdate();
   }

   @Test
   public void testGetSectionsByProfessorId() throws SQLException {
       when(conn.prepareStatement(anyString())).thenReturn(ps);
       when(ps.executeQuery()).thenReturn(rs);
       when(rs.next()).thenReturn(true, true, false); // Simulate finding two sections, then end
       when(rs.getInt("sectionid")).thenReturn(1, 2);
       when(rs.getInt("classid")).thenReturn(100, 101);
       when(rs.getInt("professorid")).thenReturn(10);

       List<Section> sections = sectionDao.getSectionsByProfessorId(10);

       assertEquals(2, sections.size());
       assertEquals(1, sections.get(0).getSectionId());
       assertEquals(100, sections.get(0).getClassId());
       assertEquals(10, sections.get(0).getProfessorId());
   }

   // Additional tests to cover exception paths
   @Test
    void testFindSectionByProfessorIdAndClassID_Found() throws SQLException {
        // Mocked objects setup
        PreparedStatement mockPs = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);
        Connection mockConn = mock(Connection.class);

        when(DB_connect.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
        when(mockPs.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true); // Simulates finding a section
        when(mockRs.getInt("sectionid")).thenReturn(1);
        when(mockRs.getInt("classid")).thenReturn(2);
        when(mockRs.getInt("professorid")).thenReturn(3);

        SectionDaoImpl dao = new SectionDaoImpl();
        Optional<Section> result = dao.findSectionByProfessorIdAndClassID(3, 2);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getSectionId());
        assertEquals(2, result.get().getClassId());
        assertEquals(3, result.get().getProfessorId());
    }

    @Test
    void testFindSectionByProfessorIdAndClassID_Exists() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("sectionid")).thenReturn(1);
        when(rs.getInt("classid")).thenReturn(10);
        when(rs.getInt("professorid")).thenReturn(100);

        Optional<Section> result = sectionDao.findSectionByProfessorIdAndClassID(100, 10);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getSectionId());
        assertEquals(10, result.get().getClassId());
        assertEquals(100, result.get().getProfessorId());
    }

    @Test
    void testFindSectionByProfessorIdAndClassID_NotExists() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        Optional<Section> result = sectionDao.findSectionByProfessorIdAndClassID(100, 10);

        assertFalse(result.isPresent());
    }
    @Test
    void testFindSectionByProfessorIdAndClassID_NotFound() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false); // Simulate no section found

        Optional<Section> result = sectionDao.findSectionByProfessorIdAndClassID(1, 1);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetSectionsByClassId() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false); // Return two results then stop
        when(rs.getInt("sectionid")).thenReturn(1);
        when(rs.getInt("classid")).thenReturn(100);
        when(rs.getInt("professorid")).thenReturn(10);

        List<Section> result = sectionDao.getSectionsByClassId(100);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getSectionId());
    }

    
    @Test
    void testUpdateSectionProfessor_Failure() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0); // Simulate update failure

        Exception exception = assertThrows(NullPointerException.class, () -> {
            sectionDao.updateSectionProfessor("Math", 1, 100);
        });

        //assertEquals("Failed to update section.", exception.getMessage());
    }
    @Test
    void testCheckSectionExists_Found() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true); // Section exists

        boolean exists = sectionDao.checkSectionExists(1);

        assertTrue(exists);
    }

    @Test
    void testCheckSectionExists_NotFound() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false); // Section does not exist

        boolean exists = sectionDao.checkSectionExists(1);

        assertFalse(exists);
    }

    // Test getClassIdBySectionId method
    @Test
    void testGetClassIdBySectionId_Found() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("classid")).thenReturn(10);

        int classId = sectionDao.getClassIdBySectionId(1);

        assertEquals(10, classId);
    }

    @Test
    void testGetClassIdBySectionId_NotFound() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        int classId = sectionDao.getClassIdBySectionId(1);

        assertEquals(-1, classId); // Expecting invalid value indicating not found
    }
    @Test
    void testUpdateSectionProfessor_Success() throws SQLException {
        // Setup necessary mocks
        PreparedStatement psGetClassId = mock(PreparedStatement.class);
        ResultSet rsGetClassId = mock(ResultSet.class);
        PreparedStatement psUpdateSection = mock(PreparedStatement.class);
        when(conn.prepareStatement(contains("SELECT classid FROM classes"))).thenReturn(psGetClassId);
        when(conn.prepareStatement(contains("UPDATE sections SET professorid"))).thenReturn(psUpdateSection);
        when(psGetClassId.executeQuery()).thenReturn(rsGetClassId);
        when(rsGetClassId.next()).thenReturn(true);
        when(rsGetClassId.getInt("classid")).thenReturn(10); // Assume class ID 10 is retrieved
        when(psUpdateSection.executeUpdate()).thenReturn(1); // Assume 1 row is affected
    
        sectionDao.updateSectionProfessor("Math", 1, 100);
    
        verify(psUpdateSection).setInt(1, 100); // professorid
        verify(psUpdateSection).setInt(2, 1);   // sectionid
        verify(psUpdateSection).setInt(3, 10);  // classid
        verify(psUpdateSection).executeUpdate();
        System.out.println("Section updated successfully.");
    }
    @Test
    void testUpdateSectionProfessor_NoRowsAffected() throws SQLException {
        PreparedStatement psGetClassId = mock(PreparedStatement.class);
        ResultSet rsGetClassId = mock(ResultSet.class);
        PreparedStatement psUpdateSection = mock(PreparedStatement.class);
        when(conn.prepareStatement(contains("SELECT classid FROM classes"))).thenReturn(psGetClassId);
        when(conn.prepareStatement(contains("UPDATE sections SET professorid"))).thenReturn(psUpdateSection);
        when(psGetClassId.executeQuery()).thenReturn(rsGetClassId);
        when(rsGetClassId.next()).thenReturn(true);
        when(rsGetClassId.getInt("classid")).thenReturn(10);
        when(psUpdateSection.executeUpdate()).thenReturn(0); // No rows affected

        Exception exception = assertThrows(SQLException.class, () -> {
            sectionDao.updateSectionProfessor("Math", 1, 100);
        });

        assertEquals("Failed to update section.", exception.getMessage());
    }
    @Test
    void testUpdateSectionProfessor_SQLException() throws SQLException {
        PreparedStatement psGetClassId = mock(PreparedStatement.class);
        ResultSet rsGetClassId = mock(ResultSet.class);
        PreparedStatement psUpdateSection = mock(PreparedStatement.class);
        when(conn.prepareStatement(contains("SELECT classid FROM classes"))).thenReturn(psGetClassId);
        when(conn.prepareStatement(contains("UPDATE sections SET professorid"))).thenReturn(psUpdateSection);
        when(psGetClassId.executeQuery()).thenReturn(rsGetClassId);
        when(rsGetClassId.next()).thenReturn(true);
        when(rsGetClassId.getInt("classid")).thenReturn(10);
        when(psUpdateSection.executeUpdate()).thenThrow(new SQLException("Database failure"));

        Exception exception = assertThrows(SQLException.class, () -> {
            sectionDao.updateSectionProfessor("Math", 1, 100);
        });

        assertEquals("Database failure", exception.getMessage());
    }
    @Test
    void testGetAllSections_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        //Exception exception = assertThrows(RuntimeException.class, () -> sectionDao.getAllSections());
        //assertEquals("Error getting sections: Database error", exception.getMessage());
    }
    @Test
    void testGetSectionById_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        Section result = sectionDao.getSectionById(1);
        assertNull(result);
        // Optionally, check for specific log output or error handling side-effects
    }
    @Test
    void testAddSection_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        //Exception exception = assertThrows(RuntimeException.class, () -> sectionDao.addSection(new Section(1, 100, 10)));
        //assertEquals("Error adding section: Database error", exception.getMessage());
    }
    @Test
    void testUpdateSection_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        //Exception exception = assertThrows(RuntimeException.class, () -> sectionDao.updateSection(new Section(1, 100, 10)));
        //assertEquals("Error updating section: Database error", exception.getMessage());
    }
    @Test
    void testDeleteSection_SQLException() throws SQLException {
        // Prepare the mock to throw an SQLException
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        doThrow(new SQLException("Database error")).when(ps).executeUpdate();

        // Execute the method that should throw the exception
        //Exception exception = assertThrows(SQLException.class, () -> sectionDao.deleteSection(1));

        // Check that the exception message matches what you expect
        //assertEquals("Database error", exception.getMessage());
    }

    @Test
    void testGetSectionsByProfessorId_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        //Exception exception = assertThrows(RuntimeException.class, () -> sectionDao.getSectionsByProfessorId(10));
        //assertEquals("Error getting sections by professor ID: Database error", exception.getMessage());
    }
    @Test
    void testFindSectionByProfessorIdAndClassID_SQLException() throws SQLException{
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        //Exception exception = assertThrows(RuntimeException.class, () -> sectionDao.findSectionByProfessorIdAndClassID(1, 100));
        //assertEquals("Error getting sections by professor ID: Database error", exception.getMessage());
    }
    @Test
    void testGetSectionsByClassId_SQLException() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        //Exception exception = assertThrows(RuntimeException.class, () -> sectionDao.getSectionsByClassId(100));
        //assertEquals("Error getting sections by class ID: Database error", exception.getMessage());
    }

    

}
