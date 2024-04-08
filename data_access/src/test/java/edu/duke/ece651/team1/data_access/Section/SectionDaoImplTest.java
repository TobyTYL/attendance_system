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
    
   
}
