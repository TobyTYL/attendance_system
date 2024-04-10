package edu.duke.ece651.team1.client.controller;

import edu.duke.ece651.team1.shared.*;
import edu.duke.ece651.team1.client.*;
import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.view.AttendanceView;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AttendanceControllerTest {
    @Mock
    private BufferedReader inputReader;

    @Mock
    private PrintStream out;

    @Mock
    private AttendanceView attendanceView;

    private AttendanceController attendanceController;

    private MockedStatic<ControllerUtils> utils;
    @Mock
    private UserSession userSession;
    private int sectionId = 1;

    @BeforeEach
    void setUp() {
        // Configure default behaviors for the mocked mainMenuView
        userSession.setHost("localhost");
        userSession.setPort("8080");
        attendanceController = new AttendanceController(inputReader, out, sectionId);
        attendanceController.attendanceView = attendanceView;
        utils = Mockito.mockStatic(ControllerUtils.class);
        String url_report = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/report/class/" + sectionId;
        String url_roster = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/allStudents/" + sectionId;
        String url_dates = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/record-dates/" + sectionId;
        String url_record = "http://" + UserSession.getInstance().getHost() + ":" + UserSession.getInstance().getPort()
                + "/api/attendance/record/" + sectionId + "/";
        List<Student> mockedStudents = List.of(new Student(1, "huidan", "huidan", "email", 2),
                new Student(2, "zhecheng", "zhecngen", "email", 3));
        List<String> dates = List.of("2024-04-10");
        AttendanceRecord record = new AttendanceRecord(LocalDate.parse("2024-04-10"));
        record.initializeFromRoaster(mockedStudents);
        JsonAttendanceSerializer serializer = new JsonAttendanceSerializer();
        String recordStr = serializer.serialize(record);
        System.out.println(recordStr);
        when(ControllerUtils.executeGetRequest(eq(url_report), any(ParameterizedTypeReference.class)))
                .thenReturn("class report");
        when(ControllerUtils.executeGetRequest(eq(url_roster), any(ParameterizedTypeReference.class)))
                .thenReturn(mockedStudents);
        when(ControllerUtils.executeGetRequest(eq(url_dates), any(ParameterizedTypeReference.class)))
                .thenReturn(dates);
        when(ControllerUtils.executeGetRequest(contains("/api/attendance/record/"),
                any(ParameterizedTypeReference.class)))
                .thenReturn(recordStr);

    }

    @AfterEach
    void tearDown() {
        utils.close();
    }

    @Test
    void testStartAttendanceMenu_ReportOption() throws IOException {
        when(attendanceView.readAttendanceOption()).thenReturn("report", "back");
        attendanceController.startAttendanceMenue();
        verify(attendanceView).showClassReport(anyString());
    }

    @Test
    void testStartAttendanceMenu_Invalid() throws IOException {
        when(attendanceView.readAttendanceOption()).thenThrow(new IllegalArgumentException("Invalid option"))
                .thenReturn("back");
        attendanceController.startAttendanceMenue();
        verify(out).println("Invalid option for Attendance Management Menue");
    }

    @Test
    void testStartAttendanceMenu_TakeOption() throws IOException {
        when(attendanceView.readAttendanceOption()).thenReturn("take", "back");
        when(attendanceView.promptForStudentAttendance(eq("huidan"), anyBoolean())).thenReturn("P");
        when(attendanceView.promptForStudentAttendance(eq("zhecngen"), anyBoolean())).thenReturn("A");
        attendanceController.startAttendanceMenue();
        verify(attendanceView, times(2)).promptForStudentAttendance(anyString(), eq(true));
    }

    @Test
    void testStartAttendanceMenue_ExportOption() throws IOException {
        when(attendanceView.readAttendanceOption()).thenReturn("export", "export", "export", "back");
        when(attendanceView.readExportDateFromPrompt(anyList())).thenReturn("2024-04-10");
        when(attendanceView.readFormtFromPrompt()).thenReturn("csv", "back", "json", "back", "xml", "back");
        attendanceController.startAttendanceMenue();
        verify(attendanceView).showExportSuccessMessage(contains(".csv"));
        verify(attendanceView).showExportSuccessMessage(contains(".json"));
        verify(attendanceView).showExportSuccessMessage(contains(".xml"));
    }
    @Test
    void testStartModify_AllStudents() throws IOException {
        when(attendanceView.readAttendanceOption()).thenReturn("modify","back");
        when(attendanceView.promptForDateSelection(anyList())).thenReturn("2024-04-10");
        when(attendanceView.readModifyOption()).thenReturn("retake");
        when(attendanceView.promptForStudentAttendance(eq("huidan"), anyBoolean())).thenReturn("T");
        when(attendanceView.promptForStudentAttendance(eq("zhecngen"), anyBoolean())).thenReturn("A");
        attendanceController.startAttendanceMenue();
        verify(attendanceView).showModifyMenue();
        verify(attendanceView, times(1)).promptForDateSelection(anyList());
        verify(attendanceView, times(1)).readModifyOption();
        verify(attendanceView, times(2)).promptForStudentAttendance(anyString(), eq(false));
    }
    @Test 
    void testStartModify_SpecificStudent() throws IOException {
        when(attendanceView.readAttendanceOption()).thenReturn("modify","back");
        when(attendanceView.promptForDateSelection(anyList())).thenReturn("2024-04-10");
        when(attendanceView.readModifyOption()).thenReturn("modify");
        when(attendanceView.promptForStudentName(any(AttendanceRecord.class))).thenReturn("huidan");
        when(attendanceView.promptForAttendanceStatus()).thenReturn(AttendanceStatus.TARDY);
        attendanceController.startAttendanceMenue();
        verify(attendanceView, times(1)).showModifyMenue();
        verify(attendanceView, times(1)).promptForDateSelection(anyList());
        verify(attendanceView, times(1)).readModifyOption();
        verify(attendanceView, times(1)).promptForStudentName(any(AttendanceRecord.class));
        verify(attendanceView, times(1)).promptForAttendanceStatus();
    }


}
