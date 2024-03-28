package edu.duke.ece651.team1.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.team1.server.service.AttendanceService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AttendanceController.class})
@ExtendWith(SpringExtension.class)
class AttendanceControllerTest {
    @Autowired
    private AttendanceController attendanceController;
  


    @MockBean
    private AttendanceService attendanceService;

    @Test
    void testGetMethodName() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/attendance/record-dates");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(attendanceController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetMethodName2() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(attendanceController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetMethodName3() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/attendance/record-dates");
        requestBuilder.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(attendanceController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
     @Test
    @WithMockUser
    public void testSubmitAttendanceRecord() throws Exception {
        String recordJson ="{\n" +
        "    \"sessionDate\": \"2024-03-21\",\n" +
        "    \"Entries\": {\n" +
        "        \"yitiao\": {\n" +
        "            \"Display Name\": \"yitiao\",\n" +
        "            \"Email\": \"\",\n" +
        "            \"Attendance status\": \"Absent\"\n" +
        "        },\n" +
        "        \"zhecheng\": {\n" +
        "            \"Display Name\": \"zhecheng\",\n" +
        "            \"Email\": \"\",\n" +
        "            \"Attendance status\": \"Present\"\n" +
        "        }\n" +
        "    }\n" +
        "}";

        doNothing().when(attendanceService).saveAttendanceRecord(anyString(), anyString());
        MockHttpServletRequestBuilder requestBuilder = post("/api/attendance/record")
            .contentType(MediaType.APPLICATION_JSON)
            .content(recordJson)
            .characterEncoding("UTF-8"); 
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(attendanceController)
            .build()
            .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Attendance record saved successfully"));
    }
    @Test
    @WithMockUser
    void testGetAttendanceRecord_Success() throws Exception {
        String sessionDate = "2024-03-21";
        String dummyRecord = "Dummy record for testing";
        when(attendanceService.getRecord("user", sessionDate)).thenReturn(dummyRecord);
        MockHttpServletRequestBuilder requestBuilder = get("/api/attendance/record/{sessionDate}", sessionDate)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8"); // Ensure correct character encoding
        when(attendanceService.getRecord(anyString(), anyString())).thenReturn(dummyRecord);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(attendanceController)
            .build()
            .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(dummyRecord));
    }
    @Test
    @WithMockUser(username = "authenticatedUser") // Simulate an authenticated user
    public void testGetStudentRecordEntry_Success() throws Exception {
        String sessionDate = "2024-03-21";
        String studentName = "JohnDoe";
        String expectedResponse = "Sample response for JohnDoe on 2024-03-21";
        when(attendanceService.getStudentRecordEntry(anyString(), anyString(), anyString())).thenReturn(expectedResponse);
        MockHttpServletRequestBuilder requestBuilder = get("/api/attendance//entry/{sessionDate}/{studentName}", sessionDate, studentName)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8"); 
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(attendanceController)
            .build()
            .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                     .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
    }

    @Test
    @WithMockUser
    public void testModifyAttendanceEntry_Success() throws Exception {
        String sessionDate = "2024-03-21";
        String attendanceEntryJson = "{\"studentName\":\"JohnDoe\",\"status\":\"Present\"}";
        String expectedResponse = "Attendance entry modified successfully";
        when(attendanceService.modifyStudentEntryAndSendUpdates(anyString(), anyString(), anyString()))
                .thenReturn(expectedResponse);

        MockHttpServletRequestBuilder requestBuilder = post("/api/attendance/modification/{sessionDate}", sessionDate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(attendanceEntryJson)
                .characterEncoding("UTF-8"); 
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(attendanceController)
                    .build()
                    .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                     .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
    }




}
