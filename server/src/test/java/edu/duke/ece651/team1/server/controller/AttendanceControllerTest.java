package edu.duke.ece651.team1.server.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import edu.duke.ece651.team1.shared.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import edu.duke.ece651.team1.server.service.AttendanceService;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import static org.hamcrest.Matchers.containsString;

import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.sql.*;

@WebMvcTest(AttendanceController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AttendanceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendanceService attendanceService;
    int sectionID = 1;
    String recordJson = "{" +
    "\"Record Id\": 123," +
    "\"sessionDate\": \"2024-04-10\"," +
    "\"Entries\": {" +
    "\"JohnDoe\": {" +
    "\"student Id\": 1," +
    "\"Display Name\": \"John Doe\"," +
    "\"Email\": \"john.doe@example.com\"," +
    "\"Attendance status\": \"PRESENT\"" +
    "}}}";

    @BeforeEach
    public void setup() {

        // SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        // Authentication authentication = Mockito.mock(Authentication.class);
        // when(securityContext.getAuthentication()).thenReturn(authentication);
        // SecurityContextHolder.setContext(securityContext);

        MockitoAnnotations.openMocks(this);

    }

    @Test
    @WithMockUser
    public void getAttendanceRecordDates_Success() throws Exception {
        List<String> expectedDates = Arrays.asList("2022-01-01", "2022-01-02");
        when(attendanceService.getRecordDates(sectionID)).thenReturn(expectedDates);
        mockMvc.perform(get("/api/attendance/record-dates/{sectionID}", sectionID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\"2022-01-01\",\"2022-01-02\"]"));
    }

    @Test
    @WithMockUser
    public void getAttendanceRecordDates_Fail() throws Exception {
        when(attendanceService.getRecordDates(anyInt())).thenThrow(new RuntimeException("Test exception"));
        mockMvc.perform(get("/api/attendance/record-dates/{sectionID}", sectionID))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("[]"));
    }

    @Test
    @WithMockUser
    public void getAttendanceRecord_Success() throws Exception {
        String sessionDate = "2024-04-10";
        String expectedRecord = "expected record details";
        when(attendanceService.getRecord(sectionID, sessionDate)).thenReturn(expectedRecord);
        mockMvc.perform(get("/api/attendance/record/{sectionID}/{sessionDate}", sectionID, sessionDate))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedRecord));
    }

    @Test
    @WithMockUser
    public void getAttendanceRecord_Fail_SQLException() throws Exception {
        String sessionDate = "2024-04-10";
        when(attendanceService.getRecord(sectionID, sessionDate)).thenThrow(new SQLException("Database error"));
        mockMvc.perform(get("/api/attendance/record/{sectionID}/{sessionDate}", sectionID, sessionDate))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Failed to fetch record because")));
    }
    

    @Test
    @WithMockUser(username = "user")
    public void getStudentRecordEntry_Success() throws Exception {
        String sessionDate = "2024-04-10";
        String studentName = "huidan";
        String expectedResponse = "attendance details";
        when(attendanceService.getStudentRecordEntry(sectionID, sessionDate, studentName)).thenReturn(expectedResponse);
        mockMvc.perform(get("/api/attendance/entry/{sectionID}/{sessionDate}/{studentName}", sectionID, sessionDate,
                studentName))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    @WithMockUser
    public void getStudentRecordEntry_SQLException() throws Exception {
        String sessionDate = "2024-04-10";
        String studentName = "huidan";
        when(attendanceService.getStudentRecordEntry(sectionID, sessionDate, studentName))
                .thenThrow(new SQLException("Database error"));

        mockMvc.perform(get("/api/attendance/entry/{sectionID}/{sessionDate}/{studentName}", sectionID, sessionDate,
                studentName))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Failed to fetch student attendance entry")));
    }
    @Test
    @WithMockUser
    public void getStudentRecordEntry_Exception() throws Exception {
        String sessionDate = "2024-04-10";
        String studentName = "huidan";
        when(attendanceService.getStudentRecordEntry(sectionID, sessionDate, studentName))
                .thenThrow(new IllegalArgumentException("other exception"));

        mockMvc.perform(get("/api/attendance/entry/{sectionID}/{sessionDate}/{studentName}", sectionID, sessionDate,
                studentName))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Error fetching student attendance entry: ")));
    }


    @Test
    @WithMockUser
    public void getAllStudent_Success() throws Exception {
        List<Student> expectedStudents = Arrays.asList(
                new Student(1, "a", "c", "a@example.com", null),
                new Student(2, "b", "d", "b@example.com", null));
        when(attendanceService.getAllStudent(sectionID)).thenReturn(expectedStudents);
        mockMvc.perform(get("/api/attendance/allStudents/{sectionID}", sectionID))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{'studentId':1,'legalName':'a','disPlayName':'c','email':'a@example.com'},{'studentId':2,'legalName':'b','disPlayName':'d','email':'b@example.com'}]"));
    }

    @Test
    @WithMockUser
    public void getAllStudent_NotFound() throws Exception {
        when(attendanceService.getAllStudent(sectionID)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/attendance/allStudents/{sectionID}", sectionID))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void getAllStudent_InternalServerError() throws Exception {
        when(attendanceService.getAllStudent(sectionID)).thenThrow(new RuntimeException("Unexpected error"));
        mockMvc.perform(get("/api/attendance/allStudents/{sectionID}", sectionID))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    public void getReportForProfessor_Success() throws Exception {
        String expectedReport = "Attendance report details";
        when(attendanceService.getAttendanceReportForProfessor(sectionID)).thenReturn(expectedReport);
        mockMvc.perform(get("/api/attendance/report/class/{sectionID}", sectionID))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedReport));
    }

    @Test
    @WithMockUser
    public void getReportForProfessor_InternalServerError() throws Exception {
        String errorMessage = "Database error";
        when(attendanceService.getAttendanceReportForProfessor(sectionID))
                .thenThrow(new RuntimeException(errorMessage));
        mockMvc.perform(get("/api/attendance/report/class/{sectionID}", sectionID))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Get class Report error because " + errorMessage)));
    }

    @Test
    @WithMockUser
    public void getReportForStudent_Success() throws Exception {
        int studentID = 1;
        Boolean detail = true;
        String expectedReport = "Detailed report for student";
        when(attendanceService.getAttendanceReportForStudent(studentID, sectionID, detail)).thenReturn(expectedReport);
        mockMvc.perform(get("/api/attendance/report/student/{studentID}/{sectionID}", studentID, sectionID)
                .param("detail", detail.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedReport));
    }

    @Test
    @WithMockUser
    public void getReportForStudent_InternalServerError() throws Exception {
        int studentID = 1;
        Boolean detail = false;
        String errorMessage = "Service error";
        when(attendanceService.getAttendanceReportForStudent(studentID, sectionID, detail))
                .thenThrow(new RuntimeException(errorMessage));
        mockMvc.perform(get("/api/attendance/report/student/{studentID}/{sectionID}", studentID, sectionID)
                .param("detail", detail.toString()))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Get student Report error because " + errorMessage)));
    }

    @Test
    @WithMockUser
    public void modifyAttendanceEntry_Success() throws Exception {
        String sessionDate = "2024-04-10";
        String attendanceEntryJson = "{\"Legal Name\":\"yitiao\",\"Attendance Status\":\"PRESENT\"}";
        String responseMessage = "Attendance entry modified successfully";
        when(attendanceService.modifyStudentEntryAndSendUpdates(sectionID, sessionDate, attendanceEntryJson))
                .thenReturn(responseMessage);
        mockMvc.perform(post("/api/attendance/modification/{sectionID}/{sessionDate}", sectionID, sessionDate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(attendanceEntryJson))
                .andExpect(status().isOk())
                .andExpect(content().string(responseMessage));
    }

    @Test
    @WithMockUser
    public void modifyAttendanceEntry_InternalServerError() throws Exception {
        String sessionDate = "2024-04-10";
        String attendanceEntryJson = "{\"Legal Name\":\"yitiao\",\"Attendance Status\":\"PRESENT\"}";
        String errorMessage = "Database error";
        when(attendanceService.modifyStudentEntryAndSendUpdates(sectionID, sessionDate, attendanceEntryJson))
                .thenThrow(new RuntimeException(errorMessage));
        mockMvc.perform(post("/api/attendance/modification/{sectionID}/{sessionDate}", sectionID, sessionDate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(attendanceEntryJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Error modifying attendance entry: " + errorMessage)));
    }

    @Test
    @WithMockUser
    public void SubmitAttendanceRecord_Success() throws Exception {
        int sectionID = 1;
        doNothing().when(attendanceService).saveAttendanceRecord(recordJson, sectionID);

        mockMvc.perform(post("/api/attendance/record/{sectionID}", sectionID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(recordJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Attendance record saved successfully"));
    }

    @Test
    @WithMockUser
    public void SubmitAttendanceRecord_Fail_Exception() throws Exception {
        String errorMessage = "Database error";
        doThrow(new RuntimeException(errorMessage)).when(attendanceService).saveAttendanceRecord(recordJson, sectionID);
        mockMvc.perform(post("/api/attendance/record/{sectionID}", sectionID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(recordJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Failed to save attendance record because" + errorMessage)));
    }

    @Test
    @WithMockUser
    public void updateAttendanceRecord_Success() throws Exception {
        String expectedResponse = "Attendance record update successfully";
        doNothing().when(attendanceService).updateAttendanceRecord(recordJson);

        mockMvc.perform(put("/api/attendance/record/{sectionID}", sectionID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(recordJson))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    @WithMockUser
    public void updateAttendanceRecord_Fail_Exception() throws Exception {
        String errorMessage = "Service error";
        doThrow(new RuntimeException(errorMessage)).when(attendanceService).updateAttendanceRecord(recordJson);
        mockMvc.perform(put("/api/attendance/record/{sectionID}", sectionID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(recordJson))
                .andExpect(status().isInternalServerError())
                .andExpect(
                        content().string(containsString("Failed to update attendance record because" + errorMessage)));
    }

}
