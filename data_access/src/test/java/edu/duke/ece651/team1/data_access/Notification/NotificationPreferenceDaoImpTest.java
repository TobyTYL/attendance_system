package edu.duke.ece651.team1.data_access.Notification;

import edu.duke.ece651.team1.data_access.DB_connect;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class NotificationPreferenceDaoImpTest {
    @Mock
    private Connection conn;
    @Mock
    private PreparedStatement ps;
    private NotificationPreferenceDaoImp preferenceDao;
    @Mock
    private ResultSet rs;
    private MockedStatic<DB_connect> mockedDBConnect;

    @BeforeEach
    void setUp() {
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);
        mockedDBConnect = Mockito.mockStatic(DB_connect.class);
        mockedDBConnect.when(DB_connect::getConnection).thenReturn(conn);
        preferenceDao = new NotificationPreferenceDaoImp();
    }
    @AfterEach
    void tearDown() {
        mockedDBConnect.close();
    }

    @Test
    public void testUpdateNotificationPreference() throws SQLException {
        int studentId = 123;
        int classId = 456;
        boolean receiveNotifications = true;
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        preferenceDao.updateNotificationPreference(studentId, classId, receiveNotifications);
    }

    @Test
    public void testFindNotificationPreferenceByStudentIdAndClassId() throws SQLException {
        int studentId = 123;
        int classId = 456;
        boolean receiveNotifications = true;

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getBoolean("ReceiveNotifications")).thenReturn(receiveNotifications);
        when(rs.getInt("PreferenceID")).thenReturn(1);

        NotificationPreference preference = preferenceDao.findNotificationPreferenceByStudentIdAndClassId(studentId, classId);

        assertEquals(studentId, preference.getStudentId());
        assertEquals(classId, preference.getClassId());
        assertEquals(receiveNotifications, preference.isReceiveNotifications());
    }
    @Test
    public void testAddNotificationPreference() throws SQLException {
        int studentId = 123;
        int classId = 456;
        boolean receiveNotifications = true;

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        preferenceDao.addNotificationPreference(studentId, classId, receiveNotifications);

    }

}
