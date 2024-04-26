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

@Controller
@RequestMapping("/qrcode")
public class QRCodeController {
    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @GetMapping("/{sectionId}")
    public String getQRCode(@PathVariable int sectionId, Model model) {
        try {
            String targetUrl = "/student/markattendance/" + sectionId;
            String redirectUrl = "https://" + UserSession.getInstance().getHost() + ":" + "8081"
                    + "/markattendance/authenticate";
            Double latitude = (Double) model.asMap().get("latitude");
            Double longitude = (Double) model.asMap().get("longitude");
            String qr = qrCodeService.generateQRCodeImage(redirectUrl, latitude, longitude);
            model.addAttribute("qr", qr);
            return "test";
        } catch (Exception e) {
            return "";
        }
    }

    @GetMapping("markattendance/authenticate")
    public String authen() {
        return "qrAuthenticate";
    }
    @GetMapping("/markattendance/result")
    public String showResult(Model model) {
        if (model.containsAttribute("success")) {
            model.addAttribute("message", "Attendance successfully marked!");
            model.addAttribute("success", true);
        } else {
            String errorMessage = (String) model.asMap().get("errorMessage");
            if (errorMessage != null) {
                model.addAttribute("message", errorMessage);
            } else {
                model.addAttribute("message", "An unknown error occurred.");
            }
            model.addAttribute("success", false);
        }
        return "attendanceResult"; 
    }
    

    @PostMapping("markattendance/authenticate")
    public String postMethodName(@RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "token") String token, @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude, RedirectAttributes redirectAttributes) {
        String response = userService.authenticate(username, password);
        if ("loginFailed".equals(response)) {
            redirectAttributes.addFlashAttribute("loginError", "Login failed");
            return "redirect:/qrcode/markattendance/authenticate";
        }
        if (tokenService.validateToken(token)) {
            double threshold = 2;
            if (tokenService.validateLocation(token, latitude, longitude, threshold)) {
                // call server service to update
                JSONObject jsonObject = new JSONObject(response);
                int studentId = jsonObject.getInt("id");
                redirectAttributes.addFlashAttribute("success", true);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not in the classRoom, Attendance mark Failed");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "The QR code has been expired, Attendance mark Failed");
        }

        return "redirect:/qrcode/markattendance/result";

    }
}
