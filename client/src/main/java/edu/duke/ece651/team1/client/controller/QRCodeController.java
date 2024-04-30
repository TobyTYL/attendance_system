package edu.duke.ece651.team1.client.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.duke.ece651.team1.client.service.*;
import org.springframework.stereotype.Controller;
import edu.duke.ece651.team1.client.model.*;
import org.springframework.ui.Model;
import edu.duke.ece651.team1.client.model.*;
import org.slf4j.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Manages the QR code generation and attendance marking via QR code
 * authentication.
 * Handles QR code display, user authentication, token validation ,location
 * validation,device validation, and attendance update.
 */
@Controller
@RequestMapping("/qrcode")
public class QRCodeController {
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    LocationService locationService;
    @Autowired
    CookieService cookieService;

    /**
     * Generates and displays a QR code for a specific section.
     * This QR code leads to an authentication page for marking attendance.
     *
     * @param sectionId the ID of the section for which the QR code is generated
     * @param model     the Spring Model object to pass data to the view
     * @return the view name displaying the QR code
     */
    @GetMapping("/{sectionId}")
    public String getQRCode(@PathVariable int sectionId, Model model) {
        try {
            String redirectUrl = "https://" + UserSession.getInstance().getHost() + ":" + "8081"
                    + "/qrcode/markattendance/authenticate/" + sectionId;
            String qr = qrCodeService.generateQRCodeImage(redirectUrl, true);
            model.addAttribute("qr", qr);
            return "qrCode";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Provides an authentication page for users to enter their credentials as part
     * of the attendance marking process.
     *
     * @param sectionId the ID of the section for QR code authentication
     * @return the view name for user authentication
     */
    @GetMapping("markattendance/authenticate/{sectionId}")
    public String authen(@PathVariable int sectionId) {
        return "qrAuthenticate";
    }

    /**
     * Displays the result of the attendance marking process.
     *
     * @param model the Spring Model object to pass data to the view
     * @return the view name displaying the attendance marking result
     */
    @GetMapping("/markattendance/result")
    public String showResult(Model model) {
        return "attendanceResult";
    }

    /**
     * Handles the process of authenticating and marking attendance through QR code
     * scanning.
     * This includes validating the user's credentials, token, location, and device.
     *
     * @param username           the user's username
     * @param password           the user's password
     * @param token              the token from the QR code
     * @param latitude           the latitude from the user's location
     * @param longitude          the longitude from the user's location
     * @param cookieName         the name of the cookie for device validation
     * @param sectionId          the ID of the section for attendance marking
     * @param redirectAttributes attributes for a redirect scenario, used to pass
     *                           success or error messages
     * @return the redirect path after attempting to mark attendance
     */
    @PostMapping("markattendance/authenticate/{sectionId}")
    public String authenticateAndMarkAttendance(@RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("token") String token,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude, @RequestParam("cookieName") String cookieName,
            @PathVariable int sectionId,
            RedirectAttributes redirectAttributes) {
        AuthenticationResult authResult = authenticateUser(username, password, redirectAttributes);
        if (!authResult.isSuccessful()) {
            return "redirect:/qrcode/markattendance/authenticate";
        }
        if (!validateToken(token, redirectAttributes)) {
            return "redirect:/qrcode/markattendance/result";
        }
        if (!validateLocation(latitude, longitude, redirectAttributes)) {
            return "redirect:/qrcode/markattendance/result";
        }
        if (!validateDevice(cookieName, authResult.getMessage(), redirectAttributes)) {
            return "redirect:/qrcode/markattendance/result";
        }

        updateAttendance(sectionId, authResult.getMessage(), redirectAttributes);
        return "redirect:/qrcode/markattendance/result";
    }

    private AuthenticationResult authenticateUser(String username, String password,
            RedirectAttributes redirectAttributes) {
        String response = userService.authenticate(username, password);
        if ("loginFailed".equals(response)) {
            redirectAttributes.addFlashAttribute("loginError", "Login failed");
            return new AuthenticationResult(false, null);
        }
        return new AuthenticationResult(true, response);
    }

    private boolean validateToken(String token, RedirectAttributes redirectAttributes) {
        if (!tokenService.validateToken(token)) {
            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("message", "The QR code has been expired, Attendance mark Failed");
            return false;
        }
        return true;
    }

    private boolean validateDevice(String cookie, String response, RedirectAttributes redirectAttributes) {
        JSONObject jsonObject = new JSONObject(response);
        int studentId = jsonObject.getInt("id");
        if (!cookieService.validateActivity(cookie, studentId)) {
            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("message",
                    "This device has recently marked attendance for a different account.");
            return false;
        }
        return true;
    }

    private boolean validateLocation(double latitude, double longitude, RedirectAttributes redirectAttributes) {
        if (!locationService.validateLocation(latitude, longitude)) {
            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("message", "You are not in the classRoom, Attendance mark Failed");
            return false;
        }
        return true;
    }

    private void updateAttendance(int sectionId, String response, RedirectAttributes redirectAttributes) {
        JSONObject jsonObject = new JSONObject(response);
        int studentId = jsonObject.getInt("id");
        String result = attendanceService.updateStudentAttendance(sectionId, studentId);
        if (!result.equals("success")) {
            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("message", result);
            return;
        }
        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Attendance successfully marked!");
    }

}
