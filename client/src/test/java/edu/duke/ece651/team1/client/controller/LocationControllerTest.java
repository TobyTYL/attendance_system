package edu.duke.ece651.team1.client.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
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
import static org.mockito.Mockito.times;
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

@ContextConfiguration(classes = { LocationController.class })
@ExtendWith(SpringExtension.class)
public class LocationControllerTest {
    @MockBean
    private QRCodeService qrCodeService;
    @Autowired
    private LocationController controller;
    private Model model;
    RedirectAttributes redirectAttributes;

    @BeforeEach
    public void setUp() {

        model = new ExtendedModelMap();
        redirectAttributes = new RedirectAttributesModelMap();
    }

    @Test
    public void testLocationQRCodeGeneration() throws Exception {
        int sectionId = 1;
        when(qrCodeService.generateQRCodeImage(anyString(), eq(false)))
                .thenReturn("qrCodeData1", "qrCodeData2");
        String resultView = controller.getMethodName(model, sectionId);
        verify(qrCodeService, times(2)).generateQRCodeImage(anyString(), eq(false));
        assertEquals("test", resultView);
        assertEquals("qrCodeData1", model.getAttribute("qr1"));
        assertEquals("qrCodeData2", model.getAttribute("qr2"));
        assertEquals(sectionId, model.getAttribute("sectionId"));
        assertNotNull(model.getAttribute("uid"));
    }

    @Test
    public void testRecordLocation() {
        String code = "location1";
        double latitude = 35.0;
        double longitude = -78.0;
        String redirectUrl = controller.recordLocation(code, latitude, longitude, redirectAttributes);
        assertEquals("redirect:/record-location", redirectUrl);
        assertEquals("You successfully submit your location", redirectAttributes.getFlashAttributes().get("message"));
    }

    @Test
    public void testCalculateDistance() {
        controller.recordLocation("location1", 35.0, -78.0, redirectAttributes);
        controller.recordLocation("location2", 35.5, -78.5, redirectAttributes);
        ResponseEntity<String> response = controller.calculateDistance();
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Distance"));
    }

}
