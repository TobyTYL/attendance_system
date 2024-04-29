package edu.duke.ece651.team1.client.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import edu.duke.ece651.team1.client.model.*;
import edu.duke.ece651.team1.client.service.AttendanceService;
import edu.duke.ece651.team1.client.service.QRCodeService;
import edu.duke.ece651.team1.shared.AttendanceRecord;
import edu.duke.ece651.team1.shared.Student;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.servlet.http.HttpServletResponse;
@ContextConfiguration(classes = {AttendanceController.class})
@ExtendWith(SpringExtension.class)
class AttendanceControllerTest {
    @MockBean
    private AttendanceService attendanceService;
    @MockBean
    private QRCodeService qrCodeService;
    @Autowired
    private AttendanceController controller;
    int sectionId = 1;
    List<Student> mockedStudents = List.of(new Student(1, "huidan", "huidan", "email", 2),
            new Student(2, "zhecheng", "zhecngen", "email", 3));
    Model model = new ExtendedModelMap();
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    @BeforeEach
    public void setup() {
        when(attendanceService.getRoaster(sectionId)).thenReturn(mockedStudents);
    }

    @Test
    public void testBeginTakeAttendanceMan() {
        AttendanceController controller = new AttendanceController();
        controller.attendanceService = attendanceService;
        String view = controller.beginTakeAttendanceMan(sectionId, model);
        assertEquals("takeAttendance", view);
        assertEquals(mockedStudents, model.getAttribute("students"));
        assertEquals(sectionId, model.getAttribute("sectionId"));
        assertNotNull(model.getAttribute("uid"));
    }

    @Test
    public void testSendAttendanceRecord() {
        Map<String, String> params = new HashMap<>();
        params.put("attendanceStatus[1]", "PRESENT");
        params.put("attendanceStatus[2]", "ABSENT");
        String redirectUrl = controller.sendAttendanceRecord(params, sectionId, redirectAttributes);
        verify(attendanceService).sendAttendanceRecord(any(), eq(sectionId));
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("successMessage"));
        assertTrue(redirectUrl.contains("/attendance/record/" + sectionId));
    }

    @Test
    public void testShowRecord() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String sessionDate = LocalDate.now().format(formatter).toString();
        AttendanceRecord mockRecord = new AttendanceRecord();
        when(attendanceService.getAttendanceRecord(sessionDate, sectionId)).thenReturn(mockRecord);
        String view = controller.showRecord(sectionId, sessionDate, model);
        assertEquals("attendanceRecord", view);
        assertEquals(mockRecord, model.getAttribute("record"));
        assertEquals(sectionId, model.getAttribute("sectionId"));
        assertNotNull(model.getAttribute("uid"));
    }

    @Test
    public void testGetRecordList() {
        List<String> mockSessionDates = Arrays.asList("2023-04-01", "2023-04-02");
        when(attendanceService.getRecordDates(sectionId)).thenReturn(mockSessionDates);
        String view = controller.getrecordList(sectionId, model);
        assertEquals("recordtable", view);
        assertEquals(mockSessionDates, model.getAttribute("sessionDates"));
        assertEquals(sectionId, model.getAttribute("sectionId"));
        assertNotNull(model.getAttribute("uid"));
    }

    @Test
    public void testUpdateAttendanceRecord() {
        String sessionDate = "2024-04-10";
        Map<String, String> allParams = new HashMap<>();
        allParams.put("attendanceStatus[1]", "PRESENT");
        allParams.put("attendanceStatus[2]", "ABSENT");
        AttendanceRecord mockRecord = new AttendanceRecord(LocalDate.parse("2024-04-10"));
        mockRecord.initializeFromRoaster(mockedStudents);
        when(attendanceService.getAttendanceRecord(sessionDate, sectionId)).thenReturn(mockRecord);
        String redirectUrl = controller.updateAttendanceRecord(sectionId, sessionDate, allParams, redirectAttributes);
        verify(attendanceService).updateAttendanceRecord(mockRecord, sectionId);
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("successMessage"));
        assertEquals("redirect:/attendance/record/" + sectionId + "/" + sessionDate, redirectUrl);
    }

    @Test
    public void testDownload() throws Exception {
        String sessionDate = "2023-04-01";
        String format = "xml";
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        String redirectUrl = controller.dowonload(sectionId, sessionDate, format, redirectAttributes, mockResponse);
        verify(attendanceService).exportRecord(sessionDate, sectionId, format, mockResponse);
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("successMessage"));
        assertEquals("redirect:/attendance/records/" + sectionId, redirectUrl);
    }

    @Test
    public void testGetClassReport() {
        List<AttendanceSummary> mockSummaries = Arrays.asList(new AttendanceSummary(
                "StudentName1: Attended 5/10 sessions (50.00% attendance rate), Tardy Count: 2."));
        when(attendanceService.getAttendancestatistic(sectionId)).thenReturn(mockSummaries);
        String view = controller.getClassReport(sectionId, model);
        assertEquals("classReport", view);
        assertEquals(mockSummaries, model.getAttribute("summaries"));
        assertNotNull(model.getAttribute("uid"));
    }

    @Test
    public void testBeginTakeAttendanceAuto() throws Exception {
        Model model = new ExtendedModelMap();
        controller.qrCodeService = mock(QRCodeService.class);
        when(controller.qrCodeService.generateQRCodeImage(anyString(), eq(false))).thenReturn("qrCodeData");
        String view = controller.beginTakeAttendanceAuto(sectionId, model);
        assertEquals("takeAttendanceAuto", view);
        assertEquals("qrCodeData", model.getAttribute("qr"));
        assertEquals(sectionId, model.getAttribute("sectionId"));
        assertNotNull(model.getAttribute("uid"));
        verify(controller.qrCodeService).generateQRCodeImage(anyString(), eq(false));
    }

    @Test
    public void testSendInitialAttendanceWhenScanned() {
        double threshold = 10.0;
        UserSession.getInstance().setScaned(true);
        String redirectUrl = controller.sendInitialAttendance(threshold, sectionId, redirectAttributes);
        verify(controller.attendanceService).sendAttendanceRecord(any(AttendanceRecord.class), eq(sectionId));
        assertFalse(UserSession.getInstance().isScaned());
        assertEquals(threshold, UserSession.getInstance().getThreshold());
        assertEquals("redirect:/qrcode/" + sectionId, redirectUrl);
    }

    @Test
    public void testSendInitialAttendanceWhenNotScanned() {
        double threshold = 10.0;
        UserSession.getInstance().setScaned(false);
        String redirectUrl = controller.sendInitialAttendance(threshold, sectionId, redirectAttributes);
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("notScanned"));
        assertEquals("redirect:/attendance/new/auto/" + sectionId, redirectUrl);
    }

    @Test
    public void testSubmitLocation() {
        double latitude = 35.0;
        double longitude = -78.0;
        String redirectUrl = controller.submitLocation(sectionId, latitude, longitude, redirectAttributes);
        assertEquals(longitude, UserSession.getInstance().getProfessorLongitude(), 0);
        assertEquals(latitude, UserSession.getInstance().getProfesssorLatitude(), 0);
        assertTrue(UserSession.getInstance().isScaned());
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("message"));
        assertEquals("redirect:/attendance/submitPosition/" + sectionId, redirectUrl);
    }

}
