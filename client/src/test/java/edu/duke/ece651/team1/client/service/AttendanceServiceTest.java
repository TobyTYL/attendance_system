package edu.duke.ece651.team1.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.AttendanceRecordExporter;
import edu.duke.ece651.team1.shared.AttendanceRecordExporterFactory;
import edu.duke.ece651.team1.shared.JsonAttendanceSerializer;
import edu.duke.ece651.team1.shared.Student;
import edu.duke.ece651.team1.client.controller.AttendanceController;
import edu.duke.ece651.team1.client.model.AttendanceSummary;
import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.model.*;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTest {

    @InjectMocks
    private AttendanceService attendanceService;
    @Mock
    private UserSession userSession;
    private MockedStatic<ApiService> mockedApiService;
    private int sectionId = 1;
    List<Student> mockedStudents = List.of(new Student(1, "huidan", "huidan", "email", 2),
            new Student(2, "zhecheng", "zhecngen", "email", 3));
    List<String> dates = List.of("2024-04-10");
    AttendanceRecord record = new AttendanceRecord(LocalDate.parse("2024-04-10"));

    @BeforeEach
    void setUp() {
        userSession.setHost("localhost");
        userSession.setPort("8080");
        mockedApiService = Mockito.mockStatic(ApiService.class);
        String url_report = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/report/class/" + sectionId;
        String url_roster = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/allStudents/" + sectionId;
        String url_dates = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/record-dates/" + sectionId;
        String url_record = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/record/" + sectionId + "/";

        record.initializeFromRoaster(mockedStudents);
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String recordStr = serializer.serialize(record);
        when(ApiService.executeGetRequest(eq(url_report), any(ParameterizedTypeReference.class)))
                .thenReturn("Attendance Summary: StudentName: John Doe, Total Sessions: 10, Attended: 8");
        when(ApiService.executeGetRequest(eq(url_roster), any(ParameterizedTypeReference.class)))
                .thenReturn(mockedStudents);
        when(ApiService.executeGetRequest(eq(url_dates), any(ParameterizedTypeReference.class)))
                .thenReturn(dates);
        when(ApiService.executeGetRequest(contains("/api/attendance/record/"),
                any(ParameterizedTypeReference.class)))
                .thenReturn(recordStr);
        mockedApiService.when(() -> ApiService.executePostPutRequest(
                contains("/api/attendance/record/"), any(String.class), any(ParameterizedTypeReference.class),
                eq(true))).thenAnswer(invocation -> null);
        mockedApiService.when(() -> ApiService.executePostPutRequest(
                contains("/api/attendance/record/"), any(String.class), any(ParameterizedTypeReference.class),
                eq(false))).thenAnswer(invocation -> null);
        mockedApiService.when(() -> ApiService.executePostPutRequest(
                contains("/api/attendance/mark/"), any(Object.class), any(ParameterizedTypeReference.class),
                eq(true))).thenAnswer(invocation -> null);
    }

    @AfterEach
    void tearDown() {
        mockedApiService.close();
    }

    @Test
    void testGetRoaster() {
        List<Student> result = attendanceService.getRoaster(sectionId);
        assertEquals(mockedStudents, result);
    }

    @Test
    void testGetClassReport() {
        String result = attendanceService.getClassReport(sectionId);
        assertEquals("Attendance Summary: StudentName: John Doe, Total Sessions: 10, Attended: 8", result);
    }

    @Test
    void testGetRecordDates() {
        List<String> result = attendanceService.getRecordDates(sectionId);
        assertEquals(dates, result);
    }

    @Test
    void testGetAttendanceRecord() {
        AttendanceRecord result = attendanceService.getAttendanceRecord("2024-04-10", sectionId);
        assertNotNull(result);
    }

    @Test
    void testSendAttendanceRecord() {
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        attendanceService.sendAttendanceRecord(record, sectionId);
        mockedApiService.verify(() -> ApiService.executePostPutRequest(
                contains("/api/attendance/record/"), any(String.class), any(ParameterizedTypeReference.class),
                eq(true)));
    }

    @Test
    void testUpdateAttendanceRecord() {
        AttendanceRecord record = new AttendanceRecord(LocalDate.now());
        attendanceService.updateAttendanceRecord(record, sectionId);
        mockedApiService.verify(() -> ApiService.executePostPutRequest(
                contains("/api/attendance/record/"), any(String.class), any(ParameterizedTypeReference.class),
                eq(false)));
    }

    @Test
    void testExportRecord() throws IOException {
        String sessionDate = "2024-04-10";
        String format = "json";
        AttendanceRecordExporter exporter = mock(AttendanceRecordExporter.class);
        AttendanceRecordExporterFactory factory = mock(AttendanceRecordExporterFactory.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (MockedStatic<AttendanceRecordExporterFactory> mockedFactory = Mockito
                .mockStatic(AttendanceRecordExporterFactory.class)) {
            mockedFactory.when(() -> AttendanceRecordExporterFactory.createExporter(eq(format))).thenReturn(exporter);
            attendanceService.exportRecord(sessionDate, sectionId, format, response);
            verify(exporter).exportToFile(any(AttendanceRecord.class), anyString(), anyString());

        }
    }

    @Test
    void testGetAttendancestatistic() {
        String report = "Class Attendance Report:\n" +
                "StudentName1: Attended 5/10 sessions (50.00% attendance rate), Tardy Count: 2.";
        when(attendanceService.getClassReport(sectionId)).thenReturn(report);
        List<AttendanceSummary> summaries = attendanceService.getAttendancestatistic(sectionId);
        assertEquals(1, summaries.size());
        assertEquals(5, summaries.get(0).getAttendedCount());
    }

    @Test
    void testUpdateStudentAttendance() {
        int studentId = 1;
        String result = attendanceService.updateStudentAttendance(sectionId, studentId);
        assertEquals("success", result);
    }


}
