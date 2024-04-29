package edu.duke.ece651.team1.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import edu.duke.ece651.team1.client.model.*;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class StudentReportServiceTest {

    @InjectMocks
    private StudentReportService studentReportService;

    @Mock
    private UserSession userSession;

    private MockedStatic<ApiService> mockedApiService;
    int sectionId = 1;

    @BeforeEach
    void setUp() {
        userSession = UserSession.getInstance();
        userSession.setHost("localhost");
        userSession.setPort("8080");
        userSession.setUid(1);
        mockedApiService = Mockito.mockStatic(ApiService.class);

        String reportUrl_detail = "http://localhost:8080/api/attendance/report/student/1/1/?detail=true";
        String reportUrl_summary = "http://localhost:8080/api/attendance/report/student/1/1/?detail=false";

        when(ApiService.executeGetRequest(eq(reportUrl_detail), any(ParameterizedTypeReference.class)))
                .thenReturn("2024-04-12: Present\n2024-04-13: Tardy");
        when(ApiService.executeGetRequest(eq(reportUrl_summary), any(ParameterizedTypeReference.class)))
                .thenReturn("Total Attendance: 5/10 (50.00% attendance rate), Tardy Count: 2");

        // when(ApiService.executeGetRequest(eq(reportUrl_summary),
        // any(ParameterizedTypeReference.class))).thenReturn("summary report");

    }

    @AfterEach
    public void tearDown() {
        mockedApiService.close();
    }

    @Test
    public void testGetReport() {

        boolean detail = true;
        String report = studentReportService.getReport(sectionId, detail);
        String mockResponse = "2024-04-12: Present\n2024-04-13: Tardy";
        assertEquals(mockResponse, report);
    }

    @Test
    public void testGetDetailReport() {

        Map<String, String> details = studentReportService.getDetailReport(sectionId);

        assertNotNull(details);
        assertEquals("Present", details.get("2024-04-12"));
        assertEquals("Tardy", details.get("2024-04-13"));
    }

    @Test
    public void testGetSummaryStatistic() {
       
        AttendanceSummary summary = studentReportService.getSummaryStatistic(sectionId);

        assertNotNull(summary);
    }
}
