package edu.duke.ece651.team1.client.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import edu.duke.ece651.team1.client.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ContextConfiguration(classes = { QRCodeController.class })
@ExtendWith(SpringExtension.class)
public class QRCodeControllerTest {
    @MockBean
    private QRCodeService qrCodeService;
    @MockBean
    private UserService userService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private AttendanceService attendanceService;
    @MockBean
    private LocationService locationService;
    @MockBean
    private CookieService cookieService;
    @Autowired
    private QRCodeController controller;
    private Model model;
    private RedirectAttributes redirectAttributes;
    int sectionId = 1;
    String username = "user";
    String password = "pass";
    double latitude = 10.0;
    double longitude = 10.0;
    String cookieName = "cookieId";

    @BeforeEach
    public void setUp() {
        model = new ExtendedModelMap();
        redirectAttributes = new RedirectAttributesModelMap();
    }

    @Test
    public void testGetQRCode() throws Exception {
        String expectedQrCode = "qrImageData";
        when(qrCodeService.generateQRCodeImage(anyString(), anyBoolean())).thenReturn(expectedQrCode);
        String viewName = controller.getQRCode(sectionId, model);
        assertEquals("qrCode", viewName);
        assertEquals(expectedQrCode, model.getAttribute("qr"));
    }

    @Test
    public void testAuthenticateAndMarkAttendance() {
        String token = "token";
        String authResponse = "{\"id\":123}";
        when(userService.authenticate(username, password)).thenReturn(authResponse);
        when(tokenService.validateToken(token)).thenReturn(true);
        when(locationService.validateLocation(latitude, longitude)).thenReturn(true);
        when(attendanceService.updateStudentAttendance(eq(sectionId), anyInt())).thenReturn("success");
        when(cookieService.validateActivity(anyString(), anyInt())).thenReturn(true);

        String redirectUrl = controller.authenticateAndMarkAttendance(username, password, token, latitude, longitude,cookieName,
                sectionId, redirectAttributes);
        assertEquals("redirect:/qrcode/markattendance/result", redirectUrl);
        assertEquals(true, redirectAttributes.getFlashAttributes().get("success"));
        assertEquals("Attendance successfully marked!", redirectAttributes.getFlashAttributes().get("message"));
    }

    @Test
    public void testAuthen() {
        String viewName = controller.authen(sectionId);
        assertEquals("qrAuthenticate", viewName);
    }

    @Test
    public void testShowResult() {
        String viewName = controller.showResult(model);
        assertEquals("attendanceResult", viewName);
    }

    @Test
    public void testAuthenticationFails() {
        when(userService.authenticate(username, password)).thenReturn("loginFailed");
        String view = controller.authenticateAndMarkAttendance(username, password, "token", 0, 0,cookieName, 1,
                redirectAttributes);
        assertEquals("redirect:/qrcode/markattendance/authenticate", view);
        assertEquals("Login failed", redirectAttributes.getFlashAttributes().get("loginError"));
    }

    @Test
    public void testTokenValidationFails() {
        String token = "invalidToken";
        when(userService.authenticate(username, password)).thenReturn("authResponse");
        when(tokenService.validateToken(token)).thenReturn(false);
        String view = controller.authenticateAndMarkAttendance(username, password, token, 0, 0,cookieName, 1, redirectAttributes);
        assertEquals("redirect:/qrcode/markattendance/result", view);
        assertEquals(false, redirectAttributes.getFlashAttributes().get("success"));
        assertEquals("The QR code has been expired, Attendance mark Failed",
                redirectAttributes.getFlashAttributes().get("message"));
    }

    @Test
    public void testLocationValidationFails() {
        String token = "validToken";
        when(userService.authenticate(username, password)).thenReturn("{\"id\":123}");
        when(tokenService.validateToken(token)).thenReturn(true);
        when(locationService.validateLocation(latitude, longitude)).thenReturn(false);
        String view = controller.authenticateAndMarkAttendance(username, password, token, latitude, longitude,cookieName,
                sectionId, redirectAttributes);
        assertEquals("redirect:/qrcode/markattendance/result", view);
        assertEquals(false, redirectAttributes.getFlashAttributes().get("success"));
        assertEquals("You are not in the classRoom, Attendance mark Failed",
                redirectAttributes.getFlashAttributes().get("message"));
    }

    @Test
    public void testAttendanceUpdateFails() {
        String token = "validToken";
        when(userService.authenticate(username, password)).thenReturn("{\"id\":123}");
        when(tokenService.validateToken(token)).thenReturn(true);
        when(locationService.validateLocation(latitude, longitude)).thenReturn(true);
        when(attendanceService.updateStudentAttendance(sectionId, 123)).thenReturn("error");
        when(cookieService.validateActivity(anyString(), anyInt())).thenReturn(true);

        String view = controller.authenticateAndMarkAttendance(username, password, token, latitude, longitude,cookieName,
                sectionId, redirectAttributes);
        assertEquals("redirect:/qrcode/markattendance/result", view);
        assertEquals(false, redirectAttributes.getFlashAttributes().get("success"));
        assertEquals("error", redirectAttributes.getFlashAttributes().get("message"));
    }

    @Test
    public void testValidateDeviceFailure() {
        when(userService.authenticate(username, password)).thenReturn("{\"id\":123}");
        when(tokenService.validateToken("token")).thenReturn(true);
        when(locationService.validateLocation(latitude, longitude)).thenReturn(true);
        when(cookieService.validateActivity(cookieName, 123)).thenReturn(false);
        String redirectUrl = controller.authenticateAndMarkAttendance(username, password, "token", latitude, longitude, cookieName, sectionId, redirectAttributes);
        assertEquals("redirect:/qrcode/markattendance/result", redirectUrl);
        assertEquals(false, redirectAttributes.getFlashAttributes().get("success"));
        assertEquals("This device has recently marked attendance for a different account.", redirectAttributes.getFlashAttributes().get("message"));
    }
}
