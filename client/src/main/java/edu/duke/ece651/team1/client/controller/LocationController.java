package edu.duke.ece651.team1.client.controller;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import edu.duke.ece651.team1.client.service.AttendanceService;
import edu.duke.ece651.team1.client.service.CourseService;
import edu.duke.ece651.team1.client.service.QRCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.checkerframework.checker.units.qual.min;
import org.json.*;
import org.springframework.ui.Model;
import edu.duke.ece651.team1.shared.*;
import org.slf4j.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import org.springframework.ui.Model;
import edu.duke.ece651.team1.shared.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import edu.duke.ece651.team1.client.model.*;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.*;

@Controller
public class LocationController {
    private final Map<String, Location> locationStorage = new ConcurrentHashMap<>();
    @Autowired
    QRCodeService qrCodeService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    /**
     * Generates QR codes for two predefined locations and provides them along with
     * section ID to the model.
     * This method is intended to simulate a setup where QR codes are scanned to
     * record locations.
     *
     * @param model     the Spring Model object to pass data to the view
     * @param sectionId the ID of the section to which the location pertains
     * @return the name of the view to render, typically used for testing QR code
     *         functionality
     */
    @GetMapping("/test/location/{sectionId}")
    public String getMethodName(Model model, @PathVariable int sectionId) {
        try {
            String redirectUrl = "https://" + UserSession.getInstance().getHost() + ":" + "8081"
                    + "/record-location";
            String qr1 = qrCodeService.generateQRCodeImage(redirectUrl + "?code=location1", false);
            String qr2 = qrCodeService.generateQRCodeImage(redirectUrl + "?code=location2", false);
            model.addAttribute("qr1", qr1);
            model.addAttribute("qr2", qr2);
            model.addAttribute("sectionId", sectionId);
            model.addAttribute("uid", UserSession.getInstance().getUid());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return "test";

    }

    /**
     * Provides a simple view for submitting location data. This method might serve
     * as a landing page
     * following QR code scanning.
     *
     * @return the name of the view to render for submitting location data
     */
    @GetMapping("/record-location")
    public String getMethodName() {
        return "submitLocation";
    }

    /**
     * Records a location based on a given code, latitude, and longitude. This
     * method
     * stores the location into a concurrent map and adds a confirmation message to
     * redirect attributes.
     *
     * @param code               a unique identifier for the location being recorded
     * @param latitude           the latitude of the location
     * @param longitude          the longitude of the location
     * @param redirectAttributes attributes for a redirect scenario, used to show
     *                           success messages
     * @return redirect path for the location submission confirmation
     */
    @PostMapping("/record-location")
    public String recordLocation(@RequestParam String code, @RequestParam double latitude,
            @RequestParam double longitude, RedirectAttributes redirectAttributes) {
        System.out.println(code + ": " + latitude + " : " + longitude);
        locationStorage.put(code, new Location(latitude, longitude));
        redirectAttributes.addFlashAttribute("message", "You successfully submit your location");
        return "redirect:/record-location";

    }

    /**
     * Calculates the distance between two previously recorded locations identified
     * by specific codes.
     * If both locations are found, it calculates the distance and removes the
     * entries from the map.
     * If not, it returns an error message.
     *
     * @return ResponseEntity containing the calculated distance in meters or an
     *         error message if both locations are not provided
     */
    @GetMapping("/calculate-distance")
    public ResponseEntity<String> calculateDistance() {
        Location location1 = locationStorage.get("location1");
        Location location2 = locationStorage.get("location2");
        if (location1 != null && location2 != null) {
            double distance = GeoLocationUtil.calculateDistance(location1, location2);
            locationStorage.remove("location1");
            locationStorage.remove("location2");
            return ResponseEntity.ok("Distance: " + distance + " meters");
        }
        return ResponseEntity.badRequest().body("Both locations are required.");
    }
}
