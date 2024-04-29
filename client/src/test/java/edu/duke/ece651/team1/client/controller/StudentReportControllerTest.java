package edu.duke.ece651.team1.client.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import edu.duke.ece651.team1.client.model.AttendanceSummary;
import edu.duke.ece651.team1.client.model.UserSession;
import edu.duke.ece651.team1.client.service.StudentReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ContextConfiguration(classes = { StudentReportController.class })
@ExtendWith(SpringExtension.class)
public class StudentReportControllerTest {

    @MockBean
    private StudentReportService studentReportService;
    @Autowired
    private StudentReportController controller;
    private Model model;
    int sectionId = 1;
    int classId = 1;

    @BeforeEach
    public void setUp() {
        model = new ExtendedModelMap();
    }

    @Test
    public void testGetDetailReport() {
        Map<String, String> mockRecord = new HashMap<>();
        mockRecord.put("2024-04-01", "Present");
        when(studentReportService.getDetailReport(sectionId)).thenReturn(mockRecord);
        String viewName = controller.getDetailReport(sectionId, classId, model);
        assertEquals("detailReport", viewName);
        assertSame(mockRecord, model.getAttribute("record"));
        assertEquals(classId, model.getAttribute("classId"));
        assertEquals(sectionId, model.getAttribute("sectionId"));
        assertEquals(UserSession.getInstance().getUid(), model.getAttribute("uid"));
    }

    @Test
    public void testGetSummaryReport() {
        AttendanceSummary mockSummary = new AttendanceSummary("huidan", 1, 2, 0, 1, 50.00);
        when(studentReportService.getSummaryStatistic(sectionId)).thenReturn(mockSummary);
        String viewName = controller.getSummaryReport(sectionId, classId, model);
        assertEquals("summaryReport", viewName);
        assertSame(mockSummary, model.getAttribute("summary"));
        assertEquals(classId, model.getAttribute("classId"));
        assertEquals(sectionId, model.getAttribute("sectionId"));
        assertEquals(UserSession.getInstance().getUid(), model.getAttribute("uid"));
    }

}
